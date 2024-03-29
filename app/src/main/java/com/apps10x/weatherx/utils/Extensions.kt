package com.apps10x.weatherx.utils

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import retrofit2.Response
import java.net.UnknownHostException


fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun TextView.setTextAndShow(value: String?) {
    if (value.isNullOrBlank()) hide()
    else {
        show()
        text = value
    }
}

fun View?.showIndefiniteSnackBarWithAction(message: String, action: String, onAction: () -> Unit) {
    this?.let {
        Snackbar.make(it, message, Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .setAction(action) {
                onAction.invoke()
            }
            .setTextColor(Color.BLACK)
            .setActionTextColor(Color.RED)
            .show()
    }
}

suspend inline fun <reified T : Any> doSafeApiRequest(
    crossinline call: suspend () -> Response<*>,
): ApiResult<T> {
    return try {
        call.invoke().let { response ->
            if (response.isSuccessful) {
                ApiResult.Success(response.body() as? T)
            } else {
                ApiResult.Error(error = response.message())
            }
        }
    } catch (e: Exception) {
        return if (e is UnknownHostException)
            ApiResult.Error(error = "There is no internet connection. Connect to the internet and try again.")
        else {
            ApiResult.Error(error = null)
        }
    }
}