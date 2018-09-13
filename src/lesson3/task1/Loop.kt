@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int{
    var sum = 0
    var number = n
    if (n == 0) return 1
    while (number != 0){
        number/= 10
        sum++
    }
    number = n
    if (number % 10 == 0) {
        var count = 1
        while (number % power(10, count) == 0) count++
        sum += count
    }
    return sum
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    if (n == 1) return 1 else
        if (n == 2) return 1 else
            return fib(n-2)+fib(n-1)
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun isEven (n: Int) = (n % 2 == 0)

fun gcd(m: Int, n: Int):Int {
    when (m){
        0 -> return n
        1 -> return 1
    }
    when (n){
        0 -> return m
        1 -> return 1
    }
    if (m == n) return m
    if ((isEven(m))&&(isEven(n))) return 2*gcd(m/2, n/2)
    if ((isEven(m))&&(!isEven(n))) return gcd(m/2, n)
    if ((!isEven(m))&&(isEven(n))) return gcd(m, n/2)
    if ((!isEven(m))&&(!isEven(n))&&(n > m)) return gcd((n-m)/2, m)
    if ((!isEven(m))&&(!isEven(n))&&(n < m)) return gcd((m-n)/2, n)
    return -1
}

fun lcm(m: Int, n: Int): Int = abs(m*n)/gcd(m,n)

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int{
    for (i in 2..n/2){
        if (n % i == 0) return i
    }
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int = n / minDivisor(n)

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean = (gcd(m,n) == 1)

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    val lo = sqrt(m.toDouble()).toInt()
    val hi = sqrt(n.toDouble()).toInt()
    if ((lo*lo == m)||(hi*hi == n)) return true
    if (hi-lo>0) return true
    return false
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps2(x: Int, count: Int): Int {
    if (x == 1) return count else
    if(isEven(x)) return collatzSteps2(x/2, count + 1) else
        return collatzSteps2(3*x+1, count + 1)
}

fun collatzSteps(x: Int): Int {
    return collatzSteps2(x, 0)
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun power(n: Double, count:Int):Double{
    if (n == 0.0) return 0.0
    var buffer:Double = 1.0
    for (i in 1..count) buffer *= n
    return buffer
}

fun power(n: Int, count:Int):Int{
    if (n == 0) return 0
    var buffer:Int = 1
    for (i in 1..count) buffer *= n
    return buffer
}


fun sin(x: Double, eps: Double): Double{
    var n: Double = 9999999.0
    var count = -1
    var realCount = 0
    var buffer: Double = 0.0
    while (abs(n) >= eps){
        count += 2
        realCount++
        n = power(x, count)
        n /= factorial(count)
        if (isEven(realCount)) buffer -= n else
            buffer += n
    }
    return buffer
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var n: Double = 9999999.0
    var count = 0
    var realCount = 1
    var buffer: Double = 1.0
    while (abs(n) >= eps){
        count += 2
        realCount++
        n = power(x, count)
        n /= factorial(count)
        if (isEven(realCount)) buffer -= n else
            buffer += n
    }
    return buffer
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int{
    var number = n
    var swap = 0
    var count = digitNumber(n)
    for (i in 1..count){
        swap += power(10,( count - i )) * ( number % 10 )
        number /= 10
    }
    return swap
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = ( n == revert(n) )

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean{
    val last = n % 10
    var number = n
    if (n == 0) return true
    while (number != 0){
        if (number % 10 != last) return false
        number /= 10
    }
    return true
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int = TODO()

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int = TODO()
