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

/**Returns the 2D matrix array*/
fun <T: Number> Matrix<T>.getMatrix2D(): Array<Array<Number>> {
    val matrix2D = Array(rows) { Array(cols) { 0 as Number } }
    //Packs the 1D matrix with the `rows` and `cols` information into a 2D matrix.
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            matrix2D[i][j] = getMatrix1D()[i * cols + j] as T
        }
    }
    return matrix2D
}

/**
 * Converts the index of a 1D list to a 2D list (i,j)
 * @param index the index from the 1D list
 */
fun <T: Number> Matrix<T>.convertIndexTo2d(index: Int) = (index / cols) to (index % cols)

/**
 * Converts a (i,j) of a 2D array into an index of a 1D array
 * @param i The i-th element of the outer array
 * @param j The j-th element of the inner arrays
 */
fun <T: Number> Matrix<T>.convert2dToIndex(i: Int, j: Int) = i * cols + j


