package com.laaltentech.abou.fitnessapp.cameraX.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraX
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.databinding.FragmentCameraClickLayoutBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.destination
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.Executors
import javax.inject.Inject

class CameraClickFragment: Fragment() , Injectable {
    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding : FragmentCameraClickLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_camera_click_layout,
            container,
            false)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        CameraX.unbindAll()

        super.onActivityCreated(savedInstanceState)

        binding.viewCamera.bindToLifecycle(this)

        binding.clickImage.setOnClickListener {
            takePicture()
            binding.clickImage.isEnabled = false
        }
    }


    private fun takePicture() {

        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

        val executor = Executors.newSingleThreadExecutor()
        // trigger image capture
        val outputDirectory: File = storageDir
//        val photoFile = File(outputDirectory, "${System.currentTimeMillis()}.jpg")
        var photosCompressed = File(outputDirectory, "${System.currentTimeMillis()}.jpg")
        binding.viewCamera.takePicture(photosCompressed, executor,
            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.e("IMAGE URI" , "WAS : ${photosCompressed.absolutePath}")

                    lifecycleScope.launch {
                        photosCompressed = Compressor.compress(requireContext(), photosCompressed){
                            default()
                            destination(photosCompressed)
                        }

                        val sizeInMb = photosCompressed.length() / (1024.0 * 1024)

                        val sizeInMbStr = "%.2f".format(sizeInMb)

                        Log.e("file size", "Size=${sizeInMbStr}MB")
                    }

                    val action = CameraClickFragmentDirections.actionCameraClickFragmentToImageViewerFragment()
                    action.uri = photosCompressed.absolutePath
                    findNavController().navigate(action)
                    Log.e("Image", "Successfully saved image")
                }

                override fun onError(exception: ImageCaptureException) {
                    binding.clickImage.isEnabled = true
                    Log.e("Image", exception.toString())
                    //todo call if any error occurs
                }

            }
        )
    }

}