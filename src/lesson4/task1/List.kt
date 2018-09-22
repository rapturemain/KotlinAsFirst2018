@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.minDivisor
import java.io.File.separator
import java.lang.Math.pow
import kotlin.math.*

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.fold(0.0) {
    previousResult, element -> previousResult + sqr(element)
})

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double =
    if (list.isEmpty()) 0.0
    else list.sum() / list.size


/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val average = mean(list)
    for (i in 0 until list.size) {
        list[i] -= average
    }
    return  list
    }

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double = a.zip(b).fold(0.0) {
    previousElement, (element1, element2) -> previousElement + element1*element2
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double = p.withIndex().fold(0.0) {
    previousResult, (index, element) -> previousResult + element * pow(x, index.toDouble())
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> =
    if (list.isEmpty()) list
        else{
        var sum = list[0]
        for (i in 1 until list.size) {
            sum += list[i]
            list[i] = sum
        }
        list
    }

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    val list = mutableListOf<Int>()
    var number = n
    var min: Int
    while (number != 1) {
        min = minDivisor(number)
        list.add(min)
        number /= min
    }
    return list
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")




/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */

fun convert(n: Int, base: Int): List<Int> {
    var number = n
    val list = mutableListOf<Int>()
    while (number >= base) {
        list.add(number % base)
        number /= base
    }
    list.add(number)
    return list.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    val list = convert(n, base)
    return list.joinToString("") {
        it -> if (it > 9) (it + 87).toChar().toString()
        else it.toString()
    }
}


/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var number = 0
    for ((index, element) in digits.withIndex()) {
        number += element * pow(base.toDouble(), (digits.size - 1 - index).toDouble()).toInt()
    }
    return number
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    var number = 0
    for ((index, element) in str.withIndex()) {
        number += if (element.toInt() > 96) {
                  (element.toInt() - 87) * pow(base.toDouble(), (str.length - 1 - index).toDouble()).toInt()
                  } else {
                  (element.toInt() - 48) * pow(base.toDouble(), (str.length - 1 - index).toDouble()).toInt()
                  }
    }
    return number
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */

fun roman(n: Int): String {
    val list = mutableListOf<String>()
    val sym = mutableListOf("I", "V", "X", "L", "C", "D", "M")
    var count = 0
    var number = n
    while (count < 5) {
        when (number % 10) {
            1 -> list.add(sym[count])
            2 -> list.add(sym[count] + sym[count])
            3 -> list.add(sym[count] + sym[count] + sym[count])
            4 -> list.add(sym[count] + sym[count + 1])
            5 -> list.add(sym[count + 1])
            6 -> list.add(sym[count + 1] + sym[count])
            7 -> list.add(sym[count + 1] + sym[count] + sym[count])
            8 -> list.add(sym[count + 1] + sym[count] + sym[count] + sym[count])
            9 -> list.add(sym[count] + sym[count + 2])
        }
        count += 2
        number /= 10
    }
    for (i in 1..number) list.add(sym[count])
    return list.reversed().joinToString("")
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */

fun russian(n: Int): String{
    val list = mutableListOf<String>()
    val sym = mutableListOf("", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять", "десять"
            , "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать"
            , "восемнадцать", "девятнадцать", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят"
            , "восемьдесят", "девяносто", "", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот"
            , "восемьсот", "девятьсот", "тысяча", "тысячи", "тысяч", "", "одна", "две", "три", "четыре", "пять"
            , "шесть", "семь", "восемь", "девять", "десять")
    val dec = 18
    val hun = 28
    val tho = 38
    val hundec = 41
    var number = n % 1000
    if ((number % 100)<20){
        list.add(sym[number % 100])
        list.add(sym[(number / 100) + hun])
    } else {
            list.add(sym[number % 10])
            number /= 10
            if (number % 10 != 0)
            list.add(sym[(number % 10) + dec])
            number /= 10
            list.add(sym[(number % 10) + hun])
    }
    number = n / 1000
    if (number > 0) {
        if (number % 100 in 5..20) {
            list.add(sym[tho + 2])
        } else
            when (number % 10) {
                0 -> list.add(sym[tho + 2])
                1 -> list.add(sym[tho])
                2 -> list.add(sym[tho + 1])
                3 -> list.add(sym[tho + 1])
                4 -> list.add(sym[tho + 1])
                5 -> list.add(sym[tho + 2])
                6 -> list.add(sym[tho + 2])
                7 -> list.add(sym[tho + 2])
                8 -> list.add(sym[tho + 2])
                9 -> list.add(sym[tho + 2])
            }
        if (number % 100 in 10..19) {
            list.add(sym[number % 100])
            list.add(sym[(number / 100) + hun])
        } else {
            number = n / 1000
            list.add(sym[(number % 10) + hundec])
            number /= 10
            if (number % 10 != 0)
            list.add(sym[(number % 10) + dec])
            number /= 10
            list.add(sym[(number % 10) + hun])
        }
    }
    return list.filter{it != ""}.reversed().joinToString(" ")
}