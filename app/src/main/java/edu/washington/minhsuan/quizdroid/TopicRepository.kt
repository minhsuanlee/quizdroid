package edu.washington.minhsuan.quizdroid

class TopicRepository {

    class Topic(title: String, overview: String, questions: Array<String>,
                        answers: Array<String>, correct: Array<String>) {
        val NUM_CHOICES: Int = 4
        val title: String = title
        val overview: String = overview
        val questions: Array<String> = questions
        val numQuestions: Int = questions.size
        val answers: Array<String> = answers
        val correct: Array<String> = correct
        var currentIndex: Int = 0

        fun getTitle(name: String): String { return title }

        fun getOverviewInfo(): String { return overview }

        fun getTopic(): String { return title }

        fun hasNext(): Boolean { return currentIndex < numQuestions }

        fun next(): Triple<String, List<String>, String> {
            val startIndex = currentIndex * NUM_CHOICES
            val endIndex = NUM_CHOICES * (currentIndex + 1) - 1
            val current = Triple(questions[currentIndex],
                    answers.slice(startIndex until endIndex), correct[currentIndex])
            currentIndex++
            return current
        }
    }

    private val topics: ArrayList<Topic> = arrayListOf()

    fun add(title: String, overview: String, questions: Array<String>,
            answers: Array<String>, correct: Array<String>) {
        topics.add(Topic(title, overview, questions, answers, correct))
    }

    fun getData(index: Int): Topic { return topics[index] }

}