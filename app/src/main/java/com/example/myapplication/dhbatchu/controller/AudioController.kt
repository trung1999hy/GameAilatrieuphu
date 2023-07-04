package com.example.myapplication.dhbatchu.controller

import android.content.Context
import android.media.MediaPlayer
import com.example.myapplication.R
import java.io.IOException
import java.util.*

class AudioController(context: Context, onCompletionAudio: () -> Unit) : MediaPlayer.OnCompletionListener{

    private var mMediaPlayer: MediaPlayer
    private var mMediaPlayerBg: MediaPlayer
    private var context: Context
    private var onCompletionAudio: () -> Unit = {}

    init {
        mMediaPlayer = MediaPlayer()
        mMediaPlayerBg = MediaPlayer()
        this.context = context
        this.onCompletionAudio = onCompletionAudio

    }

    fun playSoundFirst() {
        try {
            mMediaPlayer = MediaPlayer.create(context, R.raw.chaomung1)
            mMediaPlayer.apply {
                start()
                setOnCompletionListener(this@AudioController)
            }
        } catch (exception: IOException) {
            mMediaPlayer.release()
        }
    }

    fun playSoundClick() {
        try {
            mMediaPlayer = MediaPlayer.create(context, R.raw.click)
            mMediaPlayer.apply {
                start()
            }
        } catch (exception: IOException) {
            mMediaPlayer.release()
        }
    }

    private fun playSoundBackground() {
        val listSound = mutableListOf<Int>()
        listSound.add(R.raw.nhac0)
        listSound.add(R.raw.nhac1)
        listSound.add(R.raw.nhac2)
        val random = Random()
        try {
            mMediaPlayerBg = MediaPlayer.create(context, listSound[random.nextInt(listSound.size)])
            mMediaPlayerBg.apply {
                start()
                isLooping = true
            }
        } catch (exception: IOException) {
            mMediaPlayerBg.release()
        }
    }

    fun playSoundNewGame() {
        if (mMediaPlayerBg.isPlaying){
            mMediaPlayerBg.stop()
            mMediaPlayerBg.release()
        }
        val listSound = mutableListOf<Int>()
        listSound.add(R.raw.daylagi0)
        listSound.add(R.raw.daylagi1)
        listSound.add(R.raw.daylagi2)
        listSound.add(R.raw.daylagi3)
        listSound.add(R.raw.daylagi4)
        listSound.add(R.raw.daylagi5)
        val random = Random()
        try {
            mMediaPlayer = MediaPlayer.create(context, listSound[random.nextInt(listSound.size)])
            mMediaPlayer.apply {
                start()
            }
        } catch (exception: IOException) {
            mMediaPlayer.release()
        }
        playSoundBackground()
    }

    fun playSoundFalse() {
        if (mMediaPlayer.isPlaying){
            mMediaPlayer.stop()
            mMediaPlayer.release()
        }
        val listSound = mutableListOf<Int>()
        listSound.add(R.raw.chuachinhxac0)
        listSound.add(R.raw.chuachinhxac1)
        listSound.add(R.raw.chuachinhxac2)
        listSound.add(R.raw.chuachinhxac3)
        listSound.add(R.raw.chuachinhxac4)
        listSound.add(R.raw.chuachinhxac5)
        listSound.add(R.raw.chuachinhxac6)
        val random = Random()
        try {
            mMediaPlayer = MediaPlayer.create(context, listSound[random.nextInt(listSound.size)])
            mMediaPlayer.apply {
                start()
            }
        } catch (exception: IOException) {
            mMediaPlayer.release()
        }
    }

    fun playSoundSuccess() {
        if (mMediaPlayer.isPlaying){
            mMediaPlayer.stop()
            mMediaPlayer.release()
        }
        val listSound = mutableListOf<Int>()
        listSound.add(R.raw.chinhxac0)
        listSound.add(R.raw.chinhxac1)
        listSound.add(R.raw.chinhxac2)
        listSound.add(R.raw.chinhxac3)
        listSound.add(R.raw.chinhxac4)
        listSound.add(R.raw.chinhxac5)
        listSound.add(R.raw.chinhxac6)
        listSound.add(R.raw.chinhxac7)
        listSound.add(R.raw.chinhxac8)
        listSound.add(R.raw.chinhxac9)
        val random = Random()
        try {
            mMediaPlayer = MediaPlayer.create(context, listSound[random.nextInt(listSound.size)])
            mMediaPlayer.apply {
                start()
            }
        } catch (exception: IOException) {
            mMediaPlayer.release()
        }
    }

    override fun onCompletion(p0: MediaPlayer?) {
        onCompletionAudio.invoke()
        mMediaPlayer.release()
    }

    fun releaseSound(){
        try {
            if (mMediaPlayer.isPlaying){
                mMediaPlayer.stop()
                mMediaPlayer.release()
            }
            if (mMediaPlayerBg.isPlaying){
                mMediaPlayerBg.stop()
                mMediaPlayerBg.release()
            }
        }catch (_: Exception){}
    }
}