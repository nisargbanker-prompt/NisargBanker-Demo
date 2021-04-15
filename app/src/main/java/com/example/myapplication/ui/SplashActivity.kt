package com.example.myapplication.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.myapplication.R
import com.example.myapplication.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                startActivity(Intent(applicationContext, AuthActivity::class.java))
                finish()
            }
        })

        /*val myanim1 = AnimationUtils.loadAnimation(this,
            R.anim.animation_from_right
        )
        logo.startAnimation(myanim1)*/

    }

    override fun onResume() {
        super.onResume()
        motionLayout.startLayoutAnimation()

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUIAndNavigation(this)
        }
    }

    private fun hideSystemUIAndNavigation(activity: Activity) {
        val decorView: View = activity.window.decorView
        decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}