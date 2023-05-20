/**
 * A data class that contains a 1D matrix and its size information.
 * This is used to represent matrix as 1D list (The Matrix class treats a `matrix` as 2D array and does calculations with it)
 * @param matrix A flat list that contains the matrix elements
 * @param rows No of rows
 * @param cols Nof of cols
 */
data class Matrix1D<T>(val matrix: Array<T>, val rows: Int, val cols: Int)


/**
 * Returns the scalar product of the Number * Matrix
 * @returns This returns a matrix of type double
 */
operator fun Number.times(other: Matrix<*>): Matrix<Double> {
    val matrix = other.getMatrix1D()
    val arr = Array(other.size) { 0.0 }
    arr.forEachIndexed { index, t ->
        arr[index] = (this.toDouble() * matrix[index].toDouble())
    }
    return Matrix(arr, other.rows, other.cols)
}

