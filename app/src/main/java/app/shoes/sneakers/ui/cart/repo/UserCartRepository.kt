package app.shoes.sneakers.ui.cart.repo

import app.shoes.sneakers.db.dao.CartDAO
import app.shoes.sneakers.db.entities.EntityShoes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserCartRepository @Inject constructor(private val cartDAO: CartDAO){

    fun getCartItem()=
        cartDAO.getAllCartItems()

    fun addProduct(coroutineScope: CoroutineScope,model: EntityShoes){
     coroutineScope.launch(Dispatchers.IO) {
         cartDAO.increaseQuantity(model.productId)
     }
    }

    fun deleteAllProduct(coroutineScope: CoroutineScope,model: EntityShoes){
       coroutineScope.launch(Dispatchers.IO) {
           cartDAO.deleteItem(model)
       }
   }

    fun clearDatabase(coroutineScope: CoroutineScope){
        coroutineScope.launch(Dispatchers.IO) {
            cartDAO.deleteAllItems()
        }
    }

     fun reduceQuantity(coroutineScope: CoroutineScope,model: EntityShoes){
        coroutineScope.launch(Dispatchers.IO) {
           if (model.quantity==1)
               cartDAO.deleteItem(model)
           else
               cartDAO.decreaseQuantity(model.productId)
        }
    }
}