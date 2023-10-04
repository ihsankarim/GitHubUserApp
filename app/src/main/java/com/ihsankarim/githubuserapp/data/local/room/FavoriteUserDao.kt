package com.ihsankarim.githubuserapp.data.local.room
import androidx.lifecycle.LiveData
import androidx.room.*
import com.ihsankarim.githubuserapp.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertFavoriteUser(user: FavoriteUserEntity)

    @Delete
     fun deleteFavoriteUser(user: FavoriteUserEntity)

    @Query("SELECT * FROM favorite_user WHERE username = :username")
    fun getFavoriteUser(username: String): LiveData<FavoriteUserEntity>

    @Query("SELECT * FROM favorite_user")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity>>
}
