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

package com.kaklis.android.isearchmovies.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.kaklis.android.isearchmovies.data.db.MoviesDao
import com.kaklis.android.isearchmovies.data.db.MoviesDatabase
import com.kaklis.android.isearchmovies.data.model.Movie
import com.kaklis.android.isearchmovies.data.model.MoviesResponse
import com.kaklis.android.isearchmovies.data.model.Trailer
import com.kaklis.android.isearchmovies.data.model.TrailerResponse
import com.kaklis.android.isearchmovies.data.net.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository(application: Application) {

  private val moviesDao: MoviesDao
  private val retrofitClient = RetrofitClient()


  init {
    val moviesDatabase = MoviesDatabase.getInstance(application)
    moviesDao = moviesDatabase.movieDao()
  }

  fun getAllMovies(): LiveData<List<Movie>> {
    return moviesDao.getAll()
  }

  fun insertMovie(movie: Movie) {
    moviesDao.insert(movie)
  }

  fun findMovie(id: Int): LiveData<Movie> {
    return moviesDao.find(id)
  }


  fun findMovie(name: String): LiveData<List<Movie>> {
    return moviesDao.findBy(name)
  }

  fun searchMovies(query: String): LiveData<List<Movie>> {
    val data = MutableLiveData<List<Movie>>()
    retrofitClient.searchMovies(query).enqueue(object : Callback<MoviesResponse> {

      override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
        Log.d(javaClass.simpleName, "Remember to add your KEY in your RetrofitClient.kt file")
      }

      override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
        if (response.isSuccessful) {
          val movies = response.body()?.results
          data.value = movies
        }
      }
    })
    return data
  }

  fun searchMovieDetails(query: Int): LiveData<Movie> {
    val data = MutableLiveData<Movie>()
    retrofitClient.searchMovieDetails(query).enqueue(object : Callback<Movie> {

      override fun onFailure(call: Call<Movie>, t: Throwable) {
        Log.d(javaClass.simpleName, "Remember to add your KEY in your RetrofitClient.kt file")
      }

      override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
        if (response.isSuccessful) {
          val movies = response  //.body()?.results
          data.value = movies.body()
        }
      }
    })
    return data
  }


  fun getMovieTrailersApiCall(movieId: Int): LiveData<List<Trailer>> {
    val data = MutableLiveData<List<Trailer>>()
    retrofitClient.getMovieTrailer(movieId).enqueue(object : Callback<TrailerResponse> {

      override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
        Log.d(javaClass.simpleName, "Remember to add your KEY in your RetrofitClient.kt file")
      }

      override fun onResponse(call: Call<TrailerResponse>, response: Response<TrailerResponse>) {
        if (response.isSuccessful) {
          val trailers = response.body()?.results
          data.value = trailers
        }
      }
    })
    return data
    }


}