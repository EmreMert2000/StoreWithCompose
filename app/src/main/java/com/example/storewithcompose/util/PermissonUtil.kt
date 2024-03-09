package com.example.storewithcompose.util

// PermissionUtil.kt

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object PermissionUtil {

    suspend fun checkGalleryPermission(context: Context): Boolean =
        suspendCancellableCoroutine { continuation ->
            val permissionStatus = ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )

            continuation.resume(permissionStatus == PackageManager.PERMISSION_GRANTED)
        }
}


