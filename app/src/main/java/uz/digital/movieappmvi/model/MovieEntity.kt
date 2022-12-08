package uz.digital.movieappmvi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val lan: String,
    val backImage: String,
    val posterImage: String,
    val title: String,
    val overView: String,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int
)