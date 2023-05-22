/**
 * Matrix representation class that coronations/represents a single matrix.
 * This class holds the properties of the matrix such and no of rows, columns, size etc.
 * This also holds different operation methods and other matrix methods
 * @param _m A 1D [Array] of type uniform [Number] (Non-uniform numbers raise an error)
 * @param rows The number of rows of the matrix.
 * @param cols The number of columns of the matrix
 */
class Matrix<T : Number>(private val _m: Array<T>, val rows: Int, val cols: Int) : Iterator<T> {
    /**This is the main matrix array. Operations are done on this array.
     * This is a copy of the passed array as when changing the non-copied array [_m] can change the original array that passed as an argument
     */
    private val _matrix1D = _m.copyOf()

    /**Returns the matrix size**/
    val size = rows * cols

    init {
        //Check if the size of the matrix values is in accordance with the given rows and cols value;
        if (rows * cols != _matrix1D.size) {
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

        return _matrix1D[convert2dToIndex(i, j)]
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

        return _matrix1D[i]
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

        _matrix1D[convert2dToIndex(i, j)] = newValue
        return newValue
    }

    /**Returns the 1D matrix array*/
    fun getMatrix1D() = _matrix1D

    /**Returns the copy of 2D matrix array*/
    fun getClonedMatrix2D() = _matrix1D.copyOf()

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
        return other in _matrix1D
    }

    /**
     * Returns the formatted matrix;
     */
    override fun toString(): String {
        var str = "\n"
        for (i in 0 until rows) {
            var rowStr = ""
            for (j in 0 until cols) {
                rowStr += "${_matrix1D[i * cols + j]} "
            }
            str += rowStr + '\n'
        }
        return str
    }

    override fun hashCode(): Int {
        var result = _matrix1D.contentHashCode()
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

        val element = _matrix1D[currentIndex]
        currentIndex++
        return element
    }

    /*--------Matrix Companion Object--------*/
    companion object {
        /**
         * Converts an index of a 1D array to 2D array indies of the form (i, j)
         * @param index The index of the 1D array
         * @param cols The size of the outer array of the 2D array
         * @return A pair of indices (i, j) of the 2D array
         */
        fun convertIndexTo2d(index: Int, cols: Int) = (index / cols) to (index % cols)

        /**
         * Returns a new [Matrix] of type [E] of the size [noRows] x [noCols] that's filled with elements given by [elements]
         * @param noRows The number of rows of the matrix
         * @param noCols The number of columns of the matrix
         * @param elements The elements to fill the matrix with. The index of the element is passed as an argument.
         */
        inline fun <reified E : Number> matrixOf(noRows: Int, noCols: Int, elements: (i: Int) -> E): Matrix<E> {
            if (noRows <= 0 || noCols <= 0)
                throw MatrixError.SizeInvalid(note = "Size ($noRows, $noCols) is invalid")

            var count = 0
            return Matrix(Array(noRows * noCols) { elements(count++) }, noRows, noCols)
        }


        /**
         * Returns a new [Matrix] of type [E] of the size [noRows] x [noCols] that's filled with elements given by [elements]
         * @param noRows The number of rows of the matrix
         * @param noCols The number of columns of the matrix
         * @param elements The elements to fill the matrix with.
         */
        inline fun <reified E : Number> matrixOfIndexed(noRows: Int, noCols: Int, elements: (i: Int, cord: Pair<Int, Int>) -> E): Matrix<E> {
            if (noRows <= 0 || noCols <= 0)
                throw MatrixError.SizeInvalid(note = "Size ($noRows, $noCols) is invalid")

            var count = 0
            return Matrix(Array(noRows * noCols) {
                val pair = Matrix.convertIndexTo2d(count, noCols)
                elements(count++, pair)
            }, noRows, noCols)
        }


        /**
         * Returns a new zero matrix of type [Int] thats of the size [noRows] x [noCols].
         * To get the zero matrix of other types use [matrixOf] with element that returns 0 of that type.
         * Or use [Matrix] type converters. Like [Matrix.toIntMatrix] or [Matrix.toDoubleMatrix] etc.
         * @param noRows The number of rows of the matrix.
         * @param noCols The number of columns of the matrix.
         * @return A matrix with all elements as 0 (Int).
         * @exception MatrixError.SizeInvalid Thrown when the size of the matrix is invalid.
         */
        fun zeroMatrix(noRows: Int, noCols: Int) = matrixOf(noRows, noCols) { 0 }

        /**
         * Returns the identity matrix of the a given size.
         * @param size The size of the identity matrix.
         * @return The a square matrix/identity of type [Int]
         * @exception MatrixError.SizeInvalid Thrown when the size of the matrix is invalid.
         */
        fun identityMatrix(size: Int): Matrix<Int> {
            return matrixOf(size, size) { i ->
                if ((i) % (size + 1) == 0) 1 else 0
            }
        }

        /**
         * This returns a new square matrix of size [size] all the all the diagonal elements as [diagonalElement], rest as 0.0. This
         * @param size The size of the matrix.
         * @param diagonalElement The value of the diagonal elements. (Its a double because of diffculties in type conversion for generic types)
         * @return A square(size×size) matrix of type [Double] with all diagonal elements as [diagonalElement] and rest as 0.0
         * @exception MatrixError.SizeInvalid Thrown when the size of the matrix is invalid.
         */
        fun diagonalMatrix(size: Int, diagonalElement: Double): Matrix<Double> {
            var i = 0
            return matrixOf(size, size) {
                if ((i++) % (size + 1) == 0) diagonalElement else 0.0
            }
        }

//        fun upperTriangleMatrix(size: Int, primary: Double, secondary: Double = 0.0): Matrix<Double> {
//            val elm
//        }
    }
}
