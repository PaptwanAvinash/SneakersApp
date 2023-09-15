package app.shoes.sneakers.ui.home.repo

import app.shoes.sneakers.ui.home.models.Media
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.utils.BaseResponse
import app.shoes.sneakers.utils.MyConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random


class ShoesListRepository @Inject constructor() {

    private val productNames= arrayListOf("Nike","Reebok","Boots","Adidas","Asic")

    fun getShoesList()= flow{
        when (val response=getShoesListAsync()){
           is BaseResponse.Success->{ emit(BaseResponse.Success(response.data)) }
            is BaseResponse.Failed ->
                emit(BaseResponse.Failed(MyConstants.SOMETHING_WENT_WRONG))

            else->{

            }
        }
    }

    private suspend fun getShoesListAsync():BaseResponse<ArrayList<ShoesDetailModel>>{
        delay(2000)

        ArrayList<ShoesDetailModel>().apply {
            for(i in 1..100){
                val name= productNames[Random.nextInt(0,4)]
                add(ShoesDetailModel(id = i, name = "$name $i", media = Media()))
            }
        }.let {
           return BaseResponse.Success(it)
         }
    }


}