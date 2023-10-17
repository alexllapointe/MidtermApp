package edu.iu.alex.midtermapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "score_table")
data class Score(
    @PrimaryKey(autoGenerate = true)
    var scoreID: Long = 0L,
    val name: String,
    val attempts: Int
)