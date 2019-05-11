package edu.washington.minhsuan.quizdroid

import android.Manifest
import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val INTERNET_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions()

        val preference = getPreferences(Context.MODE_PRIVATE)
        val saveBtn = findViewById<Button>(R.id.btnSave)
        val url = findViewById<EditText>(R.id.txtUrl)
        val mode = findViewById<EditText>(R.id.txtMode)
        url.hint = preference.getString("url", "Please type your url here.")
        val num = preference.getInt("min", 0)
        if (num == 0) {
            mode.hint = "Please type a number"
        } else {
            mode.hint = num.toString()
        }
        saveBtn.setOnClickListener {
            preference.edit().putString("url", url.text.toString()).apply()
            preference.edit().putInt("min", mode.text.toString().toInt()).apply()
        }

        // get singleton of app
        val singletonApp = QuizApp.instance

        // get repository
        val quizRepo = singletonApp.topicRepo
        val shorts = quizRepo.getShortDes()
        val topics = quizRepo.getTopics()

        findViewById<TextView>(R.id.txtMath).text = shorts[0]
        findViewById<TextView>(R.id.txtPhys).text = shorts[1]
        findViewById<TextView>(R.id.txtMarvel).text = shorts[2]

        val mathButton = findViewById<Button>(R.id.btnMath)
        mathButton.text = topics[0]
        mathButton.setOnClickListener {
            startIntent(topics[0], quizRepo)
        }

        val physicsButton = findViewById<Button>(R.id.btnPhysics)
        physicsButton.text = topics[1]
        physicsButton.setOnClickListener {
            startIntent(topics[1], quizRepo)
        }

        val marvelButton = findViewById<Button>(R.id.btnMarvelHeroes)
        marvelButton.text = topics[2]
        marvelButton.setOnClickListener {
            startIntent(topics[2], quizRepo)
        }
    }

    private fun startIntent(topic: String, quizRepo: JsonRepo) {
        val bundle = quizRepo.getBundle(topic)
        if (bundle != null) {
            val (questions, answers, corrects) =
                getQuestions(bundle.getQuestions())
            val intent = Intent(this@MainActivity, MultiActivity::class.java)
            intent.putExtra("Title", topic)
            intent.putExtra("Overview", bundle.getLongDes())
            intent.putExtra("Questions", questions)
            intent.putExtra("Answers", answers)
            intent.putExtra("Correct", corrects)
            startActivity(intent)
        }
    }

    // questions, answers, corrects
    private fun getQuestions(questionBundle: ArrayList<Quiz>): Triple<Array<String>, Array<String>, Array<String>> {
        var questions = arrayListOf<String>()
        var answer = arrayListOf<Array<String>>()
        var corrects = arrayListOf<String>()
        var answers = arrayListOf<String>()

        for (question in questionBundle) {
            questions.add(question.getQuestion())
            answer.add(question.getAnswers())
            corrects.add(question.getCorrect())
        }

        for (a in answer) {
            for (s in a) {
                answers.add(s)
            }
        }

        return Triple(questions.toTypedArray(), answers.toTypedArray(), corrects.toTypedArray())
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.INTERNET)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeRequest()
        } else {
            Toast.makeText(this, "Thank you for granting Internet Permission!", Toast.LENGTH_LONG).show()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET), INTERNET_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            INTERNET_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
    }
}
