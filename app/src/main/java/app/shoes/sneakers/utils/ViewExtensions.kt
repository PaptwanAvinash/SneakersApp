package app.shoes.sneakers.utils

import android.os.SystemClock
import android.util.TypedValue
import android.view.View


fun View.setOnClickListenerDebounce(debounceTime: Long = 600L, action: (v: View) -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action(v)
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}


fun convertIntToDp(mSpace: Int, view: View) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, mSpace.toFloat(), view.resources.displayMetrics
).toInt()