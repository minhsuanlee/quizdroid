package edu.washington.minhsuan.quizdroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MultiActivity : AppCompatActivity() {
    private val TAG = "MultiActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var overviewMsg: String
        var questions: Array<String>
        var answers: Array<String>
        var corrects: Array<String>
        var titleMsg: String

        intent.extras.apply {
            titleMsg = this.getString("Title")
            overviewMsg = this.getString("Overview")
            questions = this.getStringArray("Questions")
            answers = this.getStringArray("Answers")
            corrects = this.getStringArray("Correct")
        }

        // create a fragment
        val topicFragment = TopicFragment.newInstance(titleMsg, overviewMsg, questions, answers, corrects)

        // show it on the screen
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, topicFragment, "TOPIC_FRAGMENT")
            .addToBackStack(null)
            .commit()
    }
}