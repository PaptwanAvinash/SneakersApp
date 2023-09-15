package app.shoes.sneakers.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import app.shoes.sneakers.ui.details.repo.ShoesDetailRepository
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.ui.home.repo.AddCartRepository
import app.shoes.sneakers.utils.BaseResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DetailViewModel  @AssistedInject constructor(
    private val shoesDetailRepo: ShoesDetailRepository,
    private val addCartRep: AddCartRepository,
    @Assisted private val productModel:ShoesDetailModel
    ) : ViewModel() {

    var mProductDetailModel : ShoesDetailModel?=null

    val addProductStatus=addCartRep.addProductLiveData

    val shoesDetail=shoesDetailRepo.getShoesDetail(model = productModel)
         .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(),
             BaseResponse.Loading())

    fun addProductToCart(){
        if (mProductDetailModel==null)return
        addCartRep.addProduct(viewModelScope,mProductDetailModel!!)
    }

    @AssistedFactory
    interface Factory{
        fun create(model: ShoesDetailModel):DetailViewModel
    }

    companion object{
        fun getDetailVMModel(factory:Factory,model: ShoesDetailModel):ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(model) as T
                }
            }
        }
    }



}