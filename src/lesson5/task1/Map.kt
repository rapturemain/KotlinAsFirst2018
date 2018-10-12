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
fun swap(list: MutableList<String>, lo: Int, hi: Int): MutableList<String> {
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
        if (i > right.length - 1) return false
        if (i > left.length - 1) return true
    }
    return (left[i] < right[i])
}
fun sortListOfString(list: MutableList<String>) = sortListOfString(list, 0, list.size - 1).reversed().toMutableList()

fun sortListOfString(list: MutableList<String>, l: Int, h: Int):MutableList<String> {
    if (l == h) return list
    if (h - l == 1) return if (!isAlphabit(list[l], list[h])) swap(list, l, h) else list
    val base = list[l]
    var lo = l + 1
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
    swap(buffer, l, lo-1)
    if (l < hi) buffer = sortListOfString(buffer, l, hi)
    if (h > lo) buffer = sortListOfString(buffer, lo, h)
    return buffer
}

fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val map = mutableMapOf<Int, MutableList<String>>()
    grades.forEach {
        name, grade ->
        map.getOrPut(grade) { mutableListOf() }
        map.getValue(grade).add(name)
    }
    map.forEach {
        grade, names ->
        map[grade] = sortListOfString(names).toMutableList()
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
        if ((type == kind) && (max > cost)) {
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

fun graphFriends (friends: MutableMap<String, MutableSet<String>>, obj: String): MutableSet<String> {
    val buffer = mutableSetOf<String>()
    while (!friends.getValue(obj).isEmpty()) {
        val stringBuffer = friends.getValue(obj).first().toString()
        friends.getValue(obj).remove(stringBuffer)
        buffer.add(stringBuffer)
        buffer.addAll(graphFriends(friends, stringBuffer))
    }
    return buffer.toSortedSet().filter { it != obj }.toMutableSet()
}


fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val friendsBase = mutableMapOf<String, MutableSet<String>>()
    friends.forEach {
        key, value ->
        friendsBase[key] = value.toMutableSet()
        value.forEach {
            it ->
            if (!friendsBase.containsKey(it)) friendsBase[it] = mutableSetOf()
        }
    }
    val friendsBase2 = friendsBase.toMap()
    friendsBase.keys.forEach {
        friendsBase[it] =
                graphFriends(friendsBase2.map { it.key to it.value.toMutableSet() }.toMap().toMutableMap(), it)
    }
    return friendsBase.toMap()
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
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit = b.forEach {
    key, value ->
    if (a[key] == value) a.remove(key)
}
/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = a.intersect(b).toList()

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean =
        chars.joinToString().toLowerCase().toList().containsAll(word.toLowerCase().toList())
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
fun extractRepeats(list: List<String>): Map<String, Int> {
    val buffer = mutableMapOf<String, Int>()
    list.forEach {
        buffer[it] = buffer.getOrDefault(it, 0) + 1
    }
    return buffer.filterValues { it > 1 }
}
/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    val bufferList = words.toMutableList()
    var buffer = false
    words.forEach {
        it ->
        bufferList.remove(it)
        if ((bufferList.contains(it)) || (buffer)) { // проверка на два одинаковых слова
            buffer = true
        } else {
            bufferList.forEach { that ->
                if ((that.toList().containsAll(it.toList())) && (it.length == that.length)) buffer = true
            }
            bufferList.add(it)
        }
    }
    return buffer
}

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
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    var buffer = -1 to -1
    val bufferList = list.toMutableList()
    var count = 0
    list.forEach {
        it ->
        count++
        bufferList.remove(it)
        if (bufferList.contains(number - it)) buffer = list.indexOf(it) to bufferList.indexOf(number - it) + count
    }
    return if (buffer.first > buffer.second) buffer.second to buffer.first
           else buffer
}

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

fun findBestToReplace(map: Map<String, Pair<Int, Int>>, setToReplace: Set<String>): String {
    var max = 0
    var buffer = ""
    setToReplace.forEach {
        val (weight, cost) = map.getValue(it)
        if (cost - weight >= max) {
            max = cost - weight
            buffer = it
        }
    }
    return buffer
}

fun removeL (map: Map<String, Pair<Int, Int>>, bufferSetToReplace: MutableSet<String>, leftWeight: Int): Int{
    while (true) {
        var min = Int.MAX_VALUE
        var buffer = ""
        bufferSetToReplace.forEach {
            val weight = map.getValue(it).first
            if (min >= weight) min = weight
            buffer = it
        }
        if ((buffer != "") && (leftWeight - map.getValue(buffer).first >= 0)) bufferSetToReplace.remove(buffer)
        else return 0
    }
}

fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val map = treasures.toMutableMap()
    treasures.keys.forEach {
        if (map.getValue(it).first > capacity) map.remove(it)
    }
    var leftWeight = capacity
    val items = mutableSetOf<String>()
    map.forEach {
        key, (weight, cost) ->
        if (weight <= leftWeight) {
            items.add(key)
            leftWeight -= weight
        }
        else {
            var bestToReplace = ""
            items.forEach {
                val (weightI, costI) = map.getValue(it)
                if ((weightI >= weight) && (costI < cost) ||
                        (weightI > weight) && (costI <= cost)) {
                    if (bestToReplace != "") {
                        val (weightB, costB) = map.getValue(bestToReplace)
                        if ((weightB > weight) && (costB < cost)) bestToReplace = it
                    } else bestToReplace = it
                }
            }
            if (bestToReplace != "") {
                items.remove(bestToReplace)
                items.add(key)
                leftWeight += map.getValue(bestToReplace).first - weight
            } else {
                val setToReplace = mutableSetOf<String>()
                items.forEach {
                    val (weightI, costI) = map.getValue(it)
                    if (weightI - weight > costI - cost) setToReplace.add(it)
                }
                if (setToReplace.isNotEmpty()) {
                    val bufferSetToReplace = mutableSetOf<String>()
                    var totalWeightToReplace = 0
                    while ((leftWeight + totalWeightToReplace < weight) && (setToReplace.isNotEmpty())) {
                        val buffer = findBestToReplace(map, setToReplace)
                        totalWeightToReplace += map.getValue(buffer).first
                        setToReplace.remove(buffer)
                        bufferSetToReplace.add(buffer)
                    }
                    removeL(map, bufferSetToReplace, leftWeight + totalWeightToReplace - weight)
                    if (leftWeight + totalWeightToReplace >= weight) {
                        items.add(key)
                        items.removeAll(bufferSetToReplace)
                        leftWeight += totalWeightToReplace - weight
                    }
                }
            }
        }
    }
    return items.toSet().reversed().toSet()
}
