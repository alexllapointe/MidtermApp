package edu.iu.alex.midtermapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.View.*

class HighScoreFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ScoreAdapter
    private lateinit var emptyTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.highscore_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)


        recyclerView = view.findViewById(R.id.scoreRecyclerView)
        emptyTextView = view.findViewById(R.id.emptyTextView)

        /**
         * Used to delete the score from the database and refresh the recycler view all in one method.
         *
         * If there are no more scores after the deleted one, then the record of scores will become empty.
         *
         * Dialog appears here.
         *
         */

        adapter = ScoreAdapter { score ->

            val dialog = DeleteDialogFragment {
                viewModel.deleteScore(score)
                adapter.setScores(viewModel.getAllScores(requireContext()).value ?: emptyList())
            }
            dialog.show(parentFragmentManager, "deleteDialog")
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        /**
         * If there are no entries to the recycler view, the empty textview will show.
         *
         *
         */

        viewModel.getAllScores(requireContext()).observe(viewLifecycleOwner) { scores ->
            adapter.setScores(scores)

            if (scores.isEmpty()) {
                emptyTextView.visibility = VISIBLE
                recyclerView.visibility = GONE
            } else {
                emptyTextView.visibility = GONE
                recyclerView.visibility = VISIBLE
            }
        }
    }

}
