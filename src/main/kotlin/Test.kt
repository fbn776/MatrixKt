import matrix.Matrix
import matrix.minus
import matrix.plus
import matrix.times

fun main() {
    val A = Matrix(arrayOf(
        1,2,3,
        4,5,6,
        7,8,9
    ), 3, 3)
    val B = Matrix(arrayOf(
        4,3,1,
        8,9,4,
        1,3,5
    ), 3, 3)

    val C = A + B
    val D = A - B
    val E = A * B

    println("C is $C\n D is $D\n E is $E")
}
