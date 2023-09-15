package app.shoes.sneakers.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.shoes.sneakers.R
import app.shoes.sneakers.base.BaseViewHolder
import app.shoes.sneakers.databinding.ItemSearchBinding
import app.shoes.sneakers.databinding.ItemShoesBinding
import app.shoes.sneakers.ui.home.models.ShoesDetailModel
import app.shoes.sneakers.utils.setImageUrl
import app.shoes.sneakers.utils.setOnClickListenerDebounce
import kotlin.Int

class ShoesListAdapter(private val mList: ArrayList<ShoesDetailModel>,
                       private val onProductClick:(model: ShoesDetailModel)->Unit,
                       private val mViewType: Int= VIEW_TYPE_HOME) :
    RecyclerView.Adapter<BaseViewHolder>() {
    companion object{
        const val VIEW_TYPE_HOME=1
        const val VIEW_TYPE_SEARCH=2
    }

    lateinit var mContext:Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        mContext=parent.context
        return if (mViewType== VIEW_TYPE_HOME)
            ShoeItemViewHolder(ItemShoesBinding
            .inflate(LayoutInflater.from(mContext),parent,false))
        else
            SearchItemViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(mContext),parent,false))

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount()= mList.size

    fun updateList(list:List<ShoesDetailModel>){
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    private inner class ShoeItemViewHolder(private val mBinding: ItemShoesBinding)
        : BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            mList.elementAtOrNull(position)?.let {model->
                    mBinding.apply {
                        tvName.text=model.name
                        tvPrice.text=mContext.getString(R.string.rupees)+" "+model.retailPrice.toString()
                        container.setOnClickListenerDebounce {
                            mList.elementAtOrNull(adapterPosition)?.let {
                                onProductClick.invoke(it)
                            }
                        }
                    }
            }
        }
    }

    private inner class SearchItemViewHolder(private val mBinding: ItemSearchBinding)
        : BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            mList.elementAtOrNull(position)?.let {model->
                mBinding.apply {
                    tvName.text=model.name
                    TvBrand.text=model.brand
                    tvPrice.text=mContext.getString(R.string.rupees)+" "+model.retailPrice.toString()
                    container.setOnClickListenerDebounce {
                        mList.elementAtOrNull(adapterPosition)?.let {
                            onProductClick.invoke(it)
                        }
                    }
                }
            }
        }
    }

}