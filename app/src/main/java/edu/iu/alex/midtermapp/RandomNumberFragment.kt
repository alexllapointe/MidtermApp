package edu.iu.alex.midtermapp

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import java.util.Random

class RandomNumberFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    private var randomNumber: Int = 0
    private var attempts: Int = 0
    private val random = Random()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        randomNumber = random.nextInt(100) + 1
        return inflater.inflate(R.layout.random_number_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnOK: Button = view.findViewById(R.id.btnOK)
        val btnPlus: ImageButton = view.findViewById(R.id.btnPlus)
        val btnMinus: ImageButton = view.findViewById(R.id.btnMinus)
        val userInput: EditText = view.findViewById(R.id.user_input_guess)
        val playerNameInput: EditText = view.findViewById(R.id.player_name_input)


        /**
         * Button 'OK' listener for when pressed.
         *
         * This logic serves the purpose of keeping track of the attempts, showing a toast (high or low) to help user,
         * and sending the user and their most recent score data back to the home screen.
         *
         *
         */

        btnOK.setOnClickListener {
            val userGuess = userInput.text.toString().toIntOrNull()

            if (userGuess != null) {
                attempts++
                viewModel.attempts.value = attempts
                when {
                    userGuess > randomNumber -> showToastLower()
                    userGuess < randomNumber -> showToastHigher()
                    else -> {

                        val playerName = playerNameInput.text.toString()
                        viewModel.savePlayerScore(playerName, attempts)

                        viewModel.attempts.value = 0

                        val bundle = Bundle()
                        bundle.putInt("lastScore", attempts)
                        bundle.putString("playerName", playerName)

                        findNavController().navigate(R.id.gameFragment_to_mainFragment, bundle)
                    }
                }
            }
        }

        /**
         * Button 'Plus' listener for when pressed.
         *
         * This logic allows the 'btnPlus' imagebutton to increment the current number of the user input.
         * It defaults to 0 if there is no user input.
         *
         *
         */

        btnPlus.setOnClickListener {
            val currentNumber = userInput.text.toString().toIntOrNull() ?: 0
            userInput.setText((currentNumber + 1).toString())
        }


        /**
         * Button 'Minus' listener for when pressed.
         *
         * This logic allows the 'btnMinus' imagebutton to decrement the current number of the user input.
         * It defaults to 0 if there is no user input and does not allow the user to go below 0.
         *
         */

        btnMinus.setOnClickListener {
            val currentNumber = userInput.text.toString().toIntOrNull() ?: 0
            if (currentNumber > 0) {
                userInput.setText((currentNumber - 1).toString())
            } else {
                userInput.setText("")
            }
        }
    }

    /**
     * Method for playing the Buzz Sound.
     *
     *
     */
    fun playBuzzSound() {
        val mediaPlayer = MediaPlayer.create(context, R.raw.buzz)
        mediaPlayer.start()

        mediaPlayer.setOnCompletionListener { mp -> mp.release() }
    }

    /**
     * Method for showing the lower toast to help nudge the user in the right direction.
     *
     *The sound method is used in this for cleaner code in the switch statement.
     */

    private fun showToastLower(){
        Toast.makeText(requireContext(), "Lower!", Toast.LENGTH_SHORT).show()
        playBuzzSound()
    }

    /**
     * Method for showing the higher toast to help nudge the user in the right direction.
     *
     *The sound method is used in this for cleaner code in the switch statement.
     */

    private fun showToastHigher(){
        Toast.makeText(requireContext(), "Higher!", Toast.LENGTH_SHORT).show()
        playBuzzSound()
    }
}