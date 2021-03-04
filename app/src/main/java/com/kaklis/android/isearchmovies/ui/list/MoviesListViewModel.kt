package com.kaklis.android.isearchmovies.ui.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.kaklis.android.isearchmovies.ISearchMoviesApp
import com.kaklis.android.isearchmovies.data.model.Movie

class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

  private val movieRepository = getApplication<ISearchMoviesApp>().getMovieRepository()
  private val moviesList = MediatorLiveData<List<Movie>>()

  init {
    getAllMovies()
  }

  // 1
  fun getMovieList(): LiveData<List<Movie>> {
    return moviesList
  }

  // 2
  fun getAllMovies() {
    moviesList.addSource(movieRepository.getAllMovies()) { movies ->
      moviesList.postValue(movies)
    }
  }

  fun searchPeople(name: String) {
    moviesList.addSource(movieRepository.findMovie(name)) { movies ->
      moviesList.postValue(movies)
    }
  }

  fun searchMovie(query: String): LiveData<List<Movie>> {
    return movieRepository.searchMovies(query)
  }

}