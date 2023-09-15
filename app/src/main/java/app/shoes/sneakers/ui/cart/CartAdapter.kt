package app.shoes.sneakers.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.shoes.sneakers.R
import app.shoes.sneakers.base.BaseViewHolder
import app.shoes.sneakers.databinding.ItemCartBinding
import app.shoes.sneakers.db.entities.EntityShoes
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.utils.setImageUrl
import app.shoes.sneakers.utils.setOnClickListenerDebounce

class CartAdapter (private var mList: List<EntityShoes>,
                   private val cartListener: CartListener) :
    RecyclerView.Adapter<BaseViewHolder>() {
    lateinit var mContext:Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        mContext=parent.context
        val binding= ItemCartBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return CartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount()= mList.size

    fun updateList(list:List<EntityShoes>){
        mList=list
        notifyDataSetChanged()
    }

    private inner class CartItemViewHolder(private val mBinding: ItemCartBinding)
        : BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            mList.elementAtOrNull(position)?.let {model->
                mBinding.apply {
                    tvName.text=model.name
                    TvBrand.text=model.brand
                    tvPrice.text=mContext.getString(R.string.rupees)+" "+model.retailPrice.toString()
                    tvCount.text=model.quantity.toString()
                    ivAddProduct.setOnClickListenerDebounce {
                        mList.elementAtOrNull(adapterPosition)?.let {
                            cartListener.addProduct(it)
                        }
                    }
                    ivDelete.setOnClickListenerDebounce {
                        mList.elementAtOrNull(adapterPosition)?.let {
                            cartListener.removeAllProduct(it)
                        }
                    }
                    ivreduceProductQu.setOnClickListenerDebounce {
                        mList.elementAtOrNull(adapterPosition)?.let {
                            cartListener.reduceQuantity(it)
                        }
                    }
                }
            }
        }
    }
}

interface CartListener{
    fun addProduct(model:EntityShoes)
    fun removeAllProduct(model: EntityShoes)
    fun reduceQuantity(model:EntityShoes)
}