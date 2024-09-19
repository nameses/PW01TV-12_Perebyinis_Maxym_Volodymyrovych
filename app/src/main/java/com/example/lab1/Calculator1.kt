package com.example.lab1

// Data class to hold the result of ExecCalculation
class CalculationResult(var success: Boolean = true, var errorMsg:String = "",
    var hc: Double = 0.0, var cc: Double = 0.0, var sc: Double = 0.0, var nc: Double = 0.0, var oc: Double = 0.0, var ac: Double = 0.0,
    var hg: Double = 0.0, var cg: Double = 0.0, var sg: Double = 0.0, var ng: Double = 0.0, var og: Double = 0.0,
    var qr: Double = 0.0, var qc: Double = 0.0, var qg: Double = 0.0
)

class Calculator1(
    val hp: Double,
    val cp: Double,
    val sp: Double,
    val np: Double,
    val op: Double,
    val wp: Double,
    val ap: Double
) {


    fun ExecCalculation(): CalculationResult {
        val calcResult = CalculationResult()

        val coefRC = 100.0 / (100.0 - wp)
        val coefRG = 100.0 / (100.0 - wp - ap)

        calcResult.hc = hp * coefRC
        calcResult.cc = cp * coefRC
        calcResult.sc = sp * coefRC
        calcResult.nc = np * coefRC
        calcResult.oc = op * coefRC
        calcResult.ac = ap * coefRC

        val totalDry = calcResult.hc + calcResult.cc + calcResult.sc + calcResult.nc + calcResult.oc + calcResult.ac
        if(totalDry < 99.0 || totalDry > 101.0){
            calcResult.success = false
            calcResult.errorMsg = "Помилка під час розрахунку складу сухої маси"
        }

        calcResult.hg = hp * coefRG
        calcResult.cg = cp * coefRG
        calcResult.sg = sp * coefRG
        calcResult.ng = np * coefRG
        calcResult.og = op * coefRG

        val totalG = calcResult.hg + calcResult.cg + calcResult.sg + calcResult.ng + calcResult.og
        if(totalG < 99.0 || totalG > 101.0){
            calcResult.success = false
            calcResult.errorMsg = "Помилка під час розрахунку складу горючої маси"
        }

        calcResult.qr = 339.0 * cp + 1030.0 * hp - 108.8 * (op - sp) - 25.0 * wp
        //з кДж/кг в МДж/кг
        calcResult.qr /= 1000.0

        calcResult.qc = (calcResult.qr + 0.025 * wp) * (100.0 / (100.0 - wp))
        calcResult.qg = (calcResult.qr + 0.025 * wp) * (100.0 / (100.0 - wp - ap))

        return calcResult
    }
}