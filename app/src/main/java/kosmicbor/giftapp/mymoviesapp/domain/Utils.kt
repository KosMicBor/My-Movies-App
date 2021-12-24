package kosmicbor.giftapp.mymoviesapp.domain


import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.viewShow() {
    visibility = View.VISIBLE
}

fun View.viewHide() {
    visibility = View.GONE
}

fun View.showSnackBar(
    view: View,
    @StringRes stringRes: Int,
    length: Int = Snackbar.LENGTH_INDEFINITE,
    @StringRes actionText: Int,
    action: (View) -> Unit
) {
    Snackbar.make(
        view, stringRes,
        length
    ).setAction(actionText, action).show()
}