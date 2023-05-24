fun main() {
    val x = arrayOf(1,2,3,4,5,6,7,8,9)
    val m1 = Matrix(x, 3, 3)

    val y = arrayOf(2,4,2,1,4,2,8,3,1)
    val m2 = Matrix(y, 3, 3)


    val m3 = m1 + m2
    print(m3)
}
