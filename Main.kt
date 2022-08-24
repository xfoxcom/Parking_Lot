
import java.util.*
import kotlin.random.Random

fun main() {
    print("How many mines do you want on the field? ")
    val scanner = Scanner(System.`in`)
    val mines = scanner.nextInt()
    val list = mutableListOf(
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", ".")
    )
    val hided = mutableListOf(
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", ".")
    )

    val newList = mutableListOf<Int>()
    while (newList.distinct().size != mines) {
        newList.add(Random.nextInt(0, 89))
        newList.removeAll(mutableListOf(9, 19, 29, 39, 49, 59, 69, 79))
    }

    for (i in newList) {
        if (i in 0..8) list[0][i] = "X" else list[i/10][i%10] = "X"
    }

    var value : Any
    value = 5
    value = 9
    value = " "

    scan(list)
    showField(hided)

    while (!winCondition(list, hided)) {
        print("Set/unset mine marks or claim a cell as free: ")
        val (a, b, c) = readln().split("\\s+".toRegex())
        when(c) {
            "free" -> if (list[b.toInt() - 1][a.toInt() - 1].matches("[0-9]+".toRegex())) {
                hided[b.toInt() - 1][a.toInt() - 1] = list[b.toInt() - 1][a.toInt() - 1]
                showField(hided)
            } else if (list[b.toInt() - 1][a.toInt() - 1].matches("\\.".toRegex())) {
                reveal(list, hided, b.toInt() - 1, a.toInt() - 1)
                reveal(list, hided, b.toInt() - 1, a.toInt() - 1)
                showField(hided)
            } else if (list[b.toInt() - 1][a.toInt() - 1].matches("X".toRegex())) {
                for (i in 0..8) {
                    for (j in 0..8) {
                        if (list[i][j].matches("X".toRegex())) hided[i][j] = list[i][j]
                    }
                }
                showField(hided)
                print("You stepped on a mine and failed!")
                return
            }
            "mine" ->  if (hided[b.toInt() - 1][a.toInt() - 1].matches("\\*".toRegex())) {
                hided[b.toInt() - 1][a.toInt() - 1] = "."
                showField(hided)
                if (winCondition(list, hided)) {
                    println("Congratulations! You found all the mines!")
                }
            }
            else if
                         (hided[b.toInt() - 1][a.toInt() - 1].matches("\\.".toRegex())) {
                hided[b.toInt() - 1][a.toInt() - 1] = "*"
                showField(hided)
                if (winCondition(list, hided)) {
                    println("Congratulations! You found all the mines!")
                }
            }
        }
    }
}
fun reveal (openField: MutableList<MutableList<String>>, hiddenField: MutableList<MutableList<String>>, b:Int, a:Int) {

    if (b < 0 || b > 8 || a < 0 || a > 8 ) return

    if (openField[b][a].matches("\\d".toRegex())) {
        hiddenField[b][a] = openField[b][a]
        return
    }
    if (hiddenField[b][a] != "." || openField[b][a] == "X") return

    hiddenField[b][a] = "/"
    replaceStar(hiddenField, openField)

    reveal(openField, hiddenField, b - 1, a)
    reveal(openField, hiddenField, b, a + 1)
    reveal(openField, hiddenField, b, a - 1)
    reveal(openField, hiddenField, b + 1, a)

    reveal(openField, hiddenField, b - 1, a - 1)
    reveal(openField, hiddenField, b + 1, a + 1)
    reveal(openField, hiddenField, b - 1, a + 1)
    reveal(openField, hiddenField, b + 1, a - 1)
}
fun scan (line: MutableList<MutableList<String>>) {
    for (i in 0..8) {
        for (j in 0..8) {
            var count = 0
            if (line[i][j] != "X") {
                try { if (line[i-1][j-1] == "X") count++  } catch (e: IndexOutOfBoundsException) { e.message }
                try { if (line[i-1][j] == "X") count++ } catch (e: IndexOutOfBoundsException) { e.message }
                try { if (line[i-1][j+1] == "X") count++ } catch (e: IndexOutOfBoundsException) { e.message }
                try { if (line[i][j+1] == "X") count++ } catch (e: IndexOutOfBoundsException) { e.message }
                try { if (line[i][j-1] == "X") count++ } catch (e: IndexOutOfBoundsException) { e.message }
                try { if (line[i+1][j-1] == "X") count++ } catch (e: IndexOutOfBoundsException) { e.message }
                try { if (line[i+1][j] == "X") count++ } catch (e: IndexOutOfBoundsException) { e.message }
                try { if (line[i+1][j+1] == "X") count++ } catch (e: IndexOutOfBoundsException) { e.message }
                if (count > 0) line[i][j] = "$count"
            }
        }
    }
}
fun showField (list: MutableList<MutableList<String>>) {
    var i = 1
    println(" │123456789│")
    println("—|—————————|")
    for (strings in list) {
        print("${i++}|")
        for (string in strings) {
            print(string)
        }
        print("|")
        println()
    }
    println("—|—————————|")
}
fun winCondition (openField: MutableList<MutableList<String>>, hiddenField: MutableList<MutableList<String>>): Boolean {
    val list = mutableListOf(
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", ".")
    )
    val listH = mutableListOf(
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", "."),
        mutableListOf(".", ".", ".", ".", ".", ".", ".", ".", ".")
    )

    for (i in 0..8) {
        for (j in 0..8) {
            if (openField[i][j] == "X") list[i][j] = "*"
        }
    }
    for (i in 0..8) {
        for (j in 0..8) {
            if (hiddenField[i][j] == "*") listH[i][j] = "*"
        }
    }
    return list == listH
}
fun replaceStar (hiddenField: MutableList<MutableList<String>>, openField: MutableList<MutableList<String>>) {
    for (i in 0..8) {
        for (j in 0..8) {
            try {
                if (hiddenField[i][j] == "*" && checkIf(hiddenField, i, j) && !openField[i][j].matches("\\d".toRegex())
                )
                    hiddenField[i][j] = "/"
            } catch (e: IndexOutOfBoundsException) {
                e.message
            }
        }
    }
}
fun checkIf (hiddenField: MutableList<MutableList<String>>, i:Int, j:Int): Boolean {
    try { if (hiddenField[i-1][j-1] == "/") return true } catch (e: IndexOutOfBoundsException) { e.message }
    try { if (hiddenField[i-1][j] == "/") return true } catch (e: IndexOutOfBoundsException) { e.message }
    try { if (hiddenField[i-1][j+1] == "/") return true } catch (e: IndexOutOfBoundsException) { e.message }
    try { if (hiddenField[i][j+1] == "/") return true } catch (e: IndexOutOfBoundsException) { e.message }
    try { if (hiddenField[i][j-1] == "/") return true } catch (e: IndexOutOfBoundsException) { e.message }
    try { if (hiddenField[i+1][j-1] == "/") return true } catch (e: IndexOutOfBoundsException) { e.message }
    try { if (hiddenField[i+1][j] == "/") return true } catch (e: IndexOutOfBoundsException) { e.message }
    try { if (hiddenField[i+1][j+1] == "/") return true } catch (e: IndexOutOfBoundsException) { e.message }
    return false
}
