package top.mk85.mike.guessthenumber

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvInfo: TextView
    private lateinit var etInput: EditText
    private lateinit var bControl: Button

    private var guess: Int = 0
    private var gameFinished: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvInfo = findViewById(R.id.textView)
        etInput = findViewById(R.id.editTextNumberDecimal)
        bControl = findViewById(R.id.button)

        guess = (Math.random() * 100).toInt()
    }

    fun onClick(v:View) {
        if (!gameFinished) {
            val inp: Int = etInput.text.toString().toInt()

            if (inp > guess)
                tvInfo.text = resources.getString(R.string.ahead)
            else if (inp < guess)
                tvInfo.text = resources.getString(R.string.behind)
            else {
                tvInfo.text = resources.getString(R.string.hit)
                bControl.text = resources.getString(R.string.play_more)
                gameFinished = true
            }
        } else {
            guess = (Math.random() * 100).toInt()
            bControl.text = resources.getString(R.string.input_value)
            tvInfo.text = resources.getText(R.string.try_to_guess)
            gameFinished = false
        }

        etInput.setText("")
    }
}