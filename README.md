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
You add, subtract, cross multiply two matrices. You can use `+`, `-` and `*` for the operations.
Example:
```kotlin
import matrix.Matrix
import matrix.minus
import matrix.plus
import matrix.times

val A = Matrix(arrayOf(
    1,2,3,
    4,5,6,
    7,8,9
), 3, 3)
val B = Matrix(arrayOf(
    4,3,1,
    8,9,4,
    1,3,5
), 3, 3)

val C = A + B
val D = A - B
val E = A * B
```
Here `C` holds the matrix sum of `A` and `B`<br>
`D` holds the matrix subtraction of `A` and `B`<br>
`E` holds the matrix cross product between `A` and `B`

Operations are as simple as this!

You can also do stuff like finding the transpose, minor matrix, cofactor, adjoint, determinant, row and column transforms, rank etc.

For the full docs read .....



