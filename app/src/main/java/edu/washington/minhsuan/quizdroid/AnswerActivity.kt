package edu.washington.minhsuan.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

        var hasNext: Boolean

        intent.extras.apply {
            val titleMsg: String = this.getString("Title")
            val title = findViewById<View>(R.id.txtAnswerNumber) as TextView
            title.text = titleMsg!!

            val correctMsg = this.getString("Correct")
            val correct = findViewById<View>(R.id.txtCorrect) as TextView
            correct.text = "The Correct Answer is: \n ${correctMsg!!}"
            correct.gravity = Gravity.CENTER

            val userMsg = this.getString("UserAnswer")
            val userAnswer = findViewById<View>(R.id.txtUserAnswer) as TextView
            userAnswer.text = "Your answer was: \n ${userMsg!!}"
            userAnswer.gravity = Gravity.CENTER

            hasNext = this.getBoolean("hasNext")
        }

        val nextButton = findViewById<Button>(R.id.btnNext)
        if (hasNext) {
            nextButton.text = "Next"
            nextButton.setOnClickListener {
//                val intent = Intent(this@AnswerActivity, QuestionActivity::class.java)
//                intent.putExtra("Title", "Math")
//                intent.putExtra("Questions", questions)
//                intent.putExtra("Answers", answers)
//                intent.putExtra("Correct", correctIndex)
//                startActivity(intent)
            }
        } else {
            nextButton.text = "Finish"
            nextButton.setOnClickListener {
                val intent = Intent(this@AnswerActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}