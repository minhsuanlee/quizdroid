package edu.washington.minhsuan.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class QuestionActivity : AppCompatActivity() {

    private val TAG = "QuestionActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }
}