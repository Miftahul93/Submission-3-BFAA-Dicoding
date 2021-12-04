package com.example.mysubmissiongithubuser.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mysubmissiongithubuser.db.DatabaseUser
import com.example.mysubmissiongithubuser.db.FavoriteUser
import com.example.mysubmissiongithubuser.db.FavoriteUserDao

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var dbUser: DatabaseUser?
    private var userDao: FavoriteUserDao?

    init {
        dbUser = DatabaseUser.getDatabase(application)
        userDao = dbUser?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}