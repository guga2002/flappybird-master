
package com.kostasdrakonakis.flappybird

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.github.kostasdrakonakis.annotation.Intent

@Intent
class MainActivity : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(FlappyBird(), AndroidApplicationConfiguration())
    }
}
