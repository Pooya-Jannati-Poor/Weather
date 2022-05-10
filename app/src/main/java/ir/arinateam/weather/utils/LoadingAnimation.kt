package ir.arinateam.weather.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import ir.arinateam.weather.R

class LoadingAnimation(context: Context) {

    var dialogLoading: Dialog = Dialog(context)

    init {
        initLoadingDialog()
    }

    private fun initLoadingDialog() {

        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLoading.setCancelable(false)
        dialogLoading.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogLoading.setContentView(R.layout.loading_loading_animation)
        dialogLoading.show()

    }

    fun hideDialog() {

        dialogLoading.dismiss()

    }

}