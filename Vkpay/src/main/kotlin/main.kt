const val VK_PAY = "VK pay"
const val MASTERCARD = "Mastercard"
const val MAESTRO = "Maestro"
const val VISA = "Visa"
const val MIR = "Mir"
var cardType : String = ""
var userChoise: Int = 0
var comissionPercentage = 0.0
var transferAmmount:Int = 0
var previoustranslations = 0
var sumOfComission = 0


fun choosingCard(input:Int): String {
    when(input){
        1 -> cardType = MASTERCARD
        2 -> cardType = MAESTRO
        3 -> cardType = VISA
        4 -> cardType = MIR
    }
    return cardType
}

fun comissionPercent(
    cardType: String,
    transferAmount:Int,
    previoustranslations:Int = 0
): Double {
    when(cardType){
        VK_PAY -> comissionPercentage = 0.0
        VISA,MIR -> comissionPercentage = 0.75
        MAESTRO,MASTERCARD -> when(transferAmount+previoustranslations){
            in 30000..7500000 -> comissionPercentage = 0.0
            else -> comissionPercentage = 0.6
        }
    }
    return comissionPercentage
}

fun comissionAmmount(transferAmmount: Int, cardType: String, comissionPercentage:Double) : Int {
    sumOfComission = (transferAmmount/100 * comissionPercentage).toInt()
    when (cardType){
        MASTERCARD,MAESTRO -> {
            if (comissionPercentage == 0.6) sumOfComission += 20
        }
        VISA,MIR -> {
            if(sumOfComission < 3500) sumOfComission = 3500
        }
    }
    return sumOfComission
}


fun main() {
    println("Если желаете выполнить перевод через VK Pay нажмите Enter(для смены способа перевода нажмите пробел, далее Enter)")
    val input = readLine()
    if (input == " ") {
        println(
            """Выберите тип карты :
            |1.$MASTERCARD
            |2.$MAESTRO
            |3.$VISA
            |4.$MIR
        """.trimMargin()
        )
        userChoise = readLine()!!.toInt()
        println("Введите сумму перевода")
        transferAmmount = readLine()!!.toInt()
        comissionAmmount(transferAmmount,cardType = choosingCard(userChoise),comissionPercentage = comissionPercent(choosingCard(userChoise), transferAmmount, previoustranslations))
    } else {
        cardType = VK_PAY
        println("Введите сумму перевода")
        transferAmmount = readLine()!!.toInt()
        comissionAmmount(transferAmmount,cardType, comissionPercentage =  comissionPercent(cardType,transferAmmount,previoustranslations))
    }

    println("""Способ перевода: $cardType
        |Сумма перевода: $transferAmmount
        |Процент комиссии: $comissionPercentage
        |Cумма комиссии составит: $sumOfComission
    """.trimMargin())
}