package edu.iu.alex.midtermapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var mainTextView: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLatestScore()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.play_game_button).setOnClickListener {
            findNavController().navigate(R.id.mainFragment_to_gameFragment)
        }

        view.findViewById<Button>(R.id.view_scores_button).setOnClickListener {
            findNavController().navigate(R.id.mainFragment_to_highScoreFragment)
        }

        mainTextView = view.findViewById(R.id.textView)

        val lastScore = arguments?.getInt("lastScore", -1)
        val playerName = arguments?.getString("playerName", "")

        if (lastScore != null && lastScore != -1 && playerName != null && playerName.isNotEmpty()) {
            mainTextView.text = "$playerName score: $lastScore. Play another game?"
        } else {
            mainTextView.text = "Welcome to the game!"
        }


    }




}