package app.shoes.sneakers.ui.home.repo

import androidx.lifecycle.MutableLiveData
import app.shoes.sneakers.db.dao.CartDAO
import app.shoes.sneakers.db.entities.EntityShoes
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.utils.errorHandlerScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class AddCartRepository @Inject constructor(private val cartDAO: CartDAO,
                                            private val errorHandler: CoroutineExceptionHandler) {

    val addProductLiveData = MutableLiveData<Boolean>()

    fun addProduct(coroutineScope: CoroutineScope, model: ShoesDetailModel){
        errorHandlerScope(coroutineScope,errorHandler)
            .launch(Dispatchers.IO) {
            addProductLiveData.postValue(false)
            delay(1000)
            cartDAO.getProductDetail(model.id).let {
                if(it==null)
                EntityShoes().let {entity->
                    entity.productId=model.id
                    entity.name=model.name
                    entity.imgUrl=model.media.imageUrl
                    entity.retailPrice=model.retailPrice
                    entity.quantity=1
                    entity.brand=model.brand
                    entity.totalAmount=model.retailPrice
                    cartDAO.insert(entity)
                    addProductLiveData.postValue(true)
                }
                else{
                    it.quantity=it.quantity+1
                    it.totalAmount=it.retailPrice*it.quantity
                    cartDAO.update(it)
                    addProductLiveData.postValue(true)
                }
            }
        }
    }

}