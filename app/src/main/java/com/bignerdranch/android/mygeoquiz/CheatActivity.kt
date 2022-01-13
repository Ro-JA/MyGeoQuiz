package com.bignerdranch.android.mygeoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import com.bignerdranch.android.mygeoquiz.R

const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.mygeoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE =
    "com.bignerdranch.android.mygeoquiz.answer_is_true"

private const val KEY_WAS_CHEATED = "was cheated"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var apiLevel: TextView

    private var answerIsTrue = false
    private var wasCheated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        apiLevel = findViewById(R.id.api_level_text_view)
        val buildingNumber = Build.VERSION.SDK_INT.toString()
        apiLevel.text = getString(R.string.api_level, buildingNumber)

        wasCheated = savedInstanceState?.getBoolean(KEY_WAS_CHEATED, false) ?: false
        setAnswerShownResult(wasCheated)



        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener {
            wasCheated = true
            setAnswerShownResult(true)
            fillTextIfCheated()
        }

        fillTextIfCheated()
    }

    private fun fillTextIfCheated() {
        if (wasCheated) {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_WAS_CHEATED, wasCheated)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        if (wasCheated) {
            val data = Intent().apply {
                putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
            }
            setResult(Activity.RESULT_OK, data)
        }
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}