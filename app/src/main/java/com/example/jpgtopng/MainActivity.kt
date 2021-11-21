package com.example.jpgtopng

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.jpgtopng.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import java.io.File

class MainActivity : MvpAppCompatActivity(), MainView {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val presenter by moxyPresenter { MainPresenter(ImageRepo()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtons()
    }

    private fun setButtons() {
        binding.startConvert.setOnClickListener {
            presenter.startConverting()
        }

        binding.cancelConvert.setOnClickListener {
            presenter.cancelConverting()
        }
    }

    override fun showImage(imgFile: File) {
        val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
        binding.imageView.setImageBitmap(bitmap)
    }

    override fun onError(t: Throwable) {
        Log.v("_TEST", t.localizedMessage)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}