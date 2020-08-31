package com.fmohammadi.myshop.model

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.fmohammadi.myshop.R
import ss.com.bannerslider.ImageLoadingService

class glideImageLoadingService(var mContext:Context):ImageLoadingService {


    override fun loadImage(url: String?, imageView: ImageView?) {
        Glide.with(mContext)
            .load(url)
            .placeholder(R.drawable.placeholder_product)
            .error(R.drawable.placeholder_error)
            .into(imageView!!)
    }

    override fun loadImage(resource: Int, imageView: ImageView?) {
        Glide.with(mContext)
            .load(resource)
            .placeholder(R.drawable.placeholder_product)
            .error(R.drawable.placeholder_error)
            .into(imageView!!)
    }

    override fun loadImage(
        url: String?,
        placeHolder: Int,
        errorDrawable: Int,
        imageView: ImageView?
    ) {
        Glide.with(mContext)
            .load(url)
            .placeholder(R.drawable.placeholder_product)
            .error(R.drawable.placeholder_error)
            .into(imageView!!)
    }
}