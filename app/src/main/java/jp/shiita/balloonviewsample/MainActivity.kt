package jp.shiita.balloonviewsample

import android.os.Bundle
import android.widget.SeekBar
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import jp.shiita.balloonviewsample.databinding.ActivityMainBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class MainActivity : AppCompatActivity() {

    private val binding by dataBinding<ActivityMainBinding>(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.imageView.x = progress * 10.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
}

class ActivityBindingDelegate<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : ReadOnlyProperty<AppCompatActivity, T> {
    private var binding: T? = null

    override operator fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T =
        binding ?: DataBindingUtil.inflate<T>(
            thisRef.layoutInflater,
            layoutResId,
            null,
            false
        ).also {
            thisRef.setContentView(it.root)
            it.lifecycleOwner = thisRef
            binding = it
        }
}

@Suppress("unused")
fun <T : ViewDataBinding> AppCompatActivity.dataBinding(@LayoutRes layoutResId: Int): ActivityBindingDelegate<T> =
    ActivityBindingDelegate(layoutResId)
