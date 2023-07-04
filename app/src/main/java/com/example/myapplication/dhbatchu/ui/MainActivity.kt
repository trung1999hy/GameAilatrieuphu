package com.example.myapplication.dhbatchu.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.BaseActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainDhBinding
import com.example.myapplication.dhbatchu.controller.AudioController

class MainActivity : BaseActivity<ActivityMainDhBinding>() {

    private var audioController: AudioController? = null

    override fun getLayoutBinding(): ActivityMainDhBinding {
        return ActivityMainDhBinding.inflate(layoutInflater)
    }

    override fun updateUI(savedInstanceState: Bundle?) {
         binding.imgStart.setOnImageClickListener {
            startActivity(Intent(this@MainActivity, PlayActivity::class.java))

           finish()
        }

        audioController = AudioController(this) {
            binding.imgStart.visibility = View.VISIBLE
            binding.lottieCenter.visibility = View.INVISIBLE
        }
        audioController?.playSoundFirst()
    }

    override fun onPause() {
        super.onPause()
        audioController?.releaseSound()
    }

}