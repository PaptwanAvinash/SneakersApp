package app.shoes.sneakers.ui.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.ui.home.repo.ShoesListRepository
import app.shoes.sneakers.ui.search.repo.SearchProductRepository
import app.shoes.sneakers.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val shoesRepo: SearchProductRepository
) : ViewModel() {

    val shoesList=MutableLiveData<ArrayList<ShoesDetailModel>>()

    fun requestSearchItems(text:String)=shoesRepo.fetchSearchProducts(text)

}