@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import jdk.nashorn.internal.objects.Global.Infinity


/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
        shoppingList: List<String>,
        costs: Map<String, Double>): Double {
    var totalCost = 0.0
    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
        phoneBook: MutableMap<String, String>,
        countryCode: String) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
        text: List<String>,
        vararg fillerWords: String): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val buffer = mutableMapOf<String, String>()
    mapA.forEach {
        key, value ->
        if ((mapB.containsKey(key)) && (value != mapB[key])) buffer[key] = "$value, ${ mapB[key] }"
        else buffer[key] = value
    }
    mapB.forEach {
        key, value ->
        if (!buffer.containsKey(key)) buffer[key] = value
    }
    return buffer
}

/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun swap(list: MutableList<String>, lo: Int, hi: Int):MutableList<String> {
    val left = list[lo]
    list[lo] = list[hi]
    list[hi] = left
    return list
}

fun isAlphabit(left: String, right: String): Boolean {
    if (left == "") return true
    if (right == "") return false
    var i = 0
    while (left[i] == right[i]){
        i++
        if ((i > left.length - 1) && (i > right.length - 1)) return true
        if (i > right.length - 1) return true
        if (i > left.length - 1) return false
    }
    return (left[i] < right[i])
}
fun sortListOfString(list: MutableList<String>) = sortListOfString(list, 0, list.size - 1).reversed().toMutableList()

fun sortListOfString(list: MutableList<String>, l: Int, h: Int):MutableList<String> {
    if (l == h) return list
    if (h - l == 1) return if (!isAlphabit(list[l], list[h])) swap(list, l, h) else list
    val base = list[l]
    var lo = l
    var hi = h
    var buffer = list
    while (lo < hi){
        while ((isAlphabit(list[lo], base)) && (lo < hi)) lo++
        while ((isAlphabit(base, list[hi])) && (lo < hi)) hi--
        if (lo != hi) {
            buffer = swap(buffer, lo, hi)
            lo++
            hi--
        }
    }
    swap(buffer, l, lo)
    if (l < hi) buffer = sortListOfString(buffer, l, hi)
    if (h > lo) buffer = sortListOfString(buffer, lo, h)
    return buffer
}

fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val map = mutableMapOf<Int, MutableList<String>>()
    grades.forEach {
        value, key ->
        if (!map.containsKey(key)) map[key] = emptyList<String>().toMutableList()
        map[key]?.add(value)
    }
    map.forEach{
        key, value ->
        map[key] = sortListOfString(value)
    }
    return map.toSortedMap(compareBy<Int> { it }.reversed())
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean = a.all { (key, value) ->
    b[key] == value
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val map = mutableMapOf<String, Double>()
    val buffer = mutableMapOf<String, Pair<Double, Int>>()
    stockPrices.forEach {
        (key, value) ->
        if (buffer.containsKey(key)) {
            val (first, second) = buffer.getValue(key)
            buffer.replace(key, (first to second), (first + value to second + 1))
        } else buffer[key] = value to 1
    }
    buffer.forEach {
        key, value ->
        map[key] = value.first / value.second
    }
    return map
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var max = Infinity
    var buffer: String? = null
    stuff.forEach {
        key, (type, cost) ->
        if ((type == kind) && (max >= cost)) {
            buffer = key
            max = cost
        }
    }
    return buffer
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */

fun graphFriends(friends: MutableMap<String, Pair<MutableSet<String>, Int>>, obj: String, last: Int):
        Pair<MutableSet<String>, Int> {
    if (friends.getValue(obj).second % 2 == 1) return (friends.getValue(obj).first to last)
    if (friends.getValue(obj).second in 6..last) return (friends.getValue(obj).first to last)
    friends.replace(obj, friends.getValue(obj).first to last)
    var buffer = mutableSetOf<String>()
    var max = 0
    friends.getValue(obj).first.forEach {
        it ->
        if ((it != obj) && (friends.containsKey(it))) {
            val rec = graphFriends(friends, it, last + 2)
            buffer.addAll(rec.first)
            if (max < rec.second) max = rec.second
        } else if (!friends.containsKey(it)) {
            friends[it] = mutableSetOf<String>() to last-1
        }
    }
    val secbuf = friends.getValue(obj).first
    secbuf.addAll(buffer.filter { it != obj })
    friends.replace(obj, (secbuf to 1))
    buffer = friends.getValue(obj).first
    return (buffer to max)
}

fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val buffer = mutableMapOf<String, Pair<MutableSet<String>, Int>>()
    friends.forEach {
        key, value ->
        value.forEach { if ((!friends.containsKey(it)) && (!buffer.containsKey(it)))
                            buffer[it] = mutableSetOf<String>() to -1}
        buffer[key] = value.toMutableSet() to 0
    }
    var max = 6
    buffer.forEach {
        key, _ ->
        val rec = graphFriends(buffer, key, max)
        max = rec.second + 2
    }
    val ans = friends.toMutableMap()
    buffer.forEach{
        key, value ->
        ans[key] = value.first
    }
    return ans
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit = TODO()

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = TODO()

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean = TODO()

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> = TODO()

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean = TODO()

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> = TODO()

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> = TODO()
