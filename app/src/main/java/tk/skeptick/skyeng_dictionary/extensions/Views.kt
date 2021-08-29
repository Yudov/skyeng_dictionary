package tk.skeptick.skyeng_dictionary.extensions

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.ShapeAppearanceModel
import tk.skeptick.skyeng_dictionary.R

@Suppress("FunctionName")
fun BottomRoundedMaterialShape(context: Context): ShapeAppearanceModel {
    return ShapeAppearanceModel.builder(context, R.style.ShapeAppearanceOverlay_CardBottomCorners, 0).build()
}

fun RecyclerView.addOnScrollToBottomListener(
    layoutManager: LinearLayoutManager,
    visibleThreshold: Int,
    block: () -> Unit
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (layoutManager.itemCount <= (layoutManager.findLastVisibleItemPosition() + visibleThreshold)) block()
        }
    })
}