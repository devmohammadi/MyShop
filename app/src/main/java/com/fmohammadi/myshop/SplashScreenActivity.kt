package com.fmohammadi.myshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import com.fmohammadi.myshop.R.*
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    private var fadeIn: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_splash_screen)

        logoShopping.speed = 2f


        fadeIn = AlphaAnimation(0f, 1f)
        fadeIn!!.interpolator = DecelerateInterpolator()
        fadeIn!!.duration = 3000


        nameStore.animation = fadeIn
        descriptions.animation = fadeIn
        phoneNumber.animation = fadeIn



        Handler().postDelayed({
            kotlin.run {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 4000)
    }
}