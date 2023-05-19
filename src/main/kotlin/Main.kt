fun main() {
    val values1 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val values2 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    val values3 = arrayOf("hi", "hello")

    val m1 = Matrix(values1, 3, 3)
    val m2 = Matrix(values2, 3, 4)

    println("matrix 1 is $m1")
    m1.transpose()
    println("transposed is $m1")


    val z = m1 * m2
    println("The result is $z")
}

