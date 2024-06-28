package com.example.s_eda_app.singleton

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser


class ImageRequestSingleton(
    url: String,
    errorListener: Response.ErrorListener? = null,
    listener: Response.Listener<Bitmap>? = null
) : Request<Bitmap>(Method.GET, url, errorListener) {
    override fun parseNetworkResponse(response: NetworkResponse?): Response<Bitmap> {
        return Response.success(
            BitmapFactory.decodeByteArray(response?.data, 0, response?.data?.size ?: 0),
            HttpHeaderParser.parseCacheHeaders(response))
    }

    override fun deliverResponse(p0: Bitmap?) {

    }
    companion object {
        private var instance: ImageRequestSingleton? = null
        fun getInstance(
            url: String,
            errorListener: Response.ErrorListener? = null,
            listener: Response.Listener<Bitmap>? = null
        ): ImageRequestSingleton {
            if (instance == null) {
                instance = ImageRequestSingleton(url, errorListener, listener)
            }
            return instance!!
        }
    }
}