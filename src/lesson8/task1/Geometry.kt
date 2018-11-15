@file:Suppress("UNUSED_PARAMETER")
package lesson8.task1

import lesson1.task1.sqr
import lesson2.task1.triangleKind
import java.lang.IllegalArgumentException
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point): this(linkedSetOf(a, b, c))
    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double = TODO()

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment = TODO()

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle = TODO()

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double): this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point = TODO()

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line = TODO()

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = TODO()

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line = TODO()

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> = TODO()

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle = TODO()

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun middlePoint(p1: Point, p2: Point): Point = Point((p1.x / 2 + p2.x / 2), (p1.y / 2 + p2.y / 2))

fun halfDist (p1: Point, p2: Point) = p1.distance(p2) / 2

fun minContainingCircle(vararg points: Point): Circle {
    if (points.isEmpty()) throw IllegalArgumentException()
    val size = points.size
    if (size == 1) return Circle(points[0], 0.0)
    var currentCircle = Circle(middlePoint(points[0], points[1]), halfDist(points[0], points[1]))
    for (i in 2 until size) {
        val it = points[i]
        if (!currentCircle.contains(it)) {
            currentCircle = findSecondEdgePoint(points.take(i), it)
        }
    }
    println(currentCircle)
    return currentCircle
}

fun findSecondEdgePoint(points: List<Point>, firstEdgePoint: Point): Circle {
    val size = points.size
    var currentCircle = Circle(middlePoint(points[0], firstEdgePoint), halfDist(points[0], firstEdgePoint))
    for (i in 1 until size) {
        val it = points[i]
        if (!currentCircle.contains(it)) {
            currentCircle = findThirdEdgePoint(points.take(i), firstEdgePoint, it)
        }
    }
    return currentCircle
}

fun findThirdEdgePoint(points: List<Point>, firstEdgePoint: Point, secondEdgePoint: Point): Circle {
    val size = points.size
    var currentCircle = Circle(middlePoint(firstEdgePoint, secondEdgePoint), halfDist(firstEdgePoint, secondEdgePoint))
    for (i in 0 until size) {
        val it = points[i]
        if (!currentCircle.contains(points[i])) {
            currentCircle = findCircumcircleOfTriangle(firstEdgePoint, secondEdgePoint, it)
        }
    }
    return currentCircle
}

fun findCircumcircleOfTriangle(p1: Point, p2: Point, p3: Point): Circle {
    val a = p1.distance(p2)
    val b = p1.distance(p3)
    val c = p2.distance(p3)
    if (triangleKind(a, b, c) == -1) {
        return Circle(Point((p1.x + p2.x + p3.x) / 3, (p1.y + p2.y + p3.y) / 3), maxOf(a, b, c) / 2)
    }
    val radius = a * b * c / 4 / Triangle(p1, p2, p3).area()
    // Уравнения прямых серединных перпендикуляров
    val p12y = p2.y - p1.y
    val p12x = p2.x - p1.x
    val k1 = -1 * p12x / p12y
    var middlePoint = middlePoint(p2, p1)
    val m1 = middlePoint.y - k1 * middlePoint.x
    val p13y = p3.y - p1.y
    val p13x = p3.x - p1.x
    val k2 = -1 * p13x / p13y
    middlePoint = middlePoint(p3, p1)
    val m2 = middlePoint.y - k2 * middlePoint.x
    val m = (m2 - m1)
    val k = k1 - k2
    val x = m / k
    val center = Point(x, k1 * x + m1)
    return Circle(center, radius)
}