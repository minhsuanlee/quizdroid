package edu.washington.minhsuan.quizdroid

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class AnswerFragment : Fragment() {

    private val TAG = "AnswerFragment"

    companion object {
        fun newInstance(titleMsg: String, questions: Array<String>, answers: Array<String>,
                        corrects: Array<String>, correctMsg: String, userMsg: String, currentIndex: Int,
                        totalCorrect: Int): AnswerFragment {
            val args = Bundle().apply {
                putString("Title", titleMsg)
                putStringArray("Questions", questions)
                putStringArray("Answers", answers)
                putStringArray("Correct", corrects)
                putString("CorrectAns", correctMsg)
                putString("UserAns", userMsg)
                putInt("CurrentIndex", currentIndex)
                putInt("TotalCorrect", totalCorrect)
            }

            val fragment = AnswerFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_answer, container, false)

        val topic = arguments?.let {
            val topic = it.getString("Title")
            val questions = it.getStringArray("Questions")
            val answers = it.getStringArray("Answers")
            val correct = it.getStringArray("Correct")
            val correctAns = it.getString("CorrectAns")
            val userAns = it.getString("UserAns")
            val currentIndex = it.getInt("CurrentIndex") + 1
            val totalCorrect = it.getInt("TotalCorrect")

            if (topic != null && questions != null && answers != null && correct != null) {
                populateAnswerView(rootView, topic, questions, answers, correct, correctAns, userAns, currentIndex,
                    totalCorrect)
            }
        }

        return rootView
    }

    private fun populateAnswerView(rootView: View, titleMsg: String, questions: Array<String>, answers: Array<String>,
                                   corrects: Array<String>, correctAns: String, userAns: String, currentIndex: Int,
                                   totalCorrect: Int) {
        val title = rootView.findViewById<View>(R.id.txtAnswerNumber) as TextView
        title.text = titleMsg

        val correct = rootView.findViewById<View>(R.id.txtCorrect) as TextView
        correct.text = "The Correct Answer is: \n $correctAns"
        correct.gravity = Gravity.CENTER

        val userAnswer = rootView.findViewById<View>(R.id.txtUserAnswer) as TextView
        userAnswer.text = "Your answer was: \n $userAns"
        userAnswer.gravity = Gravity.CENTER

        var newTotal = totalCorrect
        if (correctAns == userAns) { newTotal++ }

        val nextButton = rootView.findViewById<Button>(R.id.btnNext)
        var hasNext = currentIndex < questions.size

        if (hasNext) {
            nextButton.text = "Next"
            nextButton.setOnClickListener {
                // create a fragment
                val questionFragment = QuestionFragment.newInstance(titleMsg, questions, answers,
                    corrects, currentIndex, newTotal)

                // show it on the screen
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, questionFragment, "QUESTION_FRAGMENT")
                    .addToBackStack(null)
                    .commit()
            }
        } else {
            val txtScore = rootView.findViewById<TextView>(R.id.txtScore)
            txtScore.visibility = View.VISIBLE
            txtScore.text = "You have $newTotal out of ${questions.size} correct"
            txtScore.gravity = Gravity.CENTER
            nextButton.text = "Finish"
            nextButton.setOnClickListener {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
