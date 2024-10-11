package com.pzbdownloaders.scribble.main_screen.domain.usecase

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.pzbdownloaders.scribble.common.presentation.MainActivity

fun cameraPermissionHandle(activity: MainActivity):String {
   return  if (ContextCompat.checkSelfPermission(
            activity,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    ) {
       "true"
    } else if (shouldShowRequestPermissionRationale(activity,android.Manifest.permission.CAMERA)) {

       "Rationale shown"
    }else{
      "false"
    }
}