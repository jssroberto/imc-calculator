package garcia.roberto.imccalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import java.util.Locale

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val weight: EditText = findViewById(R.id.weight)
        val height: EditText = findViewById(R.id.height)
        val calculate: Button = findViewById(R.id.calculate)
        val imc: TextView = findViewById(R.id.imc)
        val range: TextView = findViewById(R.id.range)

        calculate.setOnClickListener {
            if (weight.text.isEmpty() || height.text.isEmpty()) {
                return@setOnClickListener
            }
            val weightValue = weight.text.toString().toDouble()
            val heightValue = height.text.toString().toDouble()
            val imcResult = calculateIMC(weightValue, heightValue)
            imc.text = String.format(Locale.getDefault(), "%.2f", imcResult)
            setRange(imcResult, range)
        }

    }

    private fun calculateIMC(weight: Double, height: Double): Double {
        val heightInMeters = height / 100
        return weight / (heightInMeters * heightInMeters)
    }

    private fun setRange(imc: Double, range: TextView) {
        when {
            imc < 18.5 -> {
                range.text = getString(R.string.underweight)
                range.setBackgroundResource(R.color.lightblue)
            }
            imc < 24.9 -> {
                range.text = getString(R.string.normal_weight)
                range.setBackgroundResource(R.color.green)
            }
            imc < 29.9 -> {
                range.text = getString(R.string.overweight)
                range.setBackgroundResource(R.color.yellow)
            }
            imc < 34.9 -> {
                range.text = getString(R.string.obesity_class_1)
                range.setBackgroundResource(R.color.orange)
            }
            imc < 39.9 -> {
                range.text = getString(R.string.obesity_class_2)
                range.setBackgroundResource(R.color.darkorange)
            }
            else -> {
                range.text = getString(R.string.obesity_class_3)
                range.setBackgroundResource(R.color.red)
            }
        }
    }
}