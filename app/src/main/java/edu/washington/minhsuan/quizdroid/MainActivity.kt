package edu.washington.minhsuan.quizdroid

import android.Manifest
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
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.activity_main.*
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_NEUTRAL
import android.os.Build
import android.support.v7.app.AlertDialog


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val INTERNET_REQUEST_CODE = 1
    private val DEFAULT_URL = "http://tednewardsandbox.site44.com/questions.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions()

        if (!isNetworkConnected()) { Toast.makeText(this, "No Internet Connection.", Toast.LENGTH_SHORT) }

        if (Settings.Global.AIRPLANE_MODE_ON == "airplane_mode_on") {
            val builder = AlertDialog.Builder(this)
            builder.apply {
                setTitle("Turn off Airplane Mode?")
                setMessage("Would you like to go to Settings and turn off Airplane Mode?")
                setPositiveButton("Yes") { dialog, which -> startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0) }
                setNegativeButton("No") { dialog, which ->  Log.v(TAG, "They said no")}
            }
            builder.create().show()
        }

        // get singleton of app
        val singletonApp = QuizApp.instance
        val preference = getPreferences(Context.MODE_PRIVATE)

        val saveBtn = findViewById<Button>(R.id.btnSave)
        val stopBtn = findViewById<Button>(R.id.btnStop)
        val btnClear = findViewById<Button>(R.id.btnClear)

        val url = findViewById<EditText>(R.id.txtUrl)
        val mode = findViewById<EditText>(R.id.txtMode)

        saveBtn.setOnClickListener {
            var error = ""
            if (url.text.isEmpty()) { error = "$error Please enter a url;\n"}
            if (mode.text.isEmpty()) { error = "$error Please enter a delay time;"}

            if (error.isNotEmpty()) {
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
            } else {
                preference.edit().putString("url", url.text.toString()).apply()
                preference.edit().putInt("min", mode.text.toString().toInt()).apply()
                handleStart(preference.getString("url", DEFAULT_URL), preference.getInt("min", 1))
            }
        }

        stopBtn.setOnClickListener {
            handleStop()
        }

        btnClear.setOnClickListener {
            url.setText("")
            mode.setText("")
        }

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

    private fun handleStart(url: String, time: Int) {
        Log.i(TAG, "Start pressed")
        btnSave.isEnabled = false
        btnStop.isEnabled = true
        val intent = Intent(this@MainActivity, UrlService::class.java)
        intent.putExtra("Url", url)
        intent.putExtra("Time", time)
        startService(intent)
    }

    fun handleStop() {
        Log.i(TAG, "Stop pressed")
        stopService(Intent(this@MainActivity, UrlService::class.java))
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

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetworkInfo == null) { return false } else { return cm.activeNetworkInfo.isConnected }
    }
}
