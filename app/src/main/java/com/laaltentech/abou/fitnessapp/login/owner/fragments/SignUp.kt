package com.laaltentech.abou.fitnessapp.login.owner.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.cameraX.activity.CameraActivity
import com.laaltentech.abou.fitnessapp.cameraX.fragments.ImageViewerFragment
import com.laaltentech.abou.fitnessapp.databinding.FragmentSignupBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.login.data.SignUpResponse
import com.laaltentech.abou.fitnessapp.login.viewmodels.SignUpViewModel
import com.laaltentech.abou.fitnessapp.network.Status
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.Commons
import javax.inject.Inject


class SignUp : Fragment(), Injectable {

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentSignupBinding

    private val signUpViewModel: SignUpViewModel by lazy {
        ViewModelProviders.of(activity!!, viewModelFactory)
            .get(SignUpViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        binding.signUpViewModel = signUpViewModel
        Log.e("Visible", "The view was visible")
        initLayoutAnim()
        viewModelInit()

        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,101 )
        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,102 )

        binding.addProfileImage.setOnClickListener {
            val intent = Intent(activity, CameraActivity::class.java)
            startActivityForResult(intent, SHOW_CAMERA_CODE)
        }

        binding.submit.setOnClickListener {
            if(Commons.isNetworkAvailable(context!!)) {
                if(signUpViewModel.signUpData.userPhoto != null) {
                    signUpViewModel.apiCall.value = "uploadPhoto"
                }
                else{
                    signUpViewModel.apiCall.value = "uploadUser"
                }
            }
            else{
                Toast.makeText(requireContext(), "Oops!! Sorry No Network Available", Toast.LENGTH_SHORT).show()
            }
        }

        super.onActivityCreated(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val mCompressedPath = data?.extras?.getString(ImageViewerFragment.ARG_IMAGE_URL)
            Log.e("Path of the", "the path of the image result is : $mCompressedPath")

            signUpViewModel.signUpData.userPhoto = mCompressedPath
            Glide.with(binding.root)
                .load(signUpViewModel.signUpData.userPhoto)
                .apply( RequestOptions()
                    .placeholder(R.mipmap.camera_click)
                    .error(R.mipmap.camera_click)
                    .fitCenter())
                .into(binding.addProfileImage)
        }
    }

    fun viewModelInit() {
        signUpViewModel.let { it ->
            it.results.observe(viewLifecycleOwner, Observer { item ->
                when (signUpViewModel.apiCall.value) {
                    "uploadUser" -> {
                        when (item.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(
                                    context,
                                    "Congratulations, SignUp Successful",
                                    Toast.LENGTH_LONG
                                ).show()
                                findNavController().popBackStack()
                            }

                            Status.ERROR -> {
                                if (Commons.isNetworkAvailable(requireContext())) {
                                    try {
                                        item.message?.let { msg ->
                                            val response =
                                                Gson().fromJson(msg, SignUpResponse::class.java)
                                            Toast.makeText(
                                                requireContext(),
                                                response.msg,
                                                Toast.LENGTH_LONG
                                            ).show()
                                        } ?: false

                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            requireContext(),
                                            "Oops!! Something went wrong!!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                } else {
                                    Toast.makeText(
                                        activity,
                                        "No network available , comment later!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                            Status.LOADING -> {
                                binding.submit.isEnabled = false
                                binding.progress.progressBar.visibility = View.VISIBLE
                                Toast.makeText(
                                    context,
                                    "Uploading your data please wait...",
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.e("LOADING", "LOADING DATA PLEASE WAIT.....")
                            }
                        }
                    }

                    "uploadPhoto" -> {
                        when (item.status) {
                            Status.SUCCESS -> {
                                binding.submit.isEnabled = true
                                Log.e("Data", "The data received is ${item.data?.userPhoto} & user id is ${item.data?.user_id}")
                                signUpViewModel.signUpData.userPhoto = item.data?.userPhoto
                                binding.progress.progressBar.visibility = View.INVISIBLE
                                Toast.makeText(context, "Photo Uploaded", Toast.LENGTH_LONG).show()
                                signUpViewModel.apiCall.value = "uploadUser"
                            }

                            Status.LOADING -> {
                                binding.submit.isEnabled = false
                                binding.progress.progressBar.visibility = View.VISIBLE
                                Toast.makeText(context, "Uploading your data please wait...", Toast.LENGTH_LONG ).show()
                                Log.e("LOADING", "LOADING DATA PLEASE WAIT.....")
                            }
                            Status.ERROR -> {
                                binding.submit.isEnabled = true
                                binding.progress.progressBar.visibility = View.INVISIBLE
                                if (Commons.isNetworkAvailable(requireContext())) {
                                    try {
                                        item.message?.let { msg ->
                                            val response = Gson().fromJson(msg, SignUpResponse::class.java)
                                            Toast.makeText(requireContext(), response.msg, Toast.LENGTH_LONG).show()
                                        } ?: false

                                    } catch (e: Exception) {
                                        Toast.makeText(requireContext(), "Oops!! Something went wrong!!", Toast.LENGTH_LONG).show()
                                    }

                                } else {
                                    Toast.makeText(activity, "No network available , comment later!", Toast.LENGTH_LONG).show()
                                }
                            }

                        }
                    }
                }
            })
        }
    }

    private fun askForPermission(permission: String, requestCode: Int?) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    permission
                )
            ) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(permission),
                    requestCode!!
                )

            } else {

                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(permission),
                    requestCode!!
                )
            }
        }
    }

    private fun initLayoutAnim() {
        binding.firstName.translationY = -1600f
        binding.regPhoneNumber.translationY = -1600f
        binding.lastName.translationY = -1600f
        binding.password.translationY = -1600f
        binding.confirmPassword.translationY = -1600f
        binding.regEmailId.translationY = -1600f
        binding.profileImageText.translationY = -1600f
        binding.addProfileImage.translationY = -1600f

        Glide.with(context)
            .load(R.drawable.profile_placeholder_foreground)
            .apply(
                RequestOptions()
                    .circleCrop()
            )
            .into(binding.addProfileImage)

        binding.addProfileImage.apply {
            animate().translationYBy(1600f).duration = 850
        }

        binding.profileImageText.apply {
            animate().translationYBy(1600f).duration = 850
        }

        binding.firstName.apply {
            animate().translationYBy(1600f).duration = 800
        }

        binding.lastName.apply {
            animate().translationYBy(1600f).duration = 750
        }

        binding.regPhoneNumber.apply {
            animate().translationYBy(1600f).duration = 650
        }

        binding.password.apply {
            animate().translationYBy(1600f).duration = 600
        }

        binding.confirmPassword.apply {
            animate().translationYBy(1600f).duration = 550
        }

        binding.regEmailId.apply {
            animate().translationYBy(1600f).duration = 500
        }
    }

    companion object{
        const val SHOW_CAMERA_CODE = 101
    }

}