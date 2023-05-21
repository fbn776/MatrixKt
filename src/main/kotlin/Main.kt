fun main() {
    val values1 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val values2 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val values3 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    val m1 = Matrix(values1, 3, 3)
    val m2 = Matrix(values2, 4, 4)
    val m3 = Matrix(values3, 3, 3)

    val added1 = m1 + m3
    val subtracted1 = m1 - m3


    println("The added is $added1")
    println("the subbed is $subtracted1")

    m1.forEachIndexed2d { (i,j), v ->
        println("value at ($i, $j): $v")
    }
}
