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

/**
 * This returns a new matrix with 1 row and 1 column less than the original matrix.
 * @param rowToExclude The row to exclude from the matrix.
 * @param colToExclude The column to exclude from the matrix.
 * @return The new matrix with 1 row and 1 column less than the original matrix.
 * @exception MatrixError.NotSquare This error is thrown if the matrix is not a square matrix.
 * @exception MatrixError.SizeTooSmall This error is thrown if the matrix is too small to exclude a row and column.
 * @exception MatrixError.DimensionOutOfBounds This error is thrown if the row or column to exclude is out of bounds.
 */
inline fun <reified T : Number> Matrix<T>.subSqMatrix(rowToExclude: Int, colToExclude: Int): Matrix<T> {
    if (!this.isSquare())
        throw MatrixError.NotSquare()

    if (this.size < 4)
        throw MatrixError.SizeTooSmall()

    if (rowToExclude >= this.rows || colToExclude >= this.cols)
        throw MatrixError.DimensionOutOfBounds()

    val m = Matrix(Array((this.rows - 1) * (this.cols - 1)) { 0 as T }, this.rows - 1, this.cols - 1)

    for (n in 0 until this.size) {
        var (ith, jth) = this.convertIndexTo2d(n)
        val curr = this[ith, jth]

        if (ith == rowToExclude || jth == colToExclude)
            continue

        if (ith > rowToExclude)
            ith--

        if (jth > colToExclude)
            jth--

        m[ith,jth] = curr
    }

    return m
}