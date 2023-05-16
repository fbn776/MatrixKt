data class MatrixError(val errorName: String, val hasError: Boolean, val displayText: String) {
    fun throwError() {
        throw Error(displayText)
    }
}

class Matrix(values: List<Number>, rows: Int, cols: Int) {
    private val matrixSizeError = MatrixError("Matrix Size Error", false, "The size of the matrix, doesn't match the rows and columns")

    init {
        //Check if
        if(rows * cols != values.size) {
            matrixSizeError.throwError()
        }

        for (j in 1..cols) {
            val column = mutableListOf<Number>()
            for(i in 1..rows) {
                column.add(values[i])
            }
        }
    }
}