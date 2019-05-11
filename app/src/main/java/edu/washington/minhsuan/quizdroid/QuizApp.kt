package edu.washington.minhsuan.quizdroid

import android.util.Log

class QuizApp : android.app.Application() {

    val TAG = "QuizApp"

    lateinit var topicRepo: QuizRepo
        private set

    companion object {
        lateinit var instance: QuizApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        Log.v(TAG, "It's loaded and running!")

        instance = this

        topicRepo = QuizRepo()
    }
}