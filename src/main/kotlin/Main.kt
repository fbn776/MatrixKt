import kotlin.Error

fun main() {
    val values1 = arrayOf(1, 2, 3, 4, 5, 6, 7, -10, 9)
    val values2 = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2)
    val values3 = arrayOf("hi", "hello")

    val m1 = Matrix(values1, 3, 3)
    val m2 = Matrix(values2, 4, 3)

    val size = m1.isOfSameSize(m2)
    println("has same size $size")

    println("matrix 1 is $m1")
    println("matrix 2 is $m2")
}

