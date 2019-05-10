package edu.washington.minhsuan.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mathQuestions = arrayOf(
            "1 + 1 = ?",
            "2 * 3 = ?",
            "The average of first 50 natural numbers is ?"
        )
        val mathAnswers = arrayOf(
            "1", "2", "3", "4",
            "2", "3", "5", "6",
            "25.3", "25.5", "25.0", "12.25"
        )
        val mathCorrect = arrayOf("2", "6", "25.5")

        val physicsQuestions = arrayOf("With the increase of pressure, the boiling point of any substance:")
        val physicsAnswers = arrayOf("Increases", "Decreases", "Remains Same", "Becomes zero")
        val physicsCorrect = arrayOf("Increases")

        val marvelQuestions = arrayOf(
            "Which super hero is turned into dust in Avengers: Infinity War",
            "How many Avengers movies are there?",
            "Which one of the following is NOT one of the infinity stones?",
            "Which country is Black Panther from?",
            "What does Captain America carry with him into battle?"
        )
        val marvelAnswers = arrayOf(
            "Iron Man", "Spider-Man", "Thor", "Nebula",
            "1", "2", "3", "4",
            "Reality Stone", "Mind Stone", "Light Stone", "Time Stone",
            "Genosha", "Latvria", "Transia", "Wakanda",
            "Sheild", "Axe", "Sword", "Gun"
        )
        val marvelCorrect = arrayOf("Spider-Man", "4", "Light Stone", "Wakanda", "Sheild")

        val mathButton = findViewById<Button>(R.id.btnMath)
        mathButton.setOnClickListener {
            val topic = "Math"
            val unit = if (mathQuestions.size == 1) " is " else " are "
            val intent = Intent(this@MainActivity, MultiActivity::class.java)
            intent.putExtra("Title", "Math")
            intent.putExtra("Overview", getOverview(topic, mathQuestions.size, unit))
            intent.putExtra("Questions", mathQuestions)
            intent.putExtra("Answers", mathAnswers)
            intent.putExtra("Correct", mathCorrect)
            startActivity(intent)
        }

        val physicsButton = findViewById<Button>(R.id.btnPhysics)
        physicsButton.setOnClickListener {
            val unit = if (physicsQuestions.size == 1) " is " else " are "
            val topic = "Physics"
            val intent = Intent(this@MainActivity, MultiActivity::class.java)
            intent.putExtra("Title", "Physics")
            intent.putExtra("Overview", getOverview(topic, physicsQuestions.size, unit))
            intent.putExtra("Questions", physicsQuestions)
            intent.putExtra("Answers", physicsAnswers)
            intent.putExtra("Correct", physicsCorrect)
            startActivity(intent)
        }

        val marvelButton = findViewById<Button>(R.id.btnMarvelHeroes)
        marvelButton.setOnClickListener {
            val unit = if (marvelQuestions.size == 1) " is " else " are "
            val topic = "Marvel Super Heroes"
            val intent = Intent(this@MainActivity, MultiActivity::class.java)
            intent.putExtra("Title", "Marvel Super Heroes")
            intent.putExtra("Overview", getOverview(topic, marvelQuestions.size, unit))
            intent.putExtra("Questions", marvelQuestions)
            intent.putExtra("Answers", marvelAnswers)
            intent.putExtra("Correct", marvelCorrect)
            startActivity(intent)
        }
    }

    private fun getOverview(topic: String, number: Int, unit: String): String {
        return "This is intended to test your basic knowledge of $topic\n\n" +
                "There$unit$number question(s)"
    }
}
