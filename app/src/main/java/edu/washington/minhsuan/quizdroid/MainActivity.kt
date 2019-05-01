package edu.washington.minhsuan.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mathButton = findViewById<Button>(R.id.btnMath)
        mathButton.setOnClickListener {
            val intent = Intent(this@MainActivity, TopicActivity::class.java)
            intent.putExtra("Title", "Math")
            intent.putExtra("Overview", "This is intended to test your basic knowledge of Math.\n\n" +
                    "There is 1 question")
            intent.putExtra("Questions", arrayOf("1 + 1 = ?"))
            intent.putExtra("Answers", arrayOf("1", "2", "3", "4"))
            intent.putExtra("Correct", 1)
            startActivity(intent)
        }

        val physicsButton = findViewById<Button>(R.id.btnPhysics)
        physicsButton.setOnClickListener {
            val intent = Intent(this@MainActivity, TopicActivity::class.java)
            intent.putExtra("Title", "Physics")
            intent.putExtra("Overview", "This is intended to test your basic knowledge of Physics\n\n" +
                    "There is 1 question")
            intent.putExtra("Questions", arrayOf("With the increase of pressure, the boiling point of any substance:"))
            intent.putExtra("Answers", arrayOf("Increases", "Decreases", "Remains Same", "Becomes zero"))
            intent.putExtra("Correct", 0)
            startActivity(intent)
        }

        val marvelButton = findViewById<Button>(R.id.btnMarvelHeroes)
        marvelButton.setOnClickListener {
            val intent = Intent(this@MainActivity, TopicActivity::class.java)
            intent.putExtra("Title", "Marvel Super Heroes")
            intent.putExtra("Overview", "This is intended to test your basic knowledge of Marvel Super Heroes\n\n" +
                    "There is 1 question")
            intent.putExtra("Questions", arrayOf("Which super hero is turned into dust in Avengers: Infinity War"))
            intent.putExtra("Answers", arrayOf("Iron Man", "Spider-Man", "Thor", "Nebula"))
            intent.putExtra("Correct", 1)
            startActivity(intent)
        }
    }
}
