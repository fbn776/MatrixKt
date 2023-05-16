data class MatrixError(val errorName: String, val hasError: Boolean, val displayText: String) {
    fun throwError() {
        throw Error(displayText)
    }
}

class Matrix<T: Number>(matrix1D: List<T>, rows: Int, cols: Int) {
    private val matrixSizeError =
        MatrixError("Matrix Size Error", false, "The size of the matrix, doesn't match the rows and columns")

    //Create a 2D array that `rows` rows and `cols` columns and fill each cell with 0;
    val matrix2D = Array(rows) { Array<Number>(cols) { 0 } }

    init {
        //Check if the size of the matrix values is in accordance with the given rows and cols value;
        if (rows * cols != matrix1D.size) {
            matrixSizeError.throwError()
        }
        //Packs the 1D matrix with the `rows` and `cols` information into a 2D matrix.
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                matrix2D[i][j] = matrix1D[i * cols + j]
            }
        }
    }

    //A formatted matrix string;
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
}