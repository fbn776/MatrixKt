fun main() {
    val values1 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val values2 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val values3 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    val m1 = Matrix(values1, 3, 3)
    val m2 = Matrix(values2, 4, 4)
    val m3 = Matrix(values3, 3, 3)

    val minorMatrix = m1.minorMatrix()
    println(m1)
    println(minorMatrix)
}
