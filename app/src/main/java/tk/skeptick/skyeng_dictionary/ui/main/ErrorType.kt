package tk.skeptick.skyeng_dictionary.ui.main

import androidx.annotation.DrawableRes
import tk.skeptick.skyeng_dictionary.R

enum class ErrorType(@DrawableRes val imageRes: Int) {
    Empty(R.drawable.ic_write),
    NotFound(R.drawable.ic_404),
    Error(R.drawable.ic_error)
}