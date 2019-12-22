package ru.skillbranch.devintensive.extensions


fun pluriaze(value: Int, form1: String, form2: String, form5: String): String {
    if (0 == value) return form5
    val valueCent = Math.abs(value) % 100
    val valueDec = valueCent % 10

    if (valueCent > 10 && valueCent < 20) return form5
    if (valueDec > 1 && valueDec < 5) return form2
    if (valueDec == 1) return form1
    return form5
}

fun String.truncate(numberOfSymbols: Int = 16): String {
    var result: String

    val length = this.length
    when(length){
        in 0..numberOfSymbols -> result = this.trim()
        else -> result = this.substring(0..(numberOfSymbols-1)).trim() + "..."
    }

    val resultLeng = result.length

    for (i in 0 until resultLeng){
        if (result[resultLeng - i].equals(' ')){
            result = result.substring(0..(resultLeng-i))
        }
        else break
    }

    return result
}

