package com.motorro.appupdatewrapper

import android.app.PendingIntent
import android.content.Intent
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.tasks.OnFailureListener
import com.google.android.play.core.tasks.OnSuccessListener
import com.google.android.play.core.tasks.Task
import com.nhaarman.mockitokotlin2.spy

const val APP_PACKAGE = "com.motorro.appupdatewrapper"
const val APP_VERSION = 100500

/**
 * Creates custom update info
 * @param updateAvailability One of [com.google.android.play.core.install.model.UpdateAvailability] values
 * @param installStatus One of [com.google.android.play.core.install.model.InstallStatus] values
 * @param immediateAvailable If true, immediate update is available
 * @param flexibleAvailable If true, flexible update is available
 */
fun TestAppTest.createUpdateInfo(updateAvailability: Int, installStatus: Int, immediateAvailable: Boolean = true, flexibleAvailable: Boolean = true): AppUpdateInfo = AppUpdateInfo(
    APP_PACKAGE,
    APP_VERSION,
    updateAvailability,
    installStatus,
    if (immediateAvailable) PendingIntent.getBroadcast(application, 0, Intent(), 0) else null,
    if (flexibleAvailable) PendingIntent.getBroadcast(application, 0, Intent(), 0) else null
)

/**
 * A task that may [succeed] or [fail] on demand
 */
abstract class TestUpdateInfoTask: Task<AppUpdateInfo>() {
    private lateinit var onSuccessListener: OnSuccessListener<in AppUpdateInfo>
    private lateinit var onFailureListener: OnFailureListener

    override fun addOnSuccessListener(p0: OnSuccessListener<in AppUpdateInfo>): Task<AppUpdateInfo> {
        onSuccessListener = p0
        return this
    }

    override fun addOnFailureListener(p0: OnFailureListener): Task<AppUpdateInfo> {
        onFailureListener = p0
        return this
    }

    /**
     * Calls success listener with [updateInfo]
     */
    fun succeed(updateInfo: AppUpdateInfo) {
        onSuccessListener.onSuccess(updateInfo)
    }

    /**
     * Calls failure listener with [error]
     */
    fun fail(error: Exception) {
        onFailureListener.onFailure(error)
    }
}

/**
 * Creates a test task
 */
fun createTestInfoTask(): TestUpdateInfoTask = spy()
