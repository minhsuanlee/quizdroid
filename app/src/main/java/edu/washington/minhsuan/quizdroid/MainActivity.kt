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
            startActivity(intent)
        }

        val physicsButton = findViewById<Button>(R.id.btnPhysics)
        physicsButton.setOnClickListener {
            val intent = Intent(this@MainActivity, TopicActivity::class.java)
            intent.putExtra("Title", "Physics")
            intent.putExtra("Overview", "This is intended to test your basic knowledge of Physics\n\n" +
                    "There is 1 question")
            startActivity(intent)
        }

        val marvelButton = findViewById<Button>(R.id.btnMarvelHeroes)
        marvelButton.setOnClickListener {
            val intent = Intent(this@MainActivity, TopicActivity::class.java)
            intent.putExtra("Title", "Marvel Super Heroes")
            intent.putExtra("Overview", "This is intended to test your basic knowledge of Marvel Super Heroes\n\n" +
                    "There is 1 question")
            startActivity(intent)
        }
    }
}
