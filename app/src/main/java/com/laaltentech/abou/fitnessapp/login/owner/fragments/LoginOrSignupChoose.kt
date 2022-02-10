package com.laaltentech.abou.fitnessapp.login.owner.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.bottomnav.owner.activity.BottomMainNavActivity
import com.laaltentech.abou.fitnessapp.databinding.LoginOrSignupBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.FragmentDataBindingComponent
import javax.inject.Inject

class LoginOrSignupChoose : Fragment(), Injectable {

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent = FragmentDataBindingComponent(this)

    lateinit var binding: LoginOrSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("TAG", "fragment was tried to be inflated")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_or_signup,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLayoutAnim()

        binding.login.setOnClickListener {
            val action = LoginOrSignupChooseDirections.actionFragmentLoginOrSignupToLogin2()
            findNavController().navigate(action)
        }

        binding.signUp.setOnClickListener {
            val action = LoginOrSignupChooseDirections.actionFragmentLoginOrSignupToSignUp2()
            findNavController().navigate(action)
        }

        binding.bypass.setOnClickListener {
            val intent  = Intent(activity, BottomMainNavActivity::class.java)
            activity?.startActivity(intent)
        }

        askForPermission(Manifest.permission.CAMERA, MediaRecorder.VideoSource.CAMERA)

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

    private fun initLayoutAnim(){
        binding.login.translationX = -1600f
        binding.signUp.translationX = -1600f
        binding.login.apply {
            animate().translationXBy(1600f).duration = 500
        }
        binding.signUp.apply {
            animate().translationXBy(1600f).duration = 800
        }
    }
}