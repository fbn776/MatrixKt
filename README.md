# MatrixKt

### Introduction
MatrixKt is a maths library for using matrices and matrix operations in Kotlin.
The documentation is available at ./docs.md

### Features
This library supports almost all the basics matrix operations like: 


`Addition`, `Subtraction`, `Multiplication`, `Scalar Multiplication`,` Negation`, `Transpose`, `Determinant`, `Inverse`, `Minors`, `Cofactors`, `Adjoint`, `Echelon forms`, `Rank`

The library also provide util function like:

`Individual row/column getters`, `Row/column transforms`, `Row/column swapping`, `Iterators`, `forEach`, `forEachIndexed` etc...

### Basic Usage
All the matrix operations are done using the `Matrix` class.
To create a matrix, use the following code;
```kotlin
import matrix.Matrix

val A = Matrix(arrayOf(
	1,2,
	3,4
), 2, 2)
```
This creates a 2x2 matrix.

### Accessing elements
For accessing the element at `(i,j)` use `A[i,j]`<br>
Where `A` is a Matrix.

	NOTE: indices start from 0.

The matrix generated is also mutable. You set a value of the element at `(i,j)` by
`A[i,j] = b`<br>
Where `A` is a Matrix, 
`b` is `Number`

	NOTE: The datatype of 'b' should be same as that of 'A'

### Basic Operations
For addition use `+`, for subtraction use `-`, for multiplication use `/`
Example:
```kotlin
val m1 = matrix.matrix(arrayOf(1,2,3,4), 2, 2)
val m2 = matrix.matrix(arrayOf(5,6,7,8), 2, 2)

val add = m1 + m2
val sub = m1 - m2
val mult = m1 * m3
val scalarMult = 2 * m1
val scalarDiv = m1 / 2
```

Operations are as simple as this!



