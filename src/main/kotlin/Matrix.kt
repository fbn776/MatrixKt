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
            throw MatrixError.SizeError()
        }
    }

    /*--------General methods/properties--------*/
    /**
     * Getter function for accessing matrix by [i, j] format
     * @param i This denotes the i-th row of the matrix
     * @param j This denotes the j-th column of the matrix
     * @return This returns the value of the matrix at (i, j)
     */
    operator fun get(i: Int, j: Int): T {
        if ((i < 0) || (i > rows - 1) || (j < 0) || (j > cols - 1))
            throw MatrixError.IndexOutOfBound()

        return matrix1D[convert2dToIndex(i, j)]
    }

    /**
     * Returns the ith element of the matrix
     * @param i The position of element to be returned.
     * @return element at ith position
     * @exception MatrixError.IndexOutOfBound Thrown when the passed index is out of bounds.
     */
    fun getValueAtIndex(i: Int): T {
        if(i > size || i < 0)
            throw MatrixError.IndexOutOfBound()

        return matrix1D[i]
    }

    /**
     * Set a value specific value of the matrix at (i, j) and returns the new value;
     * @param i the i-th position
     * @param j the j-th position
     * @param newValue the set value
     */
    operator fun set(i: Int, j: Int, newValue: T): T {
        if ((i < 0) || (i > rows - 1) || (j < 0) || (j > cols - 1))
            throw MatrixError.IndexOutOfBound()

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
     * Clones a matrix and returns it.
     */
    fun clone(): Matrix<T> = Matrix(this.matrix1D.copyOf(), rows, cols)

    /**
     * Check if two matrices are of same size.
     * @param other the other matrix to do the check
     * @return true if rows and cols of both matrices are equal, else false
     */
    fun isOfSameSize(other: Matrix<*>) = (this.rows == other.rows && this.cols == other.cols)

    /**
     * Checks if two matrix can be cross multiplied or not
     * @return If two matrix A(n×m) and B(p×q) can be multiplied not. ie checks for m = p
     */
    fun canMult(other: Matrix<*>) = (this.cols == other.rows)

    /**
     * Checks if a matrix is square or not.
     * @return true if the matrix is square, else false
     */
    fun isSquare() = (this.rows == this.cols)

    /**Returns the matrix size**/
    val size = rows * cols

    /**Returns the 1D matrix array*/
    fun getMatrix1D() = matrix1D


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

    override fun hashCode(): Int {
        var result = matrix1D.contentHashCode()
        result = 31 * result + rows
        result = 31 * result + cols
        return result
    }
}

