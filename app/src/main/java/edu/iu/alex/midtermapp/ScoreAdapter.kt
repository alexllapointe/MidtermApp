package edu.iu.alex.midtermapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoreAdapter(private val onDeleteClick: (Score) -> Unit) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    private var scores: MutableList<Score> = mutableListOf()

    inner class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerName: TextView = itemView.findViewById(R.id.playerNameTextView)
        val guesses: TextView = itemView.findViewById(R.id.guessesTextView)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_score, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.playerName.text = score.name
        holder.guesses.text = "${score.attempts} guesses"


        holder.guesses.setOnClickListener {
            if (holder.playerName.visibility == View.VISIBLE) {
                holder.playerName.visibility = View.GONE
            } else {
                holder.playerName.visibility = View.VISIBLE
            }
        }

        /**
         * Keeps track of when delete is pressed for the delete dialog(called within ScoreAdapater).
         *
         */

        holder.deleteButton.setOnClickListener {
            onDeleteClick(score)
        }
    }

    override fun getItemCount(): Int = scores.size

    /**
     * This method adds all the scores to be used wihtin the Recycler View.
     *
     * It is necessary for all of the score items to appear correctly within the view.
     *
     *@param newScores List<Score>
     */

    fun setScores(newScores: List<Score>) {
        scores.clear()
        scores.addAll(newScores)
        notifyDataSetChanged()
    }
}
