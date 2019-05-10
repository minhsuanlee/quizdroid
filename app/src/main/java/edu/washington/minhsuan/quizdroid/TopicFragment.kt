package edu.washington.minhsuan.quizdroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class TopicFragment : Fragment() {

    private val TAG = "TopicFragment"

    companion object {
        fun newInstance(titleMsg: String, overviewMsg: String, questions: Array<String>, answers: Array<String>,
                        corrects: Array<String>): TopicFragment {
            val args = Bundle().apply {
                putString("Title", titleMsg)
                putString("Overview", overviewMsg)
                putStringArray("Questions", questions)
                putStringArray("Answers", answers)
                putStringArray("Correct", corrects)
            }

            val fragment = TopicFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_topic, container, false)

        val topic = arguments?.let {
            val topic = it.getString("Title")
            val overview = it.getString("Overview")
            val questions = it.getStringArray("Questions")
            val answers = it.getStringArray("Answers")
            val correct = it.getStringArray("Correct")
            if (topic != null && overview != null && questions != null && answers != null && correct != null) {
                populateOverview(rootView, topic, overview, questions, answers, correct)
            }
        }

        return rootView
    }

    private fun populateOverview(rootView: View, titleMsg: String, overviewMsg: String, questions: Array<String>,
                                 answers: Array<String>, corrects: Array<String>) {
        val title = rootView.findViewById<View>(R.id.txtTopicTitle) as TextView
        title.text = titleMsg

        val overview = rootView.findViewById<View>(R.id.txtOverview) as TextView
        overview.text = overviewMsg
        overview.gravity = Gravity.CENTER

        val beginButton = rootView.findViewById<Button>(R.id.btnBegin)
        beginButton?.setOnClickListener {
            populateQuestionView(titleMsg, questions, answers, corrects, 0, 0)
        }
    }

    private fun populateQuestionView(titleMsg: String, questions: Array<String>, answers: Array<String>,
                         corrects: Array<String>, currentIndex: Int, totalCorrect: Int) {
        // create a fragment
        val questionFragment = QuestionFragment.newInstance(titleMsg, questions, answers, corrects,
                currentIndex, totalCorrect)

        // show it on the screen
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.container, questionFragment, "QUESTION_FRAGMENT")
            .addToBackStack(null)
            .commit()
    }
}
