package edu.washington.minhsuan.quizdroid

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import org.json.JSONObject
import java.io.IOException
import org.json.JSONArray
import java.util.*


class QuizApp : android.app.Application() {

    val TAG = "QuizApp"

    private lateinit var sharedPreferences: SharedPreferences

    lateinit var topicRepo: JsonRepo
        private set

    companion object {
        lateinit var instance: QuizApp
            private set
        const val JSON_FILE_NAME = "questions.json"
        const val TITLE = "title"
        const val DESC = "desc"
        const val QUESTIONS = "questions"
        const val TEXT = "text"
        const val ANSWER = "answer"
        const val ANSWERS = "answers"

        private const val USER_PREF_KEY = "USER_PREFERENCES_KEY"
        private const val TIMESTAMP_KEY = "timestamp"
    }

    override fun onCreate() {
        super.onCreate()
        Log.v(TAG, "It's loaded and running!")

        instance = this

        topicRepo = JsonRepo()

        readWriteJson()
        readJson()
    }

    fun readWriteJson() {
        sharedPreferences = getSharedPreferences(USER_PREF_KEY, Context.MODE_PRIVATE)

        // Get current time stamp from SharedPreferences & print it
        val defaultErrorValue = -1L
        val timestamp = sharedPreferences.getLong("timestamp", defaultErrorValue)
        Log.i(TAG, "Current Shared preferences of time stamp = $timestamp")

        // Updated timestamp in SharedPreferences & print it
        sharedPreferences
            .edit()
            .putLong(TIMESTAMP_KEY, System.currentTimeMillis() + 1000)
            .apply()
        Log.i(TAG, "New shared preferences of time stamp = ${sharedPreferences.getLong("timestamp",
            defaultErrorValue)}")
    }

    fun readJson() {
        val jsonString: String? = try {
            // grab file from assets folder & read it to a String
            val inputStream = assets.open(JSON_FILE_NAME)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            null
        }

        jsonString?.let {
            // Create json from string
            val jsonArray = JSONArray(jsonString)
            val topics = arrayListOf<String>()
            val bundles = arrayListOf<Topic>()
            val shorts = arrayListOf<String>()

            // Read JSON array
            for (i in 0 until jsonArray.length()) {
                // get data of array value at index
                val topicJSONObject = jsonArray.get(i) as JSONObject

                // Get data value of key
                val title = topicJSONObject.get(TITLE) as String
                topics.add(title)
                val shortDes = "Hello $title"
                shorts.add(shortDes)
                val overview = topicJSONObject.get(DESC) as String
                val questionsJson = topicJSONObject.get(QUESTIONS) as JSONArray

                val questions = arrayListOf<String>()
                val answers = arrayListOf<String>()
                val corrects = arrayListOf<String>()

                for (j in 0 until questionsJson.length()) {
                    val quizJSONObject = questionsJson.get(j) as JSONObject
                    val question = quizJSONObject.get(TEXT) as String
                    val answerIndex = quizJSONObject.get(ANSWER) as String
                    val answersJson = quizJSONObject.get(ANSWERS) as JSONArray

                    questions.add(question)

                    for (k in 0 until answersJson.length()) {
                        answers.add(answersJson.get(k) as String)
                    }

                    corrects.add(answers[answerIndex.toInt()])
                }

                val bundle = topicRepo.makeTopic(title, shortDes, overview, questions, answers, corrects)
                bundles.add(bundle)
            }

            topicRepo.setMap(topics, bundles)
            topicRepo.setTopic(topics)
            topicRepo.setShorts(shorts)
        }
    }
}