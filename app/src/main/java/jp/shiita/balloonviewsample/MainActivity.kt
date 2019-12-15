package jp.shiita.balloonviewsample

import android.os.Bundle
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

        binding.balloon1.setTargetView(binding.imageView)
        binding.balloon2.setTargetView(binding.imageView)
        binding.balloon3.setTargetView(binding.imageView)
        binding.balloon4.setTargetView(binding.imageView)
    }

    override fun onDestroy() {
        binding.balloon1.removeTargetView(binding.imageView)
        binding.balloon2.removeTargetView(binding.imageView)
        binding.balloon3.removeTargetView(binding.imageView)
        binding.balloon4.removeTargetView(binding.imageView)
        super.onDestroy()
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
