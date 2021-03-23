package com.laaltentech.abou.fitnessapp.cameraX.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.databinding.FragmentImageViewerBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.OnBackPressed
import java.io.File
import javax.inject.Inject

class ImageViewerFragment : Fragment() , Injectable, OnBackPressed {

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentImageViewerBinding


    private lateinit var uri : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_viewer, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        uri = savedInstanceState?.getString("uri")
            ?: ImageViewerFragmentArgs.fromBundle(arguments!!).uri

        Glide.with(binding.root)
            .load(uri)
            .apply( RequestOptions()
                .placeholder(R.mipmap.camera_click)
                .error(R.mipmap.camera_click)
                .fitCenter())
            .into(binding.imageShow)

        binding.doneImage.setOnClickListener {
            exit()
        }

    }

    fun deleteFile(uri : String){

        val directory = File(uri)
        directory.delete()

        if(directory.exists()){
            directory.canonicalFile.delete()
            if(directory.exists()){
                context?.deleteFile(directory.name)
            }
        }
    }

    override fun onBackPressed() {
        deleteFile(uri)
        findNavController().popBackStack()
    }

    private fun exit() {
        val output = Intent()
        output.putExtra(ARG_IMAGE_URL, uri)
        activity?.setResult(Activity.RESULT_OK, output)
        activity?.finish()
    }



    companion object {
        // arguments used in sending and receiving intents
        const val ARG_IMAGE_URL = "image_url"
    }
}