
fun main() {
    val values1 = arrayOf(1, 2, 3, 4, 5, 6, 7, -10, 9)
    val values2 = arrayOf(1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8, 9.9)
    val values3 = arrayOf("hi", "hello")

    val m1 = Matrix(values1, 3, 3)
    val m2 = Matrix(values2, 3, 3)

    println("matrix 1 is $m1")
    println("matrix 2 is $m2")

    val sum = m1 + m2
    println("The result is $sum")
}

