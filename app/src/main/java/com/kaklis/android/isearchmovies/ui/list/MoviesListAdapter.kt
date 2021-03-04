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

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kaklis.android.isearchmovies.R
import com.kaklis.android.isearchmovies.data.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_details.view.*

class MoviesListAdapter(
        private val items: List<Movie>,
        private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  /**
   * Notifies click on an item with attached view
   */
  interface OnItemClickListener {
    fun onItemClick(movie: Movie, itemView: View)
  }

  /**
   * Creates view for each item in the list
   */
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_movie_details, parent, false)
    return ViewHolder(view)
  }

  /**
   * Binds view with item info
   */
  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    (holder as ViewHolder).bind(items[position], clickListener)
  }

  /**
   * Returns the size to item list
   */
  override fun getItemCount(): Int {
    return items.size
  }

  /**
   * View for item, sets item info and click events
   */
  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: Movie, listener: OnItemClickListener) = with(itemView) {
      searchTitleTextView.text = movie.title
      searchReleaseDateTextView.text = movie.releaseDate
      searchOverviewTextView.text = movie.voteAverage.toString()
      Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movie.posterPath).into(searchImageView)

      // RecyclerView on item click
      setOnClickListener {
        listener.onItemClick(movie, it)
      }
    }

  }

}
