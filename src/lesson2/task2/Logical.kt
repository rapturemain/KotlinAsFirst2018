@file:Suppress("UNUSED_PARAMETER")
package lesson2.task2

import lesson1.task1.sqr
import kotlin.math.abs
import kotlin.system.measureTimeMillis

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
        sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int): Boolean {
    var first = number / 1000 + ( number / 100 ) % 10
    var second = number % 10 + ( number % 100 ) / 10
    return (first == second)
}

/**
 * Простая
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
fun isUnder(kingX: Int, kingY: Int,
            rookX: Int, rookY: Int): Boolean {
    var under = false
    if ((kingX == rookX)||(kingY == rookY)) under = true
    if (abs(kingX-rookX) == abs(kingY - rookY)) under = true
    return under
}

fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    return isUnder(x1, y1, x2, y2)
}


/**
 * Простая
 *
 * Дан номер месяца (от 1 до 12 включительно) и год (положительный).
 * Вернуть число дней в этом месяце этого года по григорианскому календарю.
 */
fun daysInMonth(month: Int, year: Int): Int{
    when (month){
        1 -> return 31
        3 -> return 31
        4 -> return 30
        5 -> return 31
        6 -> return 30
        7 -> return 31
        8 -> return 31
        9 -> return 30
        10 -> return 31
        11 -> return  30
        12 -> return 31
    }
    if (month == 2){
        if ((year % 4 == 0)&&((year % 100 != 0)||(year % 400 == 0))) return 29 else
            return 28
    }
    return -2
}

/**
 * Средняя
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(x1: Double, y1: Double, r1: Double,
                 x2: Double, y2: Double, r2: Double): Boolean{
    if (r1>r2) return false else
    return pointInsideCircle(x1, y1, x2, y2, r2-r1)
}

/**
 * Средняя
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
fun minimum(a: Int, b: Int):Int{
    if (a < b) return a else
        return b
}

fun maximum(a: Int, b: Int):Int{
    if (a > b) return a else
        return b
}

fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean{
    val min: Int
    val first: Int
    if ((c <= a)&&(c <= b)) {
        min = c
        first = minimum(a,b)
    } else
        if ((b <= a)&&(b <= c)) {
            min= b
            first = minimum(a,c)
        } else {
            min = a
            first = minimum(b,c)
        };
    val wallFirst = minimum(r,s)
    val wallSecond = maximum(r,s)
    return ((min <= wallFirst)&&(first <= wallSecond))
}
