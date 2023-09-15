package app.shoes.sneakers.ui.search.repo

import app.shoes.sneakers.ui.home.models.Media
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.utils.BaseResponse
import app.shoes.sneakers.utils.MyConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import kotlin.random.Random

class SearchProductRepository @Inject constructor(){

    private val productNames= arrayListOf("Nike","Reebok","Boots","Adidas","Asic")
    private val productList:ArrayList<ShoesDetailModel> = arrayListOf()
    init {
        createShoesList()
    }

    val name= productNames[Random.nextInt(0,4)]

    private fun createShoesList(){
        productList.apply {
            for(i in 1..100){
                val name= productNames[Random.nextInt(0,4)]
                add(ShoesDetailModel(id = i, name = "$name $i", media = Media()))
            }
        }
    }

    fun fetchSearchProducts(text:String)= getShoesListAsync(text)


    private fun getShoesListAsync(text: String): ArrayList<ShoesDetailModel> {
        val filteredList= productList.filter { it.name.contains(text,true)}
        return filteredList as ArrayList<ShoesDetailModel>
    }
}