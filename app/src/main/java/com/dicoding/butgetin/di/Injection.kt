package com.dicoding.butgetin.di

import android.content.Context
import com.dicoding.butgetin.data.repository.UserRepository

object Injection {

    fun provideRepository(context: Context): UserRepository {
        return UserRepository(context)
    }
}