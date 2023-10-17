package edu.iu.alex.midtermapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    val latestScore: MutableLiveData<Score?> = MutableLiveData()
    val attempts = MutableLiveData<Int>()

    init {
        attempts.value = 0
    }

    private val database = ScoreDatabase.getInstance(application)
    private val scoreDao = database.scoreDao

//    val allScores: LiveData<List<Score>> = scoreDao.getAllScores()


    fun savePlayerScore(name: String, attempts: Int) {
        viewModelScope.launch {
            val playerScore = Score(0, name, attempts)
            scoreDao.insert(playerScore)
        }
    }

    fun getLatestScore() {
        viewModelScope.launch {
            val latest = scoreDao.getLatestScore()
            latestScore.postValue(latest)
        }
    }

    fun getAllScores(context: Context): LiveData<List<Score>> {
        val scoreDao = ScoreDatabase.getInstance(context).scoreDao
        return scoreDao.getAllScores()
    }

    fun deleteScore(score: Score) {
        viewModelScope.launch {
            scoreDao.delete(score)
        }
    }

    fun updateScore(score: Score) {
        viewModelScope.launch {
            scoreDao.update(score)
        }
    }

    fun getScoreById(id: Long): LiveData<Score> {
        return scoreDao.get(id)
    }

    fun countScores(): Int {
        return scoreDao.countScores()
    }
}