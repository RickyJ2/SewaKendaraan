@file:Suppress("DEPRECATION")

package com.example.sewakendaraan.activity.profile

import android.annotation.SuppressLint
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.sewakendaraan.activity.profile.ProfileActivity
import java.io.IOException

@SuppressLint("ViewConstructor")
class CameraView(context: ProfileActivity, private val mCamera: Camera) : SurfaceView(context), SurfaceHolder.Callback {
    private val mHolder: SurfaceHolder

    init{
        mCamera.setDisplayOrientation(90)
        mHolder = holder
        mHolder.addCallback(this)
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder){
        try{
            mCamera.setPreviewDisplay(mHolder)
            mCamera.startPreview()
        } catch (e: IOException){
            Log.d("error", "Camera error on SurfaceCrated" + e.message)
        }
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {
        if(mHolder.surface == null) return
        try{
            mCamera.setPreviewDisplay(mHolder)
            mCamera.startPreview()
        }catch (e: IOException){
            Log.d("Error", "Camera error on SurfaceChanged" + e.message)
        }
    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        mCamera.stopPreview()
        mCamera.release()
    }
}