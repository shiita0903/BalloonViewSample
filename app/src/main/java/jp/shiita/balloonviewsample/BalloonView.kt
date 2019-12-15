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

    private val arrowUp: View
    private val arrowDown: View
    private var listener: ViewTreeObserver.OnGlobalLayoutListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_balloon, this)
        arrowUp = findViewById(R.id.arrowUp)
        arrowDown = findViewById(R.id.arrowDown)
    }

    fun setTargetView(view: View) {
        listener = ViewTreeObserver.OnGlobalLayoutListener {

            if (view.y < this.y) showArrowUp(view)
            else showArrowDown(view)
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    fun removeTargetView(view: View) {
        listener?.let { view.viewTreeObserver.removeOnGlobalLayoutListener(it) }
        listener = null
    }

    private fun showArrowUp(view: View) {
        arrowUp.x = calcClippedX(view, arrowUp)
        arrowUp.visibility = View.VISIBLE
        arrowDown.visibility = View.GONE
    }

    private fun showArrowDown(view: View) {
        arrowDown.x = calcClippedX(view, arrowDown)
        arrowDown.visibility = View.VISIBLE
        arrowUp.visibility = View.GONE
    }

    private fun calcClippedX(targetView: View, arrowView: View): Float {
        val newArrowX = targetView.x - this.x + targetView.width / 2 - arrowView.width / 2
        return maxOf(0f, minOf(this.x + this.width - arrowView.width, newArrowX))
    }
}