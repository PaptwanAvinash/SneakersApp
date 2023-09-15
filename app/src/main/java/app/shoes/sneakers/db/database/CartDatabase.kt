package app.shoes.sneakers.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.shoes.sneakers.R
import app.shoes.sneakers.db.dao.CartDAO
import app.shoes.sneakers.db.entities.EntityShoes

@Database(entities = [EntityShoes::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDAO(): CartDAO
}