package edu.washington.minhsuan.quizdroid

import android.Manifest
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
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

        // get singleton of app
        val singletonApp = QuizApp.instance

        // get repository
        val quizRepo = singletonApp.topicRepo

        val (math, phys, marvel) = quizRepo.getShortDes()

        findViewById<TextView>(R.id.txtMath).text = math
        findViewById<TextView>(R.id.txtPhys).text = phys
        findViewById<TextView>(R.id.txtMarvel).text = marvel

        val mathButton = findViewById<Button>(R.id.btnMath)
        mathButton.setOnClickListener {
            startIntent("Math", quizRepo)
        }

        val physicsButton = findViewById<Button>(R.id.btnPhysics)
        physicsButton.setOnClickListener {
            startIntent("Physics", quizRepo)
        }

        val marvelButton = findViewById<Button>(R.id.btnMarvelHeroes)
        marvelButton.setOnClickListener {
            startIntent("Marvel Super Heroes", quizRepo)
        }
    }

    private fun startIntent(topic: String, quizRepo: QuizRepo) {
        val bundle = quizRepo.getBundle(topic)
        if (bundle != null) {
            val (questions, answers, corrects) =
                getQuestions(bundle.getQuestions())
            val intent = Intent(this@MainActivity, MultiActivity::class.java)
            intent.putExtra("Title", topic)
            intent.putExtra("Short", bundle.getShortOverview())
            intent.putExtra("Overview", bundle.getLongDes())
            intent.putExtra("Questions", questions)
            intent.putExtra("Answers", answers)
            intent.putExtra("Correct", corrects)
            Log.v(TAG, Arrays.toString(questions))
            Log.v(TAG, Arrays.toString(answers))
            Log.v(TAG, Arrays.toString(corrects))
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
