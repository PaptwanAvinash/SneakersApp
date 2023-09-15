package app.shoes.sneakers.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.shoes.sneakers.db.dao.CartDAO
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.ui.home.repo.AddCartRepository
import app.shoes.sneakers.ui.home.repo.ShoesListRepository
import app.shoes.sneakers.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    shoesRepo: ShoesListRepository
)
    :ViewModel() {
    val shoesList=shoesRepo.getShoesList()
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(),
        BaseResponse.Loading())



}