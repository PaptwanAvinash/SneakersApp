package app.shoes.sneakers.ui.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.shoes.sneakers.db.entities.EntityShoes
import app.shoes.sneakers.ui.cart.repo.UserCartRepository
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: UserCartRepository)
    :ViewModel() {

    val cartItems=cartRepository.getCartItem()

    fun addProduct(model: EntityShoes){
        viewModelScope.launch {
            cartRepository.addProduct(viewModelScope,model)
        }
    }

    fun deleteAllProduct(model: EntityShoes){
        viewModelScope.launch {
            cartRepository.deleteAllProduct(viewModelScope,model)
        }
    }

    fun clearDatabase(){
        viewModelScope.launch {
            cartRepository.clearDatabase(viewModelScope)
        }
    }

    fun reduceQuantity(model: EntityShoes){
        viewModelScope.launch {
            cartRepository.reduceQuantity(viewModelScope,model)
        }
    }

}