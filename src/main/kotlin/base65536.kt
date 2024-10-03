package cc.winogrona.base65536

import java.nio.ByteBuffer

fun stringToUTF8CodePoints(source: String): IntArray {
    val result = mutableListOf<Int>()
    val utf32Str = ByteBuffer.wrap(source.toByteArray(charset = Charsets.UTF_32BE))

    while (utf32Str.hasRemaining()) {
        result.add(utf32Str.getInt())
    }

    return result.toIntArray()
}

object Base65536 {
    fun encode(value: ByteArray): String {
        val stream = ByteBuffer.allocate(if (value.size % 2 == 0) value.size * 2 else (value.size + 1) * 2)
        value.toList().chunked(2).forEach {
            val codePoint: Int
            try {
                val b1 = it[0].toUByte().toInt()
                val b2 = it.getOrNull(1)?.toUByte()?.toInt() ?: -1
                codePoint = BLOCK_START[b2]!! + b1
            } catch (e: NullPointerException) {
                throw CharacterCodingException()
            }

            stream.putInt(codePoint)
        }

        return stream.array().toString(charset = Charsets.UTF_32BE)
    }

    fun decode(value: String): ByteArray {
        val stream = ByteBuffer.allocate(stringToUTF8CodePoints(value).size * 2)
        val codepoints = stringToUTF8CodePoints(value)
        var done = false

        for (point in codepoints) {
            if (done) {
                throw CharacterCodingException()
            }

            val b1 = point and ((1 shl 8) - 1)
            val b2: Int

            try {
                b2 = B2[point - b1]!!
            } catch (e: NullPointerException) {
                throw CharacterCodingException()
            }

            if (b2 == -1) {
                stream.put(b1.toByte())
                done = true
            } else {
                stream.put(b1.toByte())
                stream.put(b2.toByte())
            }
        }

        return stream.array()
    }
}