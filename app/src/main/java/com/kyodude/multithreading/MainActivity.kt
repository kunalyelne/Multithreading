package com.kyodude.multithreading

import android.content.Context
import android.os.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kyodude.multithreading.databinding.ActivityMainBinding
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity(), UiThreadCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myTask: MyTask
    private lateinit var customThread: MyHandlerThread
    private lateinit var mUIHandler: UIHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textViewMain = binding.tvMain;
        textViewMain.setText("Hello World");

//        myTask = MyTask(textViewMain)
//
//        myTask.execute("Runnnning")

        // handler for UI thread to receive message from worker thread
        mUIHandler = UIHandler(Looper.getMainLooper())
        mUIHandler.setContext(this)

        // create and start a new worker thread

        // create and start a new worker thread
        customThread = MyHandlerThread("HandlerThread")
        customThread.setUIThreadCallback(this)
        customThread.start()


        val handler2 = Handler()
        handler2.postDelayed( {
            customThread.addMessage(1)
        },1000)

        val handler = Handler()
        handler.postDelayed( {
          customThread.addMessage(2)
        },1000)

    }

    override fun onDestroy() {
        super.onDestroy()
        myTask.cancel(true)

        customThread.quit();
        customThread.interrupt();
    }

    override fun publishToUiThread(message: Int) {
        mUIHandler.sendEmptyMessage(message)
    }

    inner class UIHandler(looper: Looper): Handler(looper) {
        private var mWeakRefContext: WeakReference<Context>? = null

        fun setContext(context: Context?) {
            mWeakRefContext = WeakReference(context)
        }

        // simply show a toast message
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> if (mWeakRefContext != null && mWeakRefContext!!.get() != null) Toast.makeText(mWeakRefContext!!.get(), "Message 1 has been processed", Toast.LENGTH_SHORT).show()
                2 -> if (mWeakRefContext != null && mWeakRefContext!!.get() != null) Toast.makeText(mWeakRefContext!!.get(), "Message 2 has been processed", Toast.LENGTH_SHORT).show()
                else -> {
                    Toast.makeText(mWeakRefContext!!.get(), "Unknown message has been processed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}