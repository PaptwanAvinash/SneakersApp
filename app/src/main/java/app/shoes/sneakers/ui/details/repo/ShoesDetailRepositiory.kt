package app.shoes.sneakers.ui.details.repo

import android.content.Intent
import androidx.lifecycle.LiveData
import app.shoes.sneakers.db.dao.CartDAO
import app.shoes.sneakers.db.entities.EntityShoes
import app.shoes.sneakers.ui.home.models.Media
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.utils.BaseResponse
import app.shoes.sneakers.utils.MyConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShoesDetailRepository @Inject constructor(private val cartDAO: CartDAO) {


    fun getShoesDetail(model: ShoesDetailModel)= flow {
        when(val response=getShoesDetailAsync(model)){
            is BaseResponse.Success->{emit(BaseResponse.Success(response.data))}
            is BaseResponse.Failed->
                emit(BaseResponse.Failed(MyConstants.SOMETHING_WENT_WRONG))
            else->{}
        }
    }

    private suspend fun getShoesDetailAsync(model: ShoesDetailModel):BaseResponse<ShoesDetailModel>{
        delay(600)

       /* //fetching local data and increase quantity if have
        val entity=cartDAO.getProductDetail(model.id)

        if (entity!=null){
            model.quantity=entity.quantity
        }*/
        return BaseResponse.Success(model)
    }


}