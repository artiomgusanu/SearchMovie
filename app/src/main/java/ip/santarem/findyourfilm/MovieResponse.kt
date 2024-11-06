package ip.santarem.findyourfilm

import com.google.gson.annotations.SerializedName

data class MovieResponse(val results: List<Movie>)
data class Movie(
    val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("poster_path") val posterPath: String?
)
