package jp.shiita.balloonviewsample

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout

class BalloonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val arrow: View
    private var listener: ViewTreeObserver.OnGlobalLayoutListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_balloon, this)
        arrow = findViewById(R.id.arrow)
    }

    fun setTargetView(view: View) {
        listener = ViewTreeObserver.OnGlobalLayoutListener {
            val newArrowX = view.x - this.x + view.width / 2 - arrow.width / 2
            arrow.x = maxOf(0f, minOf(this.x + this.width - arrow.width, newArrowX))
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    fun removeTargetView(view: View) {
        listener?.let { view.viewTreeObserver.removeOnGlobalLayoutListener(it) }
        listener = null
    }
}