package com.example.dhbatchu.controller

import android.media.MediaPlayer

class Audio : MediaPlayer.OnCompletionListener {
    override fun onCompletion(mediaPlayer: MediaPlayer) {
        //        val manager: AssetManager = assets
//        try {
//            val file: Array<String> = manager.list(FOLDER_ASSETS) as Array<String>
//            for (i in file.indices) {
//                try {
//                   val imgStream = manager.open(FOLDER_ASSETS + "/" + file[i])
//                    val d: Drawable? = Drawable.createFromStream(imgStream, null)
//                    binding.imgQuestion.setImageDrawable(d)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
    }
}