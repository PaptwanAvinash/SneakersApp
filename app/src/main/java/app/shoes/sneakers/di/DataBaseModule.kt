package app.shoes.sneakers.di

import android.content.Context
import androidx.room.Room
import app.shoes.sneakers.R
import app.shoes.sneakers.db.dao.CartDAO
import app.shoes.sneakers.db.database.CartDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideUserDao(db: CartDatabase): CartDAO = db.cartDAO()

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): CartDatabase {
        return Room.databaseBuilder(
            context, CartDatabase::class.java, context.getString(R.string.app_name)+"UserCartDB"
        ).build()
    }

}