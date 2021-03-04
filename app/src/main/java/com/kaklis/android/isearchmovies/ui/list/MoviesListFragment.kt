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

package com.kaklis.android.isearchmovies.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.*
import androidx.navigation.findNavController
import com.kaklis.android.isearchmovies.R
import com.kaklis.android.isearchmovies.data.model.Movie
import com.kaklis.android.isearchmovies.data.net.RetrofitClient
import kotlinx.android.synthetic.main.fragment_movies_list.*

/**
 * The Fragment to show Movies list
 */
class MoviesListFragment : Fragment(),
    MoviesListAdapter.OnItemClickListener,
    SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

  private lateinit var searchView: SearchView
  private lateinit var viewModel: MoviesListViewModel
 // lateinit var retrofitClient: RetrofitClient


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
    viewModel = ViewModelProviders.of(this).get(MoviesListViewModel::class.java)
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_movies_list, container, false)
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    super.onCreateOptionsMenu(menu, inflater)
    inflater?.inflate(R.menu.menu_movies_list, menu)

    // Initialize Search View
    searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
    searchView.setOnQueryTextListener(this)
    searchView.setOnCloseListener(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Start observing people list
    viewModel.getMovieList().observe(this, Observer<List<Movie>> { movies ->
      movies?.let {
        populateMoviesList(movies)
      }
    })
  }

  /**
   * Callback for searchView text change
   */
  override fun onQueryTextChange(newText: String?) = true

  /**
   * Callback for searchView query submit
   */
  override fun onQueryTextSubmit(query: String?): Boolean {
    //viewModel.searchMovie(query!!)
    getMovieList(query!!)
    return true
  }

  fun getMovieList(query: String) {
    viewModel.searchMovie(query).observe(this, Observer { movies ->
      movies?.let {
        moviesRecyclerView.adapter = MoviesListAdapter(movies, this@MoviesListFragment)
      }
    })
  }


  /**
   * Callback for searchView close
   */
  override fun onClose(): Boolean {
    viewModel.getAllMovies()
    searchView.onActionViewCollapsed()
    return true
  }

  /**
   * Populates peopleRecyclerView with all people info
   */
  private fun populateMoviesList(movieList: List<Movie>) {
    moviesRecyclerView.adapter = MoviesListAdapter(movieList, this)
  }

  /**
   * Navigates to movie details on item click
   */
  override fun onItemClick(movie: Movie, itemView: View) {
    val movieBundle = Bundle().apply {
      putInt(getString(R.string.movie_id), movie.id)
    }
    view?.findNavController()
        ?.navigate(R.id.action_moviesListFragment_to_movieDetailsFragment, movieBundle)
  }

}
