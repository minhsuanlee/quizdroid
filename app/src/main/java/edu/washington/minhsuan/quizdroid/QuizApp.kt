package edu.washington.minhsuan.quizdroid

class QuizApp : android.app.Application() {

    lateinit var dataManager: TopicRepository
        private set

    companion object {
        lateinit var instance: QuizApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        dataManager = TopicRepository()
    }
}