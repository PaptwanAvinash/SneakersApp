package app.shoes.sneakers.ui.cart.repo

import app.shoes.sneakers.db.dao.CartDAO
import app.shoes.sneakers.db.entities.EntityShoes
import app.shoes.sneakers.utils.errorHandlerScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserCartRepository @Inject constructor(private val cartDAO: CartDAO,
                                             private val errorHandler: CoroutineExceptionHandler){

    fun getCartItem()=
        cartDAO.getAllCartItems()

    fun addProduct(coroutineScope: CoroutineScope,model: EntityShoes){
     errorHandlerScope(coroutineScope,errorHandler)
         .launch(Dispatchers.IO) {
         cartDAO.increaseQuantity(model.productId)
     }
    }

    fun deleteAllProduct(coroutineScope: CoroutineScope,model: EntityShoes){
        errorHandlerScope(coroutineScope,errorHandler)
            .launch(Dispatchers.IO) {
           cartDAO.deleteItem(model)
       }
   }

    fun clearDatabase(coroutineScope: CoroutineScope){
        errorHandlerScope(coroutineScope,errorHandler)
            .launch(Dispatchers.IO) {
            cartDAO.deleteAllItems()
        }
    }

     fun reduceQuantity(coroutineScope: CoroutineScope,model: EntityShoes){
         errorHandlerScope(coroutineScope,errorHandler)
             .launch(Dispatchers.IO) {
           if (model.quantity==1)
               cartDAO.deleteItem(model)
           else
               cartDAO.decreaseQuantity(model.productId)
        }
    }
}