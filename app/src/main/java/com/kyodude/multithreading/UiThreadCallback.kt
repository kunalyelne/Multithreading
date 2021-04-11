package com.kyodude.multithreading

interface UiThreadCallback {
    fun publishToUiThread(message: Int)
}
