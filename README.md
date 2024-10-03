# kotlin-base65536
base65536 Kotlin implementation. A translation of the [python implementation](https://github.com/Parkayun/base65536) to Kotlin. 

# Quick start
Encode:
```Kotlin
fun main() {
    val result = Base65536.encode("Hello World".toByteArray())
      println(result) // Will output 驈ꍬ啯𒁗ꍲᕤ
}
```

Decode:
```Kotlin
fun main() {
    val result = Base65536.decode("驈ꍬ啯\uD808\uDC57ꍲᕤ")
    println(result.toString(charset = Charsets.UTF_8)) // Will output Hello World
}
```

# Thanks to
The creators of the [python implementation](https://github.com/Parkayun/base65536) (Copyright (c) 2015 Ayun Park)
