package edu.washington.minhsuan.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView

class AnswerActivity : AppCompatActivity() {

    private val TAG = "TopicActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var questionsMsg: Array<String>
        var answersMsg: Array<String>
        var corrects: Array<String>
        var currentIndex: Int
        var total: Int
        val titleMsg: String

        intent.extras.apply {
            titleMsg = this.getString("Title")
            val title = findViewById<View>(R.id.txtAnswerNumber) as TextView
            title.text = titleMsg!!

            val correctMsg = this.getString("CorrectAns")
            val correct = findViewById<View>(R.id.txtCorrect) as TextView
            correct.text = "The Correct Answer is: \n ${correctMsg!!}"
            correct.gravity = Gravity.CENTER

            val userMsg = this.getString("UserAnswer")
            val userAnswer = findViewById<View>(R.id.txtUserAnswer) as TextView
            userAnswer.text = "Your answer was: \n ${userMsg!!}"
            userAnswer.gravity = Gravity.CENTER

            questionsMsg = this.getStringArray("Questions")
            answersMsg = this.getStringArray("Answers")
            corrects = this.getStringArray("Correct")

            currentIndex = this.getInt("CurrentIndex") + 1 // current question index

            total = this.getInt("TotalCorrect")

            if (correctMsg == userMsg) { total++ }
        }

        val nextButton = findViewById<Button>(R.id.btnNext)
        var hasNext = currentIndex < questionsMsg.size

        if (hasNext) {
            nextButton.text = "Next"
            nextButton.setOnClickListener {
                val intent = Intent(this@AnswerActivity, QuestionActivity::class.java)
                intent.putExtra("Title", titleMsg)
                intent.putExtra("Questions", questionsMsg)
                intent.putExtra("Answers", answersMsg)
                intent.putExtra("Correct", corrects)
                intent.putExtra("CurrentIndex", currentIndex)
                intent.putExtra("TotalCorrect", total)
                startActivity(intent)
            }
        } else {
            val txtScore = findViewById<TextView>(R.id.txtScore)
            txtScore.visibility = View.VISIBLE
            txtScore.text = "You have " + total + " out of " + questionsMsg.size +" correct"
            txtScore.gravity = Gravity.CENTER
            nextButton.text = "Finish"
            nextButton.setOnClickListener {
                val intent = Intent(this@AnswerActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}