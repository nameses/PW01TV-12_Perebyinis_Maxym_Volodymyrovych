import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lab1.R

class Task2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.task2, container, false)
    }

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editCarbon = view.findViewById<EditText>(R.id.edit_carbon)
        val editHydrogen = view.findViewById<EditText>(R.id.edit_hydrogen)
        val editOxygen = view.findViewById<EditText>(R.id.edit_oxygen)
        val editSulfur = view.findViewById<EditText>(R.id.edit_sulfur)
        val editLowerHeatingValue = view.findViewById<EditText>(R.id.edit_calorific_value)
        val editMoisture = view.findViewById<EditText>(R.id.edit_moisture)
        val editAsh = view.findViewById<EditText>(R.id.edit_ash_content)
        val editVanadium = view.findViewById<EditText>(R.id.edit_vanadium_content)
        val resultText = view.findViewById<TextView>(R.id.result_text_task2)
        val calculateButton = view.findViewById<Button>(R.id.calculate_button_task2)

        calculateButton.setOnClickListener {
            val carbon = editCarbon.text.toString().toDoubleOrNull() ?: 0.0
            val hydrogen = editHydrogen.text.toString().toDoubleOrNull() ?: 0.0
            val oxygen = editOxygen.text.toString().toDoubleOrNull() ?: 0.0
            val sulfur = editSulfur.text.toString().toDoubleOrNull() ?: 0.0
            val lowerHeatingValue = editLowerHeatingValue.text.toString().toDoubleOrNull() ?: 0.0
            val moisture = editMoisture.text.toString().toDoubleOrNull() ?: 0.0
            val ash = editAsh.text.toString().toDoubleOrNull() ?: 0.0
            val vanadium = editVanadium.text.toString().toDoubleOrNull() ?: 0.0

            // Perform calculations
            val carbonPR = carbon * (100 - moisture - ash) / 100
            val hydrogenPR = hydrogen * (100 - moisture - ash) / 100
            val oxygenPR = oxygen * (100 - moisture - ash) / 100
            val sulfurPR = sulfur * (100 - moisture - ash) / 100
            val ashPR = ash * (100 - moisture) / 100
            val vanadiumPR = vanadium * (100 - moisture) / 100

            val lowerHeatingValuePR = lowerHeatingValue * (100 - moisture - ash) / 100 - 0.025 * moisture

            resultText.text = String.format(
                "Вуглець: %.2f%%\nВодень: %.2f%%\nКисень: %.2f%%\nСірка: %.2f%%\n" +
                        "Зола: %.2f%%\nВанадій: %.2f (мг/кг)\nНижча теплота згоряння мазуту: %.2f МДж/кг",
                carbonPR, hydrogenPR, oxygenPR, sulfurPR, ashPR, vanadiumPR, lowerHeatingValuePR
            )
        }
    }
}
