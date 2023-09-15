package app.shoes.sneakers.utils

import android.text.TextUtils
import android.webkit.URLUtil
import android.widget.ImageView
import com.bumptech.glide.Glide



fun setImageUrl(view: ImageView, imageUrl: String?) {
    runCatching {
        if (!TextUtils.isEmpty(imageUrl) && URLUtil.isValidUrl(imageUrl))
            Glide.with(view.context).load(imageUrl).into(view)
    }.onFailure {
        it.printStackTrace()
    }
}
