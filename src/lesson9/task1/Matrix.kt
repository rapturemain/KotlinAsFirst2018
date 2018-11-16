@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson9.task1

import java.lang.IllegalArgumentException

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int
    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E)

    /** ------------------------------------------------------------ **/

    val container: MutableList<MutableList<E>>

    /** ------------------------------------------------------------- **/

    fun inside(row: Int, column: Int): Boolean
    fun inside(cell: Cell): Boolean

    fun isEqual(row: Int, column: Int, value: E): Boolean
    fun isEqual(cell: Cell, value: E): Boolean
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if ((height <= 0) && (width <= 0)) throw IllegalArgumentException()
    return MatrixImpl(height, width, e)
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {

    private val empty = null

    override val container = MutableList(height) { MutableList(width) { e } }

    override fun get(row: Int, column: Int): E =
            when {
                (!inside(row, column)) -> throw IndexOutOfBoundsException()
                (container[row][column] == null) -> throw NoSuchElementException()
                else -> container[row][column]
            }

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        if (!inside(row, column)) throw IndexOutOfBoundsException()
        container[row][column] = value
    }

    override fun set(cell: Cell, value: E) {
        set(cell.row, cell.column, value)
    }

    override fun equals(other: Any?) =
            ((other is Matrix<*>) &&
            (height == other.height) &&
            (width == other.width) &&
            (container == other.container))

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + container.hashCode()
        return result
    }

    override fun toString(): String = TODO()

    override fun inside(row: Int, column: Int): Boolean = ((row in 0..height) && (column in 0..width))
    override fun inside(cell: Cell): Boolean = inside(cell.row, cell.column)

    override fun isEqual(row: Int, column: Int, value: E): Boolean = (get(row, column) == value)
    override fun isEqual(cell: Cell, value: E): Boolean = isEqual(cell.row, cell.column, value)
}

