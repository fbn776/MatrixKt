/**
 * Clones a matrix and returns it.
 */
fun <T : Number> Matrix<T>.clone(): Matrix<T> = Matrix(this.getMatrix1D(), rows, cols)

/**
 * Check if two matrices are of same size.
 * @param other the other matrix to do the check
 * @return true if rows and cols of both matrices are equal, else false
 */
fun <T : Number> Matrix<T>.isOfSameSize(other: Matrix<*>) = (this.rows == other.rows && this.cols == other.cols)

/**
 * Checks if two matrix can be cross multiplied or not
 * @return If two matrix A(n×m) and B(p×q) can be multiplied not. ie checks for m = p
 */
fun <T : Number> Matrix<T>.canMult(other: Matrix<*>) = (this.cols == other.rows)

/**
 * Checks if a matrix is square or not.
 * @return true if the matrix is square, else false
 */
fun <T : Number> Matrix<T>.isSquare() = (this.rows == this.cols)

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
inline fun <reified  T : Number> Matrix<T>.getMatrix2D(): Array<Array<T>> {
    val matrix2D = Array(rows) { Array(cols) { this[0,0] } }
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
fun <T : Number> Matrix<T>.convertIndexTo2d(index: Int) = (index / cols) to (index % cols)

/**
 * Converts a (i,j) of a 2D array into an index of a 1D array
 * @param i The i-th element of the outer array
 * @param j The j-th element of the inner arrays
 */
fun <T : Number> Matrix<T>.convert2dToIndex(i: Int, j: Int) = i * cols + j

/**
 * Performs the given [action] on each element.
 */
inline fun <T : Number> Matrix<T>.forEach(action: (T) -> Unit): Unit {
    for (element in this) action(element)
}

/**
 * Performs the given [action] on each element, providing sequential index with the element.
 * @param [action] function that takes the index of an element and the element itself
 * and performs the action on the element.
 */
inline fun <T : Number> Matrix<T>.forEachIndexed(action: (index: Int, T) -> Unit): Unit {
    var index = 0
    for (item in this) action(index++, item)
}

/**
 * Performs the given [action] on each element, providing sequential index as an (i,j) pair with the element.
 * @param [action] function that takes the index of an element and the element itself
 * and performs the action on the element.
 */
inline fun <T : Number> Matrix<T>.forEachIndexed2d(action: (index: Pair<Int, Int>, T) -> Unit): Unit {
    var index = 0
    for (item in this) {
        action(this.convertIndexTo2d(index), item)
        index++
    }
}

/**
 * Gets the row at the given [rowNum] as an array.
 * @param [rowNum] the row to get
 * @return The values of a row as an array
 * @exception MatrixError.DimensionOutOfBounds thrown if the [rowNum] is out of bounds.
 */
inline fun <reified T : Number> Matrix<T>.rowAt(rowNum: Int): Array<T> {
    if (rowNum > this.rows - 1 || rowNum < 0)
        throw MatrixError.DimensionOutOfBounds(note = "Row at $rowNum does not exist")

    val row = Array(this.rows) { this[0, 0] }
    row.forEachIndexed { i, _ ->
        row[i] = this[rowNum, i]
    }
    return row
}

/**
 * Gets the column at the given [colNum] as an array.
 * @param [colNum] the column to get
 * @return The values of a column as an array
 * @exception MatrixError.DimensionOutOfBounds thrown if the [colNum] is out of bounds.
 */
inline fun <reified T : Number> Matrix<T>.columnAt(colNum: Int): Array<T> {
    if (colNum > this.rows - 1 || colNum < 0)
        throw MatrixError.DimensionOutOfBounds(note = "Column at $colNum does not exist")

    val col = Array(this.rows) { this[0, 0] }
    col.forEachIndexed { i, _ ->
        col[i] = this[i, colNum]
    }
    return col
}
