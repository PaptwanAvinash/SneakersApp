package app.shoes.sneakers.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(view: View):RecyclerView.ViewHolder(view) {
    abstract fun onBind(position:Int)
}