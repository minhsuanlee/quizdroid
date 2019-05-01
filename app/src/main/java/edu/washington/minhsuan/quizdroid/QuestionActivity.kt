package edu.washington.minhsuan.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class QuestionActivity : AppCompatActivity() {

    private val TAG = "QuestionActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var questionsMsg: Array<String>
        var answersMsg: Array<String>
        var correctIndex: Int
        var titleMsg: String

        val submitButton = findViewById<Button>(R.id.btnSubmit)
        val radioG = findViewById<RadioGroup>(R.id.radioAnswers)

        intent.extras.apply {
            titleMsg = this.getString("Title")
            val title = findViewById<View>(R.id.txtQuestionNumber) as TextView
            title.text = titleMsg!!

            questionsMsg = this.getStringArray("Questions")
            answersMsg = this.getStringArray("Answers")
            correctIndex = this.getInt("Correct")
        }

        populateQuestion(questionsMsg!![0])

        radioG.setOnCheckedChangeListener { group, checkedId ->
            submitButton.visibility = View.VISIBLE
        }

        submitButton.setOnClickListener {
            val selectedId = radioG.getCheckedRadioButtonId()
            val radioButton = findViewById<View>(selectedId) as RadioButton

            val intent = Intent(this@QuestionActivity, AnswerActivity::class.java)
            intent.putExtra("Title", titleMsg)
            intent.putExtra("Correct", answersMsg[correctIndex])
            intent.putExtra("UserAnswer", radioButton.text)
            intent.putExtra("hasNext", false)
            startActivity(intent)
        }

        populateAnswers(answersMsg, radioG)
    }

    private fun populateQuestion(question: String) {
        val questionTxt = findViewById<View>(R.id.txtQuestion) as TextView
        questionTxt.text = question
        questionTxt.gravity = Gravity.CENTER
    }

    private fun populateAnswers(answers: Array<String>, radioG: RadioGroup) {
        for (i in 1..radioG.childCount) {
            var btn = radioG.getChildAt(i - 1)
            if (btn != null) {
                (btn as RadioButton).text = answers[i - 1]
            }
        }

    }
}