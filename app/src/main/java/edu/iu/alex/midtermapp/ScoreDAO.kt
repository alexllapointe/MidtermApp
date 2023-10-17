package edu.iu.alex.midtermapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ScoreDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Score)
    @Update
    suspend fun update(task: Score)
    @Delete
    suspend fun delete(task: Score)
    @Query("SELECT * FROM score_table WHERE scoreID = :key")
    fun get(key: Long): LiveData<Score>
    @Query("SELECT * FROM score_table ORDER BY scoreID DESC")
    fun getAllScores(): LiveData<List<Score>>
    @Query("SELECT * FROM score_table ORDER BY scoreID DESC LIMIT 1")
    suspend fun getLatestScore(): Score?
    @Query("SELECT COUNT(*) FROM score_table")
    fun countScores(): Int

}