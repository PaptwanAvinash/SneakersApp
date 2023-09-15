package app.shoes.sneakers.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import app.shoes.sneakers.R
import app.shoes.sneakers.db.dao.CartDAO
import app.shoes.sneakers.db.database.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CoroutineExceptionHandlerModule {

    @Provides
    @Singleton
    fun provideCoroutineExceptionHandler(): CoroutineExceptionHandler {
       return CoroutineExceptionHandler{_,error->
            error.printStackTrace()
        }
    }

}