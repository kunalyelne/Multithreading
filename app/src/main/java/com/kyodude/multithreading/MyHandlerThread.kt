package com.kyodude.multithreading

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import java.lang.ref.WeakReference

class MyHandlerThread(name: String): HandlerThread(name) {
    private lateinit var handler: customHandler

    // use weak reference to avoid activity being leaked
    //Instead of callback we can make use of Local Broadcast as well
    private lateinit var mUiThreadCallback: WeakReference<UiThreadCallback>

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        handler = customHandler(looper)
    }

    fun addMessage(message: Int) {
        handler.sendEmptyMessage(message)
    }

    fun setUIThreadCallback(uiThreadCallback: UiThreadCallback) {
        this.mUiThreadCallback = WeakReference(uiThreadCallback)
    }

    inner class customHandler(looper: Looper): Handler(looper) {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> {
                    try {
                        sleep(1000)
                    } catch (e: InterruptedException) {
                    }
                    if (mUiThreadCallback.get() != null) {
                        mUiThreadCallback.get()!!.publishToUiThread(1)
                    }
                }
                2 -> {
                    try {
                        sleep(5000)
                    } catch (e: InterruptedException) {
                    }
                    if (mUiThreadCallback.get() != null) {
                        mUiThreadCallback.get()!!.publishToUiThread(2)
                    }
                }
                else -> {
                }
            }
        }
    }
}