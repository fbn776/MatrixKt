fun main() {
    val values1 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val values2 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val values3 = arrayOf(1,2,3,4)

    val m1 = Matrix(values1, 3, 3)
    val m2 = Matrix(values2, 4, 4)
    val m3 = Matrix(values3, 2, 2)

    val sub1 = m2.subSqMatrix(0, 0)

    val det1 = m1.determinant()
    val det2 = m2.determinant()

    m1.getValueAtIndex(-1)

    println("The det1 is $det1")
    println("The det2 is $det2")
}
