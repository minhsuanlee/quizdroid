package edu.washington.minhsuan.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView

class TopicActivity : AppCompatActivity() {

    private val TAG = "TopicActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var questions: Array<String>
        var answers: Array<String>
        var corrects: Array<String>
        var titleMsg: String

        intent.extras.apply {
            titleMsg = this.getString("Title")
            val title = findViewById<View>(R.id.txtTopicTitle) as TextView
            title.text = titleMsg!!

            val overviewMsg = this.getString("Overview")
            val overview = findViewById<View>(R.id.txtOverview) as TextView
            overview.text = overviewMsg!!
            overview.gravity = Gravity.CENTER

            questions = this.getStringArray("Questions")
            answers = this.getStringArray("Answers")
            corrects = this.getStringArray("Correct")
        }

        val beginButton = findViewById<Button>(R.id.btnBegin)
        beginButton.setOnClickListener {
            val intent = Intent(this@TopicActivity, QuestionActivity::class.java)
            intent.putExtra("Title", titleMsg)
            intent.putExtra("Questions", questions)
            intent.putExtra("Answers", answers)
            intent.putExtra("Correct", corrects)
            intent.putExtra("CurrentIndex", 0)
            intent.putExtra("TotalCorrect", 0)
            startActivity(intent)
        }
    }
}