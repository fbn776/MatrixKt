
fun main() {
    val values1 = listOf(1, 2, 3, 4, 5, 6, 7, 8.5, 9)
    val values2 = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    val m1 = Matrix(values1, 3, 3)
    val m2 = Matrix(values2, 3, 3)


    val matrix1 = m1.matrix2D
    operator fun Number.plus(other: Number) = (this.toDouble() + other.toDouble()) as Number
    println(matrix1[0][0] + matrix1[1][2])
    //println(m1 add m2)
    println(m1[0, 0])
    print(m1)

}