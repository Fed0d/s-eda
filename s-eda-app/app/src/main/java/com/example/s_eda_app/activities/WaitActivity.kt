package com.example.s_eda_app.activities

import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.s_eda_app.R

class WaitActivity : AppCompatActivity() {
    var counter=0
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wait)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        progressBar=findViewById(R.id.progress_bar)
        Handler().postDelayed(object : Runnable {
            override fun run() {
                if (counter <= 100) {
                    progressBar.progress = counter
                    counter++
                    Handler().postDelayed(this, 60)
                } else {
                    Handler().removeCallbacks(this)
                }
            }
        }, 50)
    }
}