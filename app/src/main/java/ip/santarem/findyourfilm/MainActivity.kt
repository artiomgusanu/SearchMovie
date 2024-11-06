package ip.santarem.findyourfilm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var resultsLayout: LinearLayout
    private val apiKey = "29badec9cc6cc14199ebf111b7ff8c86"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)
        resultsLayout = findViewById(R.id.resultsLayout)

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            if (query.isNotEmpty()) {
                searchMovies(query)
            }
        }
    }

    private fun searchMovies(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.searchMovies(apiKey, query)
            withContext(Dispatchers.Main) {
                displayResults(response.results)
            }
        }
    }

    private fun displayResults(movies: List<Movie>) {
        resultsLayout.removeAllViews()
        for (movie in movies) {
            val view = layoutInflater.inflate(R.layout.movie_item, null)

            val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
            val yearTextView = view.findViewById<TextView>(R.id.yearTextView)
            val posterImageView = view.findViewById<ImageView>(R.id.posterImageView)

            titleTextView.text = movie.title
            yearTextView.text = movie.releaseDate.take(4) // Extrai apenas o ano (4 primeiros caracteres)

            // Carregar a imagem do poster
            Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .into(posterImageView)

            resultsLayout.addView(view)
        }
    }

}
