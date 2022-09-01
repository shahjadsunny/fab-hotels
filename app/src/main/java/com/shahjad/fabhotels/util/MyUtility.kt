package com.shahjad.fabhotels.util

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.shahjad.fabhotels.R

object MyUtility {

//        fun showSnackbarMessage(viewShow: View, message: String?) {
//            val mSnackBar = Snackbar.make(viewShow, message.toString(), Snackbar.LENGTH_LONG)
////                .show()
//
//            val tv = mSnackBar.view.findViewById(R.id.snackbar_text) as TextView
//            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
//
//            mSnackBar.show()
//        }
    fun imageSet(imageUrl: String, view: ImageView, drawable:Int) {

        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(drawable)
            .into(view)
    }

}