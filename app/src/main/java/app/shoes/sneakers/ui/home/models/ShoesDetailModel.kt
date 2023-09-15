package app.shoes.sneakers.ui.home.models

import android.os.Parcelable
import androidx.annotation.Keep
import app.shoes.sneakers.db.entities.EntityShoes
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class ShoesDetailModel(
    val brand: String="Nike",
    val colorway: String="Blue",
    val gender: String="Male",
    val id: Int=0,
    val media: Media,
    val name: String="Boots",
    val releaseDate: String="",
    val stringPrice: String="₹ 570",
    val retailPrice: Int=100,
    val shoe: String="",
    val styleId: String="",
    val title: String="Lather Sneakers",
    val year: Int=2023,
    var quantity:Int=1
):Parcelable{
    fun getCartItemModel(model:EntityShoes)= model.let {
            it.name=name
            it.imgUrl=media.imageUrl
            it.retailPrice+=retailPrice
            it.quantity+=1
        }
}


@Parcelize
@Keep
data class Media(
    val imageUrl: String="https://assets.ajio.com/medias/sys_master/root/20220624/gNc5/62b5df8bf997dd03e29e63b1/-473Wx593H-469098206-blue-MODEL.jpg",
    val smallImageUrl: String="https://crepdogcrew.com/cdn/shop/products/AirJordan1LowAluminum_1400x.jpg?v=1671805734",
    val thumbUrl: String="https://crepdogcrew.com/cdn/shop/products/AirJordan1LowAluminum_1400x.jpg?v=1671805734"
):Parcelable