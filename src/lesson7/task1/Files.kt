@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import java.io.File

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                }
                else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val inFile = File(inputName)
    val map = mutableMapOf<String, Int>()
    val text = inFile.readText().toLowerCase()
    for (key in substrings) {
        val it = key.toLowerCase()
        val count = Regex(it).findAll(text).toList().size
        map[key] = count
    }
    return map
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val inFile = File(inputName)
    val outFile = File(outputName).bufferedWriter()
    val firstChar = "жчшщ".toList()
    val replaceMap = mapOf('ы' to 'и', 'я' to 'а', 'ю' to 'у')
    var prevWrong = false
    for (char in inFile.readText()) {
        val it = char.toLowerCase()
        var upperCase = false
        if (char.toLowerCase() != char) upperCase = true
        if (firstChar.contains(it)) {
            prevWrong = true
            outFile.write(char.toString())
        } else {
            if (prevWrong) {
                if (replaceMap.containsKey(it)) when {
                    upperCase -> outFile.write(replaceMap[it].toString().toUpperCase())
                    else -> outFile.write(replaceMap[it].toString())
                } else {
                    outFile.write(char.toString())
                }
                prevWrong = false
            } else outFile.write(char.toString())
        }
    }
    outFile.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val inFile = File(inputName)
    val outFile = File(outputName).bufferedWriter()
    val map = inFile.readLines().map { it.trim { c -> c == ' ' } }
    if (map.isEmpty()) {
        outFile.write("")
        outFile.close()
    } else {
        val maxLength = map.maxBy { it.length }!!.length
        for (it in map) {
            val size = it.length
            outFile.write(it.padStart((maxLength - size) / 2 + size))
            outFile.newLine()
        }
        outFile.close()
    }
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val inFile = File(inputName)
    val outFile = File(outputName).bufferedWriter()
    val words = inFile.readLines().map { it.trim { c -> c == ' ' }.split(" ").filter { c -> c != "" } }
    val count = words.map { it.size }
    val lengths = words.map { it.joinToString(" ").length }
    if (lengths.isEmpty()) {
        outFile.write("")
        outFile.close()
        return
    }
    val maxLength = lengths.max()!!.toInt()
    for (i in 0 until words.size) {
        when {
            words[i].size == 1 -> outFile.write(words[i][0])
            words[i].isNotEmpty() -> {
                val spaces = maxLength - lengths[i] + count[i] - 1
                val gaps = count[i] - 1
                if (spaces == count[i] - 1) outFile.write(words[i].joinToString(" "))
                else {
                    val longWords = spaces % gaps
                    val spaceSize = spaces / gaps
                    for (j in 0 until longWords) outFile.write("${words[i][j]}${"".padStart(spaceSize + 1)}")
                    for (j in longWords until gaps) outFile.write("${words[i][j]}${"".padStart(spaceSize)}")
                    outFile.write(words[i].last())
                }
            }
        }
        outFile.newLine()
    }
    outFile.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */

fun top20Words(inputName: String): Map<String, Int> {
    val inFile = File(inputName)
    val map = mutableMapOf<String, Int>()
    for (line in inFile.readLines()) {
        val buffer = line.toLowerCase().split(Regex("""[^a-zа-яё]+""")).filter { it != ""}
        for (it in buffer) {
            map[it] = map.getOrDefault(it, 0) + 1
        }
    }
    val buffer = map.map { it.key to it.value }.sortedBy { it.second }.reversed()
    return buffer.take(20).toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val inFile = File(inputName)
    val outFile = File(outputName).bufferedWriter()
    val mapUpdated = dictionary.map { it.key.toLowerCase() to it.value.toLowerCase() }.toMap()
    for (it in inFile.readText()) {
        if (it.toLowerCase() == it) outFile.write(mapUpdated.getOrDefault(it, it.toString()))
        else {
            val buffer = mapUpdated.getOrDefault(it.toLowerCase(), it.toString())
            if (buffer.isNotEmpty()) outFile.write(buffer.replaceFirst(buffer[0], buffer[0].toUpperCase()))
            else outFile.write(buffer)
        }
    }
    outFile.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun checkWord(word: String): Boolean {
    val listOfChar = word.toLowerCase().map { it to 0 }.toMap()
    return word.length == listOfChar.size
}

fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val inFile = File(inputName)
    val outFile = File(outputName).bufferedWriter()
    val listOfMax = mutableListOf<String>()
    var max = 0
    for (it in inFile.readLines()) {
        if (checkWord(it)) {
            when {
                it.length > max -> {
                    max = it.length
                    listOfMax.clear()
                    listOfMax.add(it)
                }
                it.length == max -> listOfMax.add(it)
            }
        }
    }
    outFile.write(listOfMax.joinToString(", "))
    outFile.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
    <body>
        <p>
            Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
            Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
        </p>
        <p>
            Suspendisse <s>et elit in enim tempus iaculis</s>.
        </p>
    </body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun htmlWorker(text: String): String {
    var buffer = "<html><body>"
    var boldStatus = false
    var italicStatus = false
    var crossedStatus = false
    var paraStatus = true
    var i = 0
    while (i < text.length - 2) {
        val first = text[i]
        val second = text[i + 1]
        var toAdd = ""
        if ((first == '\n') && (second == '\n')) {
            // менять второе \n на \r в Windows
            if (!paraStatus) toAdd = "</p>"
            paraStatus = true
            i += 1
        } else when {
            (first == '*') && (second == '*') -> {
                if (boldStatus) {
                    toAdd = "</b>"
                    boldStatus = false
                } else {
                    toAdd = "<b>"
                    boldStatus = true
                }
                i += 2
            }
            (first == '*') -> {
                if (italicStatus) {
                    toAdd = "</i>"
                    italicStatus = false
                } else {
                    toAdd = "<i>"
                    italicStatus = true
                }
                i += 1
            }
            (first == '~') && (second == '~') -> {
                if (crossedStatus) {
                    toAdd = "</s>"
                    crossedStatus = false
                } else {
                    toAdd = "<s>"
                    crossedStatus = true
                }
                i += 2
            }
            else -> {
                toAdd = first.toString()
                i += 1
            }
        }
        if ((first != '\n') && (paraStatus)) {
            toAdd = "<p>$toAdd"
            paraStatus = false
        }
        buffer = "$buffer$toAdd"
    }
    if (italicStatus) {
        val index = buffer.lastIndexOf("<i>")
        buffer = buffer.replaceRange(index, index + 3, "*")
    }
    if (boldStatus) {
        val index = buffer.lastIndexOf("<b>")
        buffer = buffer.replaceRange(index, index + 3, "**")
    }
    if (crossedStatus) {
        val index = buffer.lastIndexOf("<s>")
        buffer = buffer.replaceRange(index, index + 3, "~~")
    }
    if (!paraStatus) buffer = "$buffer</p>"
    return buffer
}

fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val inFile = File(inputName)
    val outFile = File(outputName).bufferedWriter()
    val text = "${inFile.readText()}  "
    var buffer = htmlWorker(text)
    buffer = "$buffer</body></html>"
    outFile.write(buffer)
    outFile.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
* Утка по-пекински
    * Утка
    * Соус
* Салат Оливье
    1. Мясо
        * Или колбаса
    2. Майонез
    3. Картофель
    4. Что-то там ещё
* Помидоры
* Фрукты
    1. Бананы
    23. Яблоки
        1. Красные
        2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
  <body>
    <ul>
      <li>
        Утка по-пекински
        <ul>
          <li>Утка</li>
          <li>Соус</li>
        </ul>
      </li>
      <li>
        Салат Оливье
        <ol>
          <li>Мясо
            <ul>
              <li>
                  Или колбаса
              </li>
            </ul>
          </li>
          <li>Майонез</li>
          <li>Картофель</li>
          <li>Что-то там ещё</li>
        </ol>
      </li>
      <li>Помидоры</li>
      <li>
        Яблоки
        <ol>
          <li>Красные</li>
          <li>Зелёные</li>
        </ol>
      </li>
    </ul>
  </body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun makeListFromString (text: String): List<List<String>> {
    var i = 0
    var index1 = 0
    var index2 = 0
    val lines = mutableListOf(mutableListOf(""))
    while (i < text.length) {
        if (text[i] == '\n') {
            if ((i < text.length - 1) && (text[i + 1] == '\n')) {
                lines.add(mutableListOf(""))
                index1 = 0
                index2++
            }
            if ((Regex("""[ ]*\*""").find(text.substring(i + 1)) != null) ||
                    (Regex("""[ ]*\d+\.""").find(text.substring(i + 1)) != null)) {
                lines[index2].add("")
                i++
                index1++
            }
        }
        lines[index2][index1] = "${lines[index2][index1]}${text[i]}"
        i++
    }
    return lines
}


fun countSpacesInStart(line: String): Int {
    var count = 0
    for (it in line) {
        if (it != ' ') return count
        count++
    }
    return -1
}

fun treeWorker(text: List<String>, string: String, spaces: Int, lastIndex: Int): Pair<String, Int> {
    if (text.isEmpty()) return "" to 0
    var buffer = string
    var i = lastIndex
    var statusOfNotNum = false
    var statusOfNum = false
    var liStatus = false
    while (i < text.size) {
        val spacesInStart = countSpacesInStart(text[i])
        val line = text[i].substring(spacesInStart)
        if ((line[0] != '*') && (!Regex("""\d+\.""").matches("${line.substringBefore('.')}."))) {
            buffer = "$buffer${text[i]}"
            i++
        } else {
            when (spacesInStart) {
                spaces + 4 -> {
                    val pair = treeWorker(text, buffer, spaces + 4, i)
                    buffer = pair.first
                    i = pair.second
                }
                spaces - 4 -> {
                    if (liStatus) buffer = "$buffer</li>"
                    if (statusOfNum) buffer = "$buffer</ol>"
                    if (statusOfNotNum) buffer = "$buffer</ul>"
                    return buffer to i
                }
                else -> when {
                    line[0] == '*' -> {
                        if (statusOfNum) {
                            buffer = "$buffer</ol>"
                            statusOfNum = false
                        }
                        if (!statusOfNotNum) {
                            buffer = "$buffer<ul>"
                            statusOfNotNum = true
                        }
                        if (liStatus) buffer = "$buffer</li>"
                        buffer = "$buffer<li>${line.substringAfter('*')}"
                        liStatus = true
                        i++
                    }
                    Regex("""\d+\.""").matches("${line.substringBefore('.')}.") -> {
                        if (statusOfNotNum) {
                            buffer = "$buffer</ul>"
                            statusOfNotNum = false
                        }
                        if (!statusOfNum) {
                            buffer = "$buffer<ol>"
                            statusOfNum = true
                        }
                        if (liStatus) buffer = "$buffer</li>"
                        buffer = "$buffer<li>${line.substringAfter('.')}"
                        liStatus = true
                        i++

                    }
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }
    if (liStatus) buffer = "$buffer</li>"
    if (statusOfNum) buffer = "$buffer</ol>"
    if (statusOfNotNum) buffer = "$buffer</ul>"
    return buffer to i
}

fun markdownToHtmlLists(inputName: String, outputName: String) {
    val inFile = File(inputName)
    val outFile = File(outputName).bufferedWriter()
    val text = inFile.readText()
    val lines = makeListFromString(text)
    var buffer = "<html><body>"
    buffer = treeWorker(lines[0], buffer, 0, 0).first
    buffer = "$buffer</body></html>"
    outFile.write(buffer)
    outFile.close()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */

fun markdownToHtml(inputName: String, outputName: String) {
    val inFile = File(inputName)
    val outFile = File(outputName).bufferedWriter()
    var buffer = ""
    val text = inFile.readText()
    val lines = makeListFromString(text)
    for (it in lines) {
        buffer = "${treeWorker(it, buffer, 0, 0).first}\n"
    }
    buffer.removeSuffix("\n")
    buffer = htmlWorker("$buffer  ")
    buffer = "$buffer</body></html>"
    outFile.write(buffer)
    outFile.close()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
   19935
*    111
--------
   19935
+ 19935
+19935
--------
 2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
  235
*  10
-----
    0
+235
-----
 2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val outFile =  File(outputName).bufferedWriter()
    val maxLength = digitNumber(lhv * rhv) + 1
    outFile.write("${lhv.toString().padStart(maxLength)}\n")
    outFile.write("*${rhv.toString().padStart(maxLength - 1)}\n")
    outFile.write("${"".padStart(maxLength, '-')}\n")
    outFile.write("${(lhv * rhv.toString().last().toString().toInt()).toString().padStart(maxLength)}\n")
    var k = 0
    for (i in digitNumber(rhv) - 2 downTo 0) {
        k++
        outFile.write("+${(lhv * rhv.toString()[i].toString().toInt()).toString().padStart(maxLength - 1 - k)}\n")
    }
    outFile.write("${"".padStart(maxLength, '-')}\n")
    outFile.write((lhv * rhv).toString().padStart(maxLength))
    outFile.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
  19935 | 22
 -198     906
 ----
    13
    -0
    --
    135
   -132
   ----
      3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

