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
 
package com.kaklis.android.isearchmovies.data.db

import android.app.Application
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.kaklis.android.isearchmovies.data.model.Movie
import com.kaklis.android.isearchmovies.utils.GenreIdConverter

// 1
@Database(entities = [Movie::class], version = 1)
@TypeConverters(GenreIdConverter::class)
abstract class MoviesDatabase : RoomDatabase() {

  abstract fun movieDao(): MoviesDao

  companion object {
    private val lock = Any()
    private const val DB_NAME = "MovieDatabase"
    private var INSTANCE: MoviesDatabase? = null

    fun getInstance(application: Application): MoviesDatabase {
      synchronized(MoviesDatabase.lock) {
        if (MoviesDatabase.INSTANCE == null) {
          MoviesDatabase.INSTANCE =
                  Room.databaseBuilder(application, MoviesDatabase::class.java, MoviesDatabase.DB_NAME)
                          .allowMainThreadQueries()
                          .build()
        }
      }
      return INSTANCE!!
    }
  }
//  abstract fun moviesDao(): MoviesDao
//
//  // 2
//  companion object {
//    private val lock = Any()
//    private const val DB_NAME = "Movie.db"
//    private var INSTANCE: MoviesDatabase? = null
//
//    // 3
//    fun getInstance(application: Application): MoviesDatabase {
//      synchronized(MoviesDatabase.lock) {
//        if (MoviesDatabase.INSTANCE == null) {
//          MoviesDatabase.INSTANCE =
//              Room.databaseBuilder(application, MoviesDatabase::class.java, MoviesDatabase.DB_NAME)
//                  .allowMainThreadQueries()
//                  .addCallback(object : RoomDatabase.Callback() {
//                    override fun onCreate(db: SupportSQLiteDatabase) {
//                      super.onCreate(db)
//                      MoviesDatabase.INSTANCE?.let {
//                        MoviesDatabase.prePopulate(it, MoviesInfoProvider.movielist)
//                      }
//                    }
//                  })
//                  .build()
//        }
//        return MoviesDatabase.INSTANCE!!
//      }
//    }
//
//    fun prePopulate(database: MoviesDatabase, movieList: List<Movie>) {
//      for (movie in movieList) {
//        AsyncTask.execute { database.moviesDao().insert(movie) }
//      }
//    }
//
//
//  }

}
