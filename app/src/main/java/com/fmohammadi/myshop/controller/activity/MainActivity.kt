package com.fmohammadi.myshop.controller.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fmohammadi.myshop.R
import com.fmohammadi.myshop.model.SliderItem
import com.fmohammadi.myshop.model.glideImageLoadingService
import kotlinx.android.synthetic.main.activity_main.*
import ss.com.bannerslider.Slider

class MainActivity : AppCompatActivity() {
    var imageLoadingService: glideImageLoadingService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageLoadingService = glideImageLoadingService(this)

        Slider.init(imageLoadingService)

        banner_slider1.setAdapter(SliderItem())

    }
}