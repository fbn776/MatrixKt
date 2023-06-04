package matrix

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
 * Checks if a square matrix is a singular matrix or not (determinate = 0)
 * @return true if the matrix is a singular matrix, else false
 * @exception MatrixError.NotSquareMatrix This error is thrown if the matrix is not a square matrix.
 */
fun <T : Number> Matrix<T>.isSingular(): Boolean {
    if (!this.isSquare())
        throw MatrixError.NotSquareMatrix()

    return this.determinant() == 0.0
}

/**Returns the 2D matrix array*/
inline fun <reified T : Number> Matrix<T>.getMatrix2D(): Array<Array<T>> {
    val matrix2D = Array(rows) { Array(cols) { this[0, 0] } }
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
inline fun <T : Number> Matrix<T>.forEach(action: (currVal: T) -> Unit): Unit {
    for (element in this) action(element)
}

/**
 * Performs the given [action] on each element, providing sequential index with the element.
 * @param [action] function that takes the index of an element and the element itself
 * and performs the action on the element.
 */
inline fun <T : Number> Matrix<T>.forEachIndexed(action: (index: Int, currVal: T) -> Unit): Unit {
    var index = 0
    for (item in this) action(index++, item)
}

/**
 * Performs the given [action] on each element, providing sequential index as an (i,j) pair with the element.
 * @param [action] function that takes the index of an element and the element itself
 * and performs the action on the element.
 */
inline fun <T : Number> Matrix<T>.forEachIndexed2d(action: (index: Pair<Int, Int>, currVal: T) -> Unit): Unit {
    var index = 0
    for (item in this) {
        action(this.convertIndexTo2d(index++), item)
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

    val row = Array(this.cols) { this[0, 0] }
    for(i in row.indices) {
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
    if (colNum > this.cols - 1 || colNum < 0)
        throw MatrixError.DimensionOutOfBounds(note = "Column at $colNum does not exist")

    val col = Array(this.rows) { this[0, 0] }
    col.forEachIndexed { i, _ ->
        col[i] = this[i, colNum]
    }
    return col
}

/**
 * Converts a matrix type Int, Float etc. to a matrix of type [Double]
 */
fun <T : Number> Matrix<T>.toDouble(): Matrix<Double> {
    val m = Matrix(Array(this.size) { 0.0 }, this.rows, this.cols)
    this.forEachIndexed2d { (i,j), t ->
        m[i, j] = t.toDouble()
    }
    return m
}

/**
 * Converts a matrix type Int, Float etc. to a matrix of type [Int]
 */
fun <T : Number> Matrix<T>.toInt(): Matrix<Int> {
    val m = Matrix(Array(this.size) { 0 }, this.rows, this.cols)
    this.forEachIndexed2d { (i,j), t ->
        m[i, j] = t.toInt()
    }
    return m
}

/**
 * Converts a matrix type Int, Float etc. to a matrix of type [Float]
 */
fun <T : Number> Matrix<T>.toFloat(): Matrix<Float> {
    val m = Matrix(Array(this.size) { 0f }, this.rows, this.cols)
    this.forEachIndexed2d { (i,j), t ->
        m[i, j] = t.toFloat()
    }
    return m
}

/**
 * Converts a matrix type Int, Float etc. to a matrix of type [Long]
 */
fun <T : Number> Matrix<T>.toLong(): Matrix<Long> {
    val m = Matrix(Array(this.size) { 0L }, this.rows, this.cols)
    this.forEachIndexed2d { (i,j), t ->
        m[i, j] = t.toLong()
    }
    return m
}

/**
 * Converts a matrix type Int, Float etc. to a matrix of type [Short]
 */
fun <T : Number> Matrix<T>.toShort(): Matrix<Short> {
    val m = Matrix<Short>(Array(this.size) { 0 }, this.rows, this.cols)
    this.forEachIndexed2d { (i,j), t ->
        m[i, j] = t.toShort()
    }
    return m
}
