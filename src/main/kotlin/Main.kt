import kotlin.Error

fun main() {
    val values1 = arrayOf(1, 2, 3, 4, 5, 6, 7, -10, 9)
    val values2 = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    val values3 = arrayOf("hi", "hello")

    val m1 = Matrix(values1, 3, 3)
    val m2 = Matrix(values2, 3, 3)

    val x = m1[1,1]
    val y = m1.matrix1D


    println(m1 == m2)

}

