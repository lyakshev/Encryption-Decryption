package encryptdecrypt

import java.io.File
import kotlin.math.abs

val alphabet = "abcdefghijklmnopqrstuvwxyz"
val alphabetBig = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

fun main(args: Array<String>) {

    var mode = "enc"
    var key = 0
    var data = ""
    var inPath = ""
    var outPath = ""
    var dataToChange = ""
    var algoritm = "shift"


    for (i in args.indices step 2) {
        when (args[i]) {
            "-mode" -> mode = args[i+1]
            "-key" -> key = args[i+1].toInt()
            "-data" -> data = args[i+1]
            "-in" -> inPath = args[i+1]
            "-out" -> outPath = args[i+1]
            "-alg" -> algoritm = args[i+1]
        }
    }

    if (data != "" && inPath != "") dataToChange = data
    if (data == "" && inPath != "") {
        dataToChange = File(inPath).readText()
    }

    when {
        mode == "enc" -> encryption(dataToChange, key, outPath, algoritm)
        mode == "dec" -> decryption(dataToChange, key, outPath, algoritm)
        else -> println("Error")
    }
}

fun encryption(text : String, number : Int, out : String, alg: String)  {
    var encryptedText = ""

    when (alg) {
        "shift" -> {
            for (bukva in text) {
                when {
                    bukva in alphabet -> encryptedText += alphabet[(alphabet.indexOf(bukva) + number) % 26]
                    bukva in alphabetBig -> encryptedText += alphabetBig[(alphabetBig.indexOf(bukva) + number) % 26]
                    else -> encryptedText += bukva
                }
            }

        }
        "unicode" -> {
            for (bukva in text) {
            encryptedText += (bukva.code + number).toChar()
            }
        }
    }


    if (out == "") print(encryptedText) else File(out).writeText(encryptedText)


}

fun decryption(text : String, number : Int, out : String, alg : String)  {
    var decryptedText = ""

    when (alg) {
        "shift" -> {
            for (bukva in text) {
                when {
                    bukva in alphabet -> decryptedText += alphabet[(26 + (alphabet.indexOf(bukva) - number)) % 26]
                    bukva in alphabetBig -> decryptedText += alphabetBig[(26 + (alphabetBig.indexOf(bukva) - number)) % 26]
                    else -> decryptedText += bukva
                }
            }
        }
        "unicode" -> {
            for (bukva in text) {
                decryptedText += (bukva.code - number).toChar()
            }
        }
    }

    if (out == "") print(decryptedText) else File(out).writeText(decryptedText)
}