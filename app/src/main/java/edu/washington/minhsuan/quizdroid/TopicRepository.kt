package edu.washington.minhsuan.quizdroid

import kotlin.collections.ArrayList

interface TopicRepository {
    fun makeTopic(title: String, shortDescription: String, longOverview: String, questions: ArrayList<String>,
                  answers: ArrayList<String>, corrects: ArrayList<String>): Topic

    fun makeQuestions(questions: ArrayList<String>, answers: ArrayList<String>,
                      corrects: ArrayList<String>): ArrayList<Quiz>

    fun getBundle(topic: String): Topic?

    fun getOverview(topic: String, number: Int): String
}

class QuizRepo(): TopicRepository {
    val TAG = "QuizRepo"

    private val NUM_CHOICES = 4

    private val mathQuestions = arrayListOf(
        "1 + 1 = ?",
        "2 * 3 = ?",
        "The average of first 50 natural numbers is ?"
    )
    private val mathAnswers = arrayListOf(
        "1", "2", "3", "4",
        "2", "3", "5", "6",
        "25.3", "25.5", "25.0", "12.25"
    )
    private val mathCorrect = arrayListOf("2", "6", "25.5")
    private val mathShort = "Hello Math!"
    private val mathOverview = getOverview("Math", mathQuestions.size)
    private val mathBundle = makeTopic("Math", mathShort, mathOverview, mathQuestions, mathAnswers,
            mathCorrect)

    private val physicsQuestions = arrayListOf("With the increase of pressure, the boiling point of any substance:")
    private val physicsAnswers = arrayListOf("Increases", "Decreases", "Remains Same", "Becomes zero")
    private val physicsCorrect = arrayListOf("Increases")
    private val physicsShort = "Hello Physics!"
    private val physicsOverview = getOverview("Physics", physicsQuestions.size)
    private val physicsBundle = makeTopic("Physics", physicsShort, physicsOverview, physicsQuestions,
            physicsAnswers, physicsCorrect)

    private val marvelQuestions = arrayListOf(
        "Which super hero is turned into dust in Avengers: Infinity War",
        "How many Avengers movies are there?",
        "Which one of the following is NOT one of the infinity stones?",
        "Which country is Black Panther from?",
        "What does Captain America carry with him into battle?"
    )
    private val marvelAnswers = arrayListOf(
        "Iron Man", "Spider-Man", "Thor", "Nebula",
        "1", "2", "3", "4",
        "Reality Stone", "Mind Stone", "Light Stone", "Time Stone",
        "Genosha", "Latvria", "Transia", "Wakanda",
        "Sheild", "Axe", "Sword", "Gun"
    )
    private val marvelCorrect = arrayListOf("Spider-Man", "4", "Light Stone", "Wakanda", "Sheild")
    private val marvelShort = "Hello Marvel!"
    private val marvelOverview = getOverview("Marvel Super Heroes", marvelQuestions.size)
    private val marvelBundle = makeTopic("Marvel Super Heroes", marvelShort, marvelOverview, marvelQuestions,
        marvelAnswers, marvelCorrect)

    private val topicMap = hashMapOf("Math" to mathBundle, "Physics" to physicsBundle,
            "Marvel Super Heroes" to marvelBundle)

    override fun makeTopic(title: String, shortDescription: String, longOverview: String,
                          questions: ArrayList<String>, answers: ArrayList<String>,
                          corrects: ArrayList<String>): Topic {
        val questionBundle = makeQuestions(questions, answers, corrects)

        return Topic(title, shortDescription, longOverview, questionBundle)
    }

    override fun makeQuestions(questions: ArrayList<String>, answers: ArrayList<String>,
                              corrects: ArrayList<String>): ArrayList<Quiz> {
        val numQuestions = questions.size
        var qArray = arrayListOf<Quiz>()
        var aArray: ArrayList<Array<String>> = arrayListOf()

        for (i in 0 until numQuestions) {
            val startIndex = i * NUM_CHOICES
            val endIndex = NUM_CHOICES * (i + 1)
            aArray.add(answers.slice(startIndex until endIndex).toTypedArray())
        }

        for (i in 0 until aArray.size) {
            qArray.add(Quiz(questions[i], aArray[i], corrects[i]))
        }

        return qArray
    }

    override fun getBundle(topic: String): Topic? {
        return topicMap[topic]
    }

    override fun getOverview(topic: String, number: Int): String {
        val unit = if (number == 1) " is " else " are "
        return "This is intended to test your basic knowledge of $topic\n\n" +
                "There$unit$number question(s)"
    }

    fun getShortDes(): Triple<String, String, String> {
        return Triple(mathShort, physicsShort, marvelShort)
    }
}

class Quiz(private val question: String, private val answers: Array<String>, private val correct: String) {
    fun getQuestion(): String { return question }

    fun getAnswers(): Array<String> { return answers }

    fun getCorrect(): String { return correct }
}

class Topic(private val topic: String, private val shortDes: String, private val longDes: String,
            private val questions: ArrayList<Quiz>) {

    fun getShortOverview(): String { return shortDes }

    fun getLongDes(): String { return longDes }

    fun getQuestions(): ArrayList<Quiz> { return questions }
}