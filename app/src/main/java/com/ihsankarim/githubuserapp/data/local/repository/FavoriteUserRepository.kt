package com.ihsankarim.githubuserapp.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ihsankarim.githubuserapp.data.local.entity.FavoriteUserEntity
import com.ihsankarim.githubuserapp.data.local.room.FavoriteUserDao
import com.ihsankarim.githubuserapp.data.local.room.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity>> {
        return mFavoriteUserDao.getAllFavoriteUsers()
    }

    fun getFavoriteUser(username: String): LiveData<FavoriteUserEntity> {
        return mFavoriteUserDao.getFavoriteUser(username)
    }

    fun insertFavoriteUser(user: FavoriteUserEntity) {
        executorService.execute {
            mFavoriteUserDao.insertFavoriteUser(user)
        }
    }

    fun deleteFavoriteUser(user: FavoriteUserEntity) {
        executorService.execute {
            mFavoriteUserDao.deleteFavoriteUser(user)
        }
    }
}