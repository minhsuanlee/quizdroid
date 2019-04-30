package edu.washington.minhsuan.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class TopicActivity : AppCompatActivity() {

    private val TAG = "TopicActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        intent.extras.apply {
            val titleMsg = this.getString("Title")
            val title = findViewById<View>(R.id.txtTopicTitle) as TextView
            title.text = titleMsg!!

            val overviewMsg = this.getString("Overview")
            val overview = findViewById<View>(R.id.txtOverview) as TextView
            overview.text = overviewMsg!!
        }

        val beginButton = findViewById<Button>(R.id.btnBegin)
        beginButton.setOnClickListener {
            val intent = Intent(this@TopicActivity, TopicActivity::class.java)
            intent.putExtra("Title", "Math")
            intent.putExtra("Overview", "This is intended to test your basic knowledge of Math")
            startActivity(intent)
        }
    }
}