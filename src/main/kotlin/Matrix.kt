/**
 * A data class that contains a 1D matrix and its size information.
 * This is used to represent matrix as 1D list (The Matrix class treats a `matrix` as 2D array and does calculations with it)
 * @param matrix A flat list that contains the matrix elements
 * @param rows No of rows
 * @param cols Nof of cols
 */
data class Matrix1D<T>(val matrix: Array<T>, val rows: Int, val cols: Int)

/**An error container class that contains all the Errors the library can throw out.*/
private sealed class Error {
    class MatrixSizeError(msg: String = "The size of the matrix does not match the row and column count.") :
        Exception(msg)

    class MatrixIndexOutOfBound(msg: String = "Tried to access a value with index [i, j] that's outside of the index bounds") :
        Exception(msg)
}

/**
 * Matrix representation class that coronations/represents a single matrix.
 * This class holds the properties of the matrix such and no of rows, columns, size etc.
 * This also holds different operation methods and other matrix methods
 * @param m A 1D [Array] of type uniform [Number] (Non-uniform numbers raise an error)
 * @param rows The number of rows of the matrix.
 * @param cols The number of columns of the matrix
 */
class Matrix<T : Number>(private val m: Array<T>, val rows: Int, val cols: Int) {
    /**This is the main matrix array. Operations are done on this array.
     * This is a copy of the passed array as when changing the non-copied array [m] can change the original array that passed as an argument
     */
    private val matrix1D = m.copyOf()

    init {
        //Check if the size of the matrix values is in accordance with the given rows and cols value;
        if (rows * cols != matrix1D.size) {
            throw Error.MatrixSizeError()
        }
    }

    /*--------General methods/properties--------*/
    /**
     * Getter function for accessing matrix by [i, j] format
     * @param i This denotes the i-th row of the matrix
     * @param j This denotes the j-th column of the matrix
     */
    operator fun get(i: Int, j: Int): T {
        if ((i < 0) || (i > rows - 1) || (j < 0) || (j > cols - 1))
            throw Error.MatrixIndexOutOfBound()

        return matrix1D[convert2dToIndex(i, j)]
    }

    /**
     * Set a value specific value of the matrix at (i, j) and returns the new value;
     * @param i the i-th position
     * @param j the j-th position
     * @param newValue the set value
     */
    operator fun set(i: Int, j: Int, newValue: T): T {
        if ((i < 0) || (i > rows - 1) || (j < 0) || (j > cols - 1))
            throw Error.MatrixIndexOutOfBound()

        matrix1D[convert2dToIndex(i, j)] = newValue
        return newValue
    }

    /**
     * Checks if a [Matrix] and an item is equal or not.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Matrix<*>
        if (this.rows != other.rows || this.cols != other.cols) return false
        return matrix1D.contentEquals(other.matrix1D)
    }

    /**
     * Check if two matrices are of same size.
     * @param other the other matrix to do the check
     * @return true if rows and cols of both matrices are equal, else false
     */
    fun isOfSameSize(other: Matrix<*>) = (this.rows == other.rows && this.cols == other.cols)

    /**Returns the matrix size**/
    val size = rows * cols
    /*--------Operators methods--------*/
    operator fun plus(other: Matrix<*>) {

    }

    /*--------Utils methods--------*/
    /**Returns the 1D matrix array*/
    fun getMatrix1D() = matrix1D

    /**Returns the 2D matrix array*/
    fun getMatrix2D(): Array<Array<Number>> {
        val matrix2D = Array(rows) { Array(cols) { 0 as Number } }
        //Packs the 1D matrix with the `rows` and `cols` information into a 2D matrix.
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                matrix2D[i][j] = matrix1D[i * cols + j] as T
            }
        }
        return matrix2D
    }

    /**
     * Converts the index of a 1D list to a 2D list (i,j)
     * @param index the index from the 1D list
     */
    private fun convertIndexTo2d(index: Int) = (index / cols) to (index % cols)

    /**
     * Converts a (i,j) of a 2D array into an index of a 1D array
     * @param i The i-th element of the outer array
     * @param j The j-th element of the inner arrays
     */
    private fun convert2dToIndex(i: Int, j: Int) = i * cols + j

    /**
     * Returns the formatted matrix;
     */
    override fun toString(): String {
        var str = "\n"
        for (i in 0 until rows) {
            var rowStr = ""
            for (j in 0 until cols) {
                rowStr += "${matrix1D[i * cols + j]} "
            }
            str += rowStr + '\n'
        }
        return str
    }

    /**Flattens the matrix(2D array) to a 1D matrix array
     * @return [Matrix1D] Returns the a [Matrix1D] data class that contains flattened 1D array, rows, columns.
     **/
    fun flatten() = Matrix1D(matrix1D, rows, cols)

    override fun hashCode(): Int {
        var result = matrix1D.contentHashCode()
        result = 31 * result + rows
        result = 31 * result + cols
        return result
    }
}
