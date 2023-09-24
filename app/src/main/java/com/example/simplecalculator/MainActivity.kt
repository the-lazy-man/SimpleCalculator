package com.example.simplecalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

class MainActivity : ComponentActivity() {
    private var txtInput : TextView? = null
    private var lastNumeric : Boolean  = false
    private var lastDot : Boolean  = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        txtInput = findViewById(R.id.txtInput)
    }
    fun OnDigit(view : View) {
//        Toast.makeText(this,"button clicked",Toast.LENGTH_SHORT).show()
        txtInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }
    fun onEqual(view : View){
        if(lastNumeric){
            var txtValue = txtInput?.text.toString()
            var prefix = ""
            try {
                if(txtValue.startsWith("-")){
                    prefix = "-"
                    txtValue = txtValue.substring(1)
                }
                if(txtValue.contains("-")){
                    val splitValue = txtValue.split("-")    // a delimiter is a character or sequence of characters used to separate and distinguish different parts within a larger text or data structure.
                    var valOne = splitValue[0]
                    var valTwo = splitValue[1]
                    if(prefix.isNotEmpty()){
                        valOne = prefix + valOne
                    }
                    val result = valOne.toDouble() - valTwo.toDouble()
                    println("valOne = $valOne , valTwo = $valTwo , result = $result")
                    txtInput?.text = removeZeroAfterDot(result.toString())
                }
                else if(txtValue.contains("+")) {
                    val splitValue =
                        txtValue.split("+")    // a delimiter is a character or sequence of characters used to separate and distinguish different parts within a larger text or data structure.
                    var valOne = splitValue[0]
                    var valTwo = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        valOne = prefix + valOne
                    }
                    txtInput?.text = removeZeroAfterDot((valOne.toDouble() + valTwo.toDouble()).toString())
                }
                else if(txtValue.contains("*")) {
                    val splitValue =
                        txtValue.split("*")    // a delimiter is a character or sequence of characters used to separate and distinguish different parts within a larger text or data structure.
                    var valOne = splitValue[0]
                    var valTwo = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        valOne = prefix + valOne
                    }
                    txtInput?.text = removeZeroAfterDot((valOne.toDouble() * valTwo.toDouble()).toString())
                }
                else{
                    if(txtValue.contains("/")) {
                        val splitValue = txtValue.split("/")    // a delimiter is a character or sequence of characters used to separate and distinguish different parts within a larger text or data structure.
                        var valOne = splitValue[0]
                        var valTwo = splitValue[1]
                        if (prefix.isNotEmpty()) {
                            valOne = prefix + valOne
                        }
                        txtInput?.text = removeZeroAfterDot((valOne.toDouble() / valTwo.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeZeroAfterDot(answer : String) : String {
        var value = answer
        if(answer.contains(".0"))  value = answer.substring(0,answer.length - 2)
        return value
    }

    fun onOperator(view: View){
        if(txtInput?.text.toString() == "")
            println("before outside txtIndex = ${txtInput?.text.toString()}")
        println("this is " +(view as Button).text.toString())
        if(txtInput?.text.toString() == "" && (view as Button).text.toString() == "-") {
            txtInput?.append((view as Button).text)
            println("this works")
            println("txtIndex = ${txtInput?.text.toString()}")

        }
        if(txtInput?.text.toString() == "")
            println("outside txtIndex = ${txtInput?.text.toString()}")
        txtInput?.text?.let{
            println("lastNumeric = $lastNumeric")
            if(lastNumeric && !isOperatorAdded(it.toString())){
                println("txtIndex = ${it.toString()}")
                lastNumeric = false
                lastDot = false
                txtInput?.append((view as Button).text)
            }
        }

    }
    // this if is being used to ignore the count of '-' sign when at the front of the expression like -89, -7 etc.
    // That means, the minus sign will not be counted and this function here will return false. Basicaly this if function alows
    // operation on negative values for eg-> -99 (*,/, +,-) 8 ,this multiplication will be possible in the app
    fun isOperatorAdded(value : String) : Boolean{
        var mutableValue = value
        if(value.startsWith("-")){
            mutableValue = mutableValue.substring(1)
            return (
                    mutableValue.contains("+") ||
                    mutableValue.contains("/") ||
                    mutableValue.contains("*") ||
                    mutableValue.contains("-")
                    )
        } else {
            return (
                    value.contains("+") ||
                    value.contains("/") ||
                    value.contains("*") ||
                    value.contains("-")
                    )
        }
    }

    fun OnClear(view: View){
        txtInput?.text = ""
        println("textIndex = ${txtInput?.text.toString()} at clear function")
        lastNumeric = false
        lastDot = false
    }
    fun OnCross(view: View){
        var a = txtInput?.text.toString()
        println("before cross a = $a")
        if(!a.length.equals(0)) {
            println("after cross a = $a")
            if (a[a.length - 1].equals("."))
                lastDot = false
            else {
                if(a.length == 1)
                    lastNumeric = false
                else lastNumeric = true
            }
            a = a.substring(0, a.length - 1)
            txtInput?.text = a
        }
    }
    fun OnDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            txtInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
}
