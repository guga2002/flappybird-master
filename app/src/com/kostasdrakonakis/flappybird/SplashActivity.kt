
package com.kostasdrakonakis.flappybird

import android.app.Activity
import android.os.Bundle
import com.github.kostasdrakonakis.androidnavigator.IntentNavigator
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        playButton.setOnClickListener {
            IntentNavigator.startMainActivity(this)
        }
    }
}