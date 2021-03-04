/*
 *
 *  * Copyright (c) 2018 Razeware LLC
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 *  * distribute, sublicense, create a derivative work, and/or sell copies of the
 *  * Software in any work that is designed, intended, or marketed for pedagogical or
 *  * instructional purposes related to programming, coding, application development,
 *  * or information technology.  Permission for such use, copying, modification,
 *  * merger, publication, distribution, sublicensing, creation of derivative works,
 *  * or sale is expressly withheld.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 *
 */

package com.kaklis.android.isearchmovies.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.kaklis.android.isearchmovies.R
import com.kaklis.android.isearchmovies.data.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.item_movie_details.view.*

/**
 * The Fragment to show movie details
 */
class MovieDetailsFragment : Fragment() {

  private lateinit var viewModel: MovieDetailsViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {

    return inflater.inflate(R.layout.fragment_movie_details, container, false)





  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Find movie with provided id
    val movieId = arguments?.getInt(getString(R.string.movie_id))
    //val movieTitle = arguments?.getString("title")

    movieId?.let {
      viewModel.getMovieDetails(movieId).observe(this, Observer { movieDetails ->
        populateMovieDetails(movieDetails)
      })


    }

      val imageViewIcon = view.findViewById(R.id.imageButton) as ImageButton
      imageViewIcon.setOnClickListener {

          addMovie(viewModel.getMovieDetails(movieId!!).value)
          Log.d("imageButton", "Selected")
      }

  }

  /**
   * Binds movie info into views
   */
  private fun populateMovieDetails(movie: Movie?) {
    Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movie?.posterPath).into(imageViewIcon)

    textViewName.text = movie?.title
    textViewMet.text = movie?.overview
    val list: MutableList<List<Int>?> = mutableListOf(movie?.genreIds)

    buttonContact.text = list[0].toString()
//    textViewEmail.text = movie?.email
//    textViewFacebook.text = movie?.facebook
//    textViewTwitter.text = movie?.twitter
  }

    fun addMovie(movie: Movie?) {

        val movie = Movie(title = movie?.title, releaseDate = movie?.releaseDate, posterPath = movie?.posterPath)
        viewModel.saveMovie(movie)

    }

}
