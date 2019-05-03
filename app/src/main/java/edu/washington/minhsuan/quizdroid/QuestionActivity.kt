package edu.washington.minhsuan.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        var corrects: Array<String>
        var titleMsg: String
        var currentIndex: Int
        var total: Int

        val submitButton = findViewById<Button>(R.id.btnSubmit)
        val radioG = findViewById<RadioGroup>(R.id.radioAnswers)

        intent.extras.apply {
            titleMsg = this.getString("Title")
            val title = findViewById<View>(R.id.txtQuestionNumber) as TextView
            title.text = titleMsg!!

            questionsMsg = this.getStringArray("Questions")
            answersMsg = this.getStringArray("Answers")
            corrects = this.getStringArray("Correct")
            currentIndex = this.getInt("CurrentIndex") // current question index
            total = this.getInt("TotalCorrect")
        }

        populateQuestion(questionsMsg!![currentIndex])

        radioG.setOnCheckedChangeListener { group, checkedId ->
            submitButton.visibility = View.VISIBLE
        }

        submitButton.setOnClickListener {
            val selectedId = radioG.getCheckedRadioButtonId()
            val radioButton = findViewById<View>(selectedId) as RadioButton

            val intent = Intent(this@QuestionActivity, AnswerActivity::class.java)
            intent.putExtra("Title", titleMsg)
            intent.putExtra("CorrectAns", corrects[currentIndex])
            intent.putExtra("UserAnswer", radioButton.text)
            intent.putExtra("CurrentIndex", currentIndex)
            intent.putExtra("Questions", questionsMsg)
            intent.putExtra("Answers", answersMsg)
            intent.putExtra("Correct", corrects)
            intent.putExtra("TotalCorrect", total)
            startActivity(intent)
        }

        populateAnswers(answersMsg, radioG, currentIndex)
    }

    private fun populateQuestion(question: String) {
        val questionTxt = findViewById<View>(R.id.txtQuestion) as TextView
        questionTxt.text = question
        questionTxt.gravity = Gravity.CENTER
    }

    private fun populateAnswers(answers: Array<String>, radioG: RadioGroup, currentIndex: Int) {
        val numAnswers = radioG.childCount
        for (i in 0..numAnswers) {
            var btn = radioG.getChildAt(i)
            if (btn != null) {
                Log.v(TAG, answers[i + currentIndex * numAnswers])
                (btn as RadioButton).text = answers[i + currentIndex * numAnswers]
            }
        }

    }
}