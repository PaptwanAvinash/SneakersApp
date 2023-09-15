package app.shoes.sneakers.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class EntityShoes {
    @PrimaryKey(autoGenerate = true)
    var id: Int=0
    var productId:Int=1
    var imgUrl: String=""
    var retailPrice: Int=0
    var name: String=""
    var brand: String=""
    var quantity=0
    var totalAmount=retailPrice
}

