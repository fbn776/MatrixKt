/**
 * Matrix representation class that coronations/represents a single matrix.
 * This class holds the properties of the matrix such and no of rows, columns, size etc.
 * This also holds different operation methods and other matrix methods
 * @param m A 1D [Array] of type uniform [Number] (Non-uniform numbers raise an error)
 * @param rows The number of rows of the matrix.
 * @param cols The number of columns of the matrix
 */
class Matrix<T : Number>(private val m: Array<T>, val rows: Int, val cols: Int) : Iterator<T> {
    /**This is the main matrix array. Operations are done on this array.
     * This is a copy of the passed array as when changing the non-copied array [m] can change the original array that passed as an argument
     */
    private val matrix1D = m.copyOf()
    /**Returns the matrix size**/
    val size = rows * cols

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
        if (i > size || i < 0)
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

    /**Returns the 1D matrix array*/
    fun getMatrix1D() = matrix1D

    /**Returns the copy of 2D matrix array*/
    fun getClonedMatrix2D() = matrix1D.copyOf()

    /**
     * Checks if a [Matrix] and an item is equal or not.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Matrix<*>
        if (this.rows != other.rows || this.cols != other.cols) return false
        return this.getMatrix1D().contentEquals(other.getMatrix1D())
    }

    /**
     * Checks if a [Matrix] contains an item or not.
     * @return true if the item is present in the matrix, false otherwise.
     */
    operator fun contains(other: Any?): Boolean {
        if (other !is Number) return false
        return other in matrix1D
    }

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

    /*--------Matrix Iterator--------*/
    private var currentIndex = 0
    override fun hasNext() = (currentIndex < size)
    override fun next(): T {
        if (!hasNext())
            throw MatrixError.NoSuchElement()

        val element = matrix1D[currentIndex]
        currentIndex++
        return element
    }

    /*--------Matrix Companion Object--------*/
    companion object {
        /**
         * Returns a new [Matrix] of type [E] and size [noRows] x [noCols] abd filled with [elements]
         * @param noRows The number of rows of the matrix
         * @param noCols The number of columns of the matrix
         * @param elements The elements to fill the matrix with.
         */
        inline fun <reified E: Number> typedMatrixOf(noRows: Int, noCols: Int, elements: () -> E): Matrix<E> {
            if(noRows <= 0 || noCols <= 0)
                throw MatrixError.SizeInvalid(note = "Size ($noRows, $noCols) is invalid")

            return Matrix(Array(noRows * noCols) { elements() }, noRows, noCols)
        }
    }
}
