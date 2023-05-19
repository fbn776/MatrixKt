fun main() {
    val values1 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val values2 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    val values3 = arrayOf("hi", "hello")

    val m1 = Matrix(values1, 3, 3)
    val m2 = Matrix(values2, 3, 4)

    val m3 = m1.transposed()

    println("matrix 1 is $m1")
    println("transposed is ${m3}")


    val z = m1 * m2
    println("The result is $z")
    //val x = (1 as Float + 1 as Long)
}

