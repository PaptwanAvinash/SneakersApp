package app.shoes.sneakers.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.shoes.sneakers.db.entities.EntityShoes

@Dao
interface CartDAO {
    @Insert
    suspend fun insert(model: EntityShoes)

    @Update
    suspend fun update(model: EntityShoes)

    @Query("SELECT * FROM EntityShoes")
    fun  getAllCartItems() : LiveData<List<EntityShoes>>

    @Query("SELECT * FROM EntityShoes WHERE productId=:id")
    fun  getProductDetail(id: Int) : EntityShoes?

    @Delete
    fun deleteItem(model: EntityShoes?)

    @Query("UPDATE EntityShoes SET quantity = quantity + 1,totalAmount = retailPrice*(quantity+1) WHERE productId=:id")
    fun increaseQuantity(id: Int)

    @Query("UPDATE EntityShoes SET quantity = quantity - 1,totalAmount = retailPrice*(quantity-1) WHERE productId=:id")
    fun decreaseQuantity(id: Int)

    @Query("DELETE FROM EntityShoes")
    fun deleteAllItems()
}