package app.shoes.sneakers

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.shoes.sneakers.db.dao.CartDAO
import app.shoes.sneakers.db.database.CartDatabase
import app.shoes.sneakers.db.entities.EntityShoes
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExampleUnitTest  {

    private lateinit var userDao: CartDAO
    private lateinit var db: CartDatabase

    @Before
    fun setup() {
        db = Room
            .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                    CartDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        userDao = db.cartDAO()
    }

    @Test
    fun isInsertWorking() = runBlocking {
        val entity = EntityShoes().apply {
            productId=1
            imgUrl=""
            retailPrice=0
            name=""
            brand=""
            quantity=0
            totalAmount=100
        }
        userDao.insert(entity)
        val retrievedUser = userDao.getProductDetail(entity.productId)
        assertEquals(compareObject(retrievedUser,entity),true)
    }

    @Test
    fun isEntityDeleted() = runBlocking {
       val entity= EntityShoes()
        entity.productId=1
        userDao.insert(entity)

        userDao.deleteItem(entity)
        val model=userDao.getProductDetail(entity.id)
        assertEquals(model,null)
    }

    @Test
    fun isQuantityIncrease() = runBlocking {
        val entity= EntityShoes()
        entity.productId=1
        entity.quantity=1
        userDao.insert(entity)
        /**calling these automatically decrease quantity using SQL query*/
        userDao.increaseQuantity(entity.productId)

        /**quantity should be 2*/
        val model=userDao.getProductDetail(entity.productId)
        assertEquals(model?.quantity,2)
    }

    @Test
    fun isQuantityDecrease() = runBlocking {
        val entity= EntityShoes()
        entity.productId=1
        entity.quantity=1
        userDao.insert(entity)
        /**calling these automatically decrease quantity using SQL query*/
        userDao.increaseQuantity(entity.productId)

        /**calling these automatically decrease quantity using SQL query*/
        userDao.decreaseQuantity(entity.productId)

        /**quantity should be 1*/
        val model=userDao.getProductDetail(entity.productId)
        assertEquals(model?.quantity,1)
    }

    @Test
    fun isDataBaseClear() = runBlocking {
        for(i in 1..3){
             EntityShoes().apply {
                productId=i
            }.let {
                 userDao.insert(it)
            }
        }

        userDao.deleteAllItems()
         val savedList=userDao.getAllCartItems().value?.size?:0
        assertEquals(savedList,0)
    }

    private fun compareObject(tableModel:EntityShoes?, model:EntityShoes)=
         tableModel?.productId== model.productId

    @After
    fun teardown() {
        db.close()
    }

}