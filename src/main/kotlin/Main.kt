fun main() {
    val values1 = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    val values2 = arrayOf(12, 3, 4, 2, 2, 4, 5, 75, 54, 4, 6, 4)
    val values3 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    val m1 = Matrix(values1, 3, 3)
    val m2 = Matrix(values2, 4, 3)
    val m3 = Matrix(values3, 3, 3)

    println(m2)
    val r = m2.rowEchelonOf()
    println("The row echelon is $r")
    println("The rank is ${m2.rank()}")
}
