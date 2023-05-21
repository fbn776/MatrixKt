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
fun <T : Number> Matrix<T>.getMatrix2D(): Array<Array<Number>> {
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
fun <T : Number> Matrix<T>.convertIndexTo2d(index: Int) = (index / cols) to (index % cols)

/**
 * Converts a (i,j) of a 2D array into an index of a 1D array
 * @param i The i-th element of the outer array
 * @param j The j-th element of the inner arrays
 */
fun <T : Number> Matrix<T>.convert2dToIndex(i: Int, j: Int) = i * cols + j


//
///**
// * Performs the given [action] on each element.
// */
//inline fun <T: Number> Matrix<T>.forEach(action: (T) -> Unit): Unit {
//    for (element in this.getMatrix1D()) action(element)
//
//}
//
///**
// * Performs the given [action] on each element, providing sequential index with the element.
// * @param [action] function that takes the index of an element and the element itself
// * and performs the action on the element.
// */
//public inline fun <T> Iterable<T>.forEachIndexed(action: (index: Pair(<Int>, <Int>), T) -> Unit): Unit {
//    var index = 0
//    for (item in this) action(index++, item)
//    TODO("DO the forEachIndexed that returns an (i,j) pair")
//}

//inline fun <reified T : Number> Matrix<T>.getRow(rowAt: Int): Array<T> {
//    val row = Array(this.rows) { this[0, 0] }
//    row.forEachIndexed { i, v ->
//        row[i] = this[rowAt, i]
//    }
//    return row
//
//    TODO("Implemeaant the forEach")
//}
//
//inline fun <reified T : Number> Matrix<T>.getColumn(colAt: Int): Array<T> {
//    val col = Array(this.cols) { this[0, 0] }
//    col.forEachIndexed { i, _ ->
//        col[i] = this[i, colAt]
//    }
//    return col
//
//    TODO("Implemeaant the forEach")
//}
//
//inline fun <reified T : Number> Matrix<T>.getColumn(colAt: Int): Array<T> = this.transposed().rowAt(colAt)

