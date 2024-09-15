package top.mk85.mike.guessthenumber

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
    private lateinit var tvTries: TextView

    private var guess: Int = 0
    private var gameFinished: Boolean = false
    private var tries: Int = 1

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
        tvTries = findViewById(R.id.textView3)

        guess = (Math.random() * 100).toInt()

        tvTries.text = String.format(resources.getString(R.string.tries), tries.toString())

        etInput.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                getResult(this.currentFocus)
                handled = true
            }
            handled
        }
    }

    fun onClick(v:View) {
        getResult(v)
    }

    private fun getResult(v:View?) {
        val tInp: String = etInput.text.toString()
        if (!gameFinished && tInp.isEmpty()) return

        if (!gameFinished) {
            val inp: Int = tInp.toInt()

            if (inp > guess) {
                tvInfo.text = resources.getString(R.string.ahead)
            } else if (inp < guess) {
                tvInfo.text = resources.getString(R.string.behind)
            }
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
            tries = 0
        }

        if (gameFinished) {
            tvTries.visibility = View.INVISIBLE
            etInput.visibility = View.INVISIBLE
            if (v != null) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
        } else {
            tries++
            tvTries.visibility = View.VISIBLE
            tvTries.text = String.format(resources.getString(R.string.tries), tries.toString())

            etInput.visibility = View.VISIBLE
            etInput.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etInput, InputMethodManager.SHOW_IMPLICIT)
        }
        etInput.setText("")
    }
}