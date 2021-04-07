package com.kyodude.multithreading

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kyodude.multithreading.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myTask: MyTask
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textViewMain = binding.tvMain;
        textViewMain.setText("Hello World");

        myTask = MyTask(textViewMain)

//        textViewMain.setText("Doing task in background :(")

        myTask.execute("Runnnning")
    }
}