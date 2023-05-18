@file:Suppress("UNCHECKED_CAST")

/**
 * An Error class that represents an error.
 * @param errorName The name of the error
 * @param displayText The text to be displayed when the error is thrown.
 */
private open class MatrixError(val errorName: String, val displayText: String) {
    fun throwError() {
        throw Error(displayText)
    }
    init {
        throwError()
    }
}

/**An error container class that contains all the Errors the libarey can throw out.*/
private sealed class Error {
    class MatrixSizeError :
        MatrixError("Matrix Size Error", "The size of the matrix, doesn't match the rows and columns")

    class UnsupportedType :
            MatrixError("Unsupported type", "Unsupported type found")

}

/**
 * A data class that contains a 1D matrix and its size information.
 * This is used to represent matrix as 1D list (The Matrix class treats a `matrix` as 2D array and does calculations with it)
 * @param matrix A flat list that contains the matrix elements
 * @param rows No of rows
 * @param cols Nof of cols
 */
data class Matrix1D<T>(val matrix: Array<T>, val rows: Int, val cols: Int)

/**
 * Matrix representation class that coronations/represents a single matrix.
 * This class holds the properties of the matrix such and no of rows, columns, size etc
 * This also holds different operation methods and other matrix methods
 * @param matrix1D A 1D [Array] of type uniform [Number] (Non-uniform numbers raise an error)
 * @param rows The number of rows of the matrix.
 * @param cols The number of columns of the matrix
 */
class Matrix<T: Number>(val matrix1D: Array<T>, val rows: Int, val cols: Int) {
    /**Creates a 2D array that `rows` rows and `cols` columns and fill each cell with 0;*/
    private val matrix2D = Array(rows) { Array(cols) { 0 as Number } }

    init {
        //Check if the size of the matrix values is in accordance with the given rows and cols value;
        if (rows * cols != matrix1D.size) {
            Error.MatrixSizeError()
        }
        //Packs the 1D matrix with the `rows` and `cols` information into a 2D matrix.
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                matrix2D[i][j] = matrix1D[i * cols + j] as T
            }
        }
    }

    /*--------General methods/properties--------*/
    /**
     * Getter function for accessing matrix by [i, j] format
     * @param i This denotes the i-th row of the matrix
     * @param j This denotes the j-th column of the matrix
     */
    operator fun get(i: Int, j: Int) = matrix2D[i][j] as T

    /**
     * Set a value specifc value of the matrix at (i, j) and returns the new value;
     * @param i the i-th position
     * @param j the j-th position
     * @param newValue the set value
     */
    operator fun set(i: Int, j: Int, newValue: T): T {
        matrix2D[i][j] = newValue
        return newValue
    }

    /**
     * Checks if a [Matrix] and an item is equal or not.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Matrix<*>
        if(this.size != other.size) return false
        return matrix1D.contentEquals(other.matrix1D)
    }
    /**Returns the matrix size**/
    val size = rows * cols

    /*--------Operators methods--------*/


    /*--------Utils methods--------*/
    /**
     * Converts the index of a 1D list to a 2D list (i,j)
     * @param index the index from the 1D list
     */
    private fun convertIndexTo2D(index: Int) = (index / cols) to (index % cols)

    /**
     * Returns the formatted matrix;
     */
    override fun toString(): String {
        var str = ""
        for (i in matrix2D) {
            var str_temp = ""
            for (cell in i) {
                str_temp += "$cell "
            }
            str += "$str_temp\n"
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
