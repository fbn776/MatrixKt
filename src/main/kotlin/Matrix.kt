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
    class MatrixSizeError: MatrixError("Matrix Size Error", "The size of the matrix, doesn't match the rows and columns")

}


class Matrix<T: Number>(val matrix1D: List<T>, rows: Int, cols: Int) {
    /**Creates a 2D array that `rows` rows and `cols` columns and fill each cell with 0;*/
    val matrix2D = Array(rows) { Array(cols) { 0 as Number} }

    init {
        //Check if the size of the matrix values is in accordance with the given rows and cols value;
        if (rows * cols != matrix1D.size) {
            Error.MatrixSizeError()
        }
        //Packs the 1D matrix with the `rows` and `cols` information into a 2D matrix.
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                matrix2D[i][j] = matrix1D[i * cols + j].toDouble()
            }
        }
    }


    /**
     * Getter function for accessing matrix by [i, j] format
     * @param i This denotes the i-th row of the matrix
     * @param j This denotes the j-th column of the matrix
     */
    operator fun get(i: Int, j: Int) = matrix2D[i][j]

    /**
     * Returns the formatted matrix;
     */
    override fun toString(): String {
        var str = ""
        for(i in matrix2D) {
            var str_temp = ""
            for(cell in i) {
                str_temp += "$cell "
            }
            str += "$str_temp\n"
        }
        return str
    }

    /**Private functions**/
}