package com.example.lab1

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class Task1Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.task1, container, false)
    }

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("MainActivity", "onCreate called")
        val editHP = view.findViewById<EditText>(R.id.edit_hp)
        val editCP = view.findViewById<EditText>(R.id.edit_cp)
        val editSP = view.findViewById<EditText>(R.id.edit_sp)
        val editNP = view.findViewById<EditText>(R.id.edit_np)
        val editOP = view.findViewById<EditText>(R.id.edit_op)
        val editWP = view.findViewById<EditText>(R.id.edit_wp)
        val editAP = view.findViewById<EditText>(R.id.edit_ap)
        val resultText = view.findViewById<TextView>(R.id.result_text)
        val calculateButton = view.findViewById<Button>(R.id.calculate_button)

        val textFields = listOf(editHP, editCP, editSP, editNP, editOP, editWP, editAP)

        addControlsEvents(textFields, resultText, calculateButton)

        calculateButton.setOnClickListener {
            val hp = editHP.text.toString().toDoubleOrNull() ?: 0.0
            val cp = editCP.text.toString().toDoubleOrNull() ?: 0.0
            val sp = editSP.text.toString().toDoubleOrNull() ?: 0.0
            val np = editNP.text.toString().toDoubleOrNull() ?: 0.0
            val op = editOP.text.toString().toDoubleOrNull() ?: 0.0
            val wp = editWP.text.toString().toDoubleOrNull() ?: 0.0
            val ap = editAP.text.toString().toDoubleOrNull() ?: 0.0

            val total = hp+cp+sp+np+op+wp+ap
            if(total != 100.0){
                Toast.makeText(context, "Сума компонентів повинна бути 100%. Зараз $total", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener;
            }

            val result = Calculator1(hp,cp,sp,np,op,wp,ap).ExecCalculation()

            if(!result.success){
                Toast.makeText(context, result.errorMsg, Toast.LENGTH_LONG)
                    .show()
                editHP.text.clear()
                editCP.text.clear()
                editSP.text.clear()
                editNP.text.clear()
                editOP.text.clear()
                editWP.text.clear()
                editAP.text.clear()
                return@setOnClickListener;
            }

            resultText.text = String.format("Суха маса:\nHс=%.3f, Cс=%.3f, Sм=%.3f, Nс=%.3f, Oс=%.3f, Aс=%.3f\n"
                    + "Горюча маса:\nHг=%.3f, Cг=%.3f, Sг=%.3f, Nг=%.3f, Oг=%.3f\n"
                    + "Нижча теплота згоряння для робочої маси:%.6f\n"
                    + "Нижча теплота згоряння для сухої маси:%.6f\n"
                    + "Нижча теплота згоряння для горючої маси:%.6f\n",
                result.hc, result.cc, result.sc, result.nc, result.oc, result.ac,
                result.hg, result.cg, result.sg, result.ng, result.og,
                result.qr, result.qc, result.qg)
        }
    }


    fun addControlsEvents(fields: List<EditText>, resultText: TextView, calculateButton: Button){
        for (editText in fields) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    validateInputs(fields, calculateButton)
                }
            })
        }
    }

    // Function to calculate the total of the inputs and validate
    fun validateInputs(fields: List<EditText>, calculateButton: Button) {
        // Calculate total
        val total = fields.sumOf { it.text.toString().toDoubleOrNull() ?: 0.0 }

        if (total > 100.0) {
            Toast.makeText(context, "Сума компонентів не може перевищувати 100%!", Toast.LENGTH_LONG).show()
            calculateButton.isEnabled = false
        } else {
            calculateButton.isEnabled = true
        }
    }
}
