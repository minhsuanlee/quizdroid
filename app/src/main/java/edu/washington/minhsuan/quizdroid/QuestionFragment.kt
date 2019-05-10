package edu.washington.minhsuan.quizdroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class QuestionFragment : Fragment() {

    private val TAG = "QuestionFragment"

    companion object {
        fun newInstance(titleMsg: String, questions: Array<String>, answers: Array<String>,
                        corrects: Array<String>, currentIndex: Int, totalCorrect: Int): QuestionFragment {
            val args = Bundle().apply {
                putString("Title", titleMsg)
                putStringArray("Questions", questions)
                putStringArray("Answers", answers)
                putStringArray("Correct", corrects)
                putInt("CurrentIndex", currentIndex)
                putInt("TotalCorrect", totalCorrect)
            }

            val fragment = QuestionFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_question, container, false)

        val topic = arguments?.let {
            val topic = it.getString("Title")
            val questions = it.getStringArray("Questions")
            val answers = it.getStringArray("Answers")
            val correct = it.getStringArray("Correct")
            val currentIndex = it.getInt("CurrentIndex")
            val totalCorrect = it.getInt("TotalCorrect")

            if (topic != null && questions != null && answers != null && correct != null) {
                populateQuestionView(rootView, topic, questions, answers, correct, currentIndex, totalCorrect)
            }
        }

        return rootView
    }

    private fun populateQuestionView(rootView: View, titleMsg: String, questions: Array<String>, answers: Array<String>,
                                     corrects: Array<String>, currentIndex: Int, totalCorrect: Int) {
        val title = rootView.findViewById<View>(R.id.txtQuestionNumber) as TextView
        title.text = titleMsg

        val questionTxt = rootView.findViewById<View>(R.id.txtQuestion) as TextView
        questionTxt.text = questions[currentIndex]
        questionTxt.gravity = Gravity.CENTER

        val radioG = rootView.findViewById<RadioGroup>(R.id.radioAnswers)

        val submitButton = rootView.findViewById<Button>(R.id.btnSubmit)
        submitButton.setOnClickListener {
            val selectedId = radioG.getCheckedRadioButtonId()
            val radioButton = rootView.findViewById<View>(selectedId) as RadioButton

            // create a fragment
            val answerFragment = AnswerFragment.newInstance(titleMsg, questions, answers, corrects,
                corrects[currentIndex], "" + radioButton.text, currentIndex, totalCorrect)

            // show it on the screen
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.container, answerFragment, "ANSWER_FRAGMENT")
                .addToBackStack(null)
                .commit()
        }

        radioG.setOnCheckedChangeListener { group, checkedId ->
            submitButton.visibility = View.VISIBLE
        }

        val numAnswers = radioG.childCount
        for (i in 0..numAnswers) {
            var btn = radioG.getChildAt(i)
            if (btn != null) {
                (btn as RadioButton).text = answers[i + currentIndex * numAnswers]
            }
        }
    }
}
