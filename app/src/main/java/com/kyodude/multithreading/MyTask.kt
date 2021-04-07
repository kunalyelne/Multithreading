package com.kyodude.multithreading

import android.os.AsyncTask
import android.widget.TextView
import java.lang.ref.WeakReference

class MyTask(textView: TextView) : AsyncTask<String, Void, String>() {

    var tvMainReference: WeakReference<TextView>;

    init {
        tvMainReference = WeakReference(textView)
    }

    override fun onPreExecute() {
        super.onPreExecute()
        tvMainReference.get()?.setText("Starting AsyncTask")
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
        tvMainReference.get()?.setText(result)
    }
}