package com.kyodude.multithreading

import android.os.AsyncTask
import android.widget.TextView
import java.lang.ref.WeakReference

class MyTask(textView: TextView) : AsyncTask<String, Void, String>() {

    var tvMain: WeakReference<TextView>;

    init {
        tvMain = WeakReference(textView)
    }

    override fun onPreExecute() {
        super.onPreExecute()
        tvMain.get()?.setText("Starting AsyncTask")
    }

    override fun doInBackground(vararg p0: String?): String {

       for(i in 0..100000) {
           //Time consuming loop :)
           println("hell")
       }
        return "Done"
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        tvMain.get()?.setText(result)
    }
}