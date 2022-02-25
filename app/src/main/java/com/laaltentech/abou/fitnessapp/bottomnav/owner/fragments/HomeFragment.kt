package com.laaltentech.abou.fitnessapp.bottomnav.owner.fragments

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.bottomnav.owner.activity.BottomMainNavActivity
import com.laaltentech.abou.fitnessapp.databinding.FragmentHomeBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.CONSTANTS
import com.laaltentech.abou.fitnessapp.util.FragmentDataBindingComponent
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable {
    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var counter = 0

    var dataBindingComponent = FragmentDataBindingComponent(this)

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false, dataBindingComponent)
        (activity as AppCompatActivity).supportActionBar!!.show()

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity as (BottomMainNavActivity)).binding.navView.visibility = View.VISIBLE

        val sharedPref = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val firstName = sharedPref.getString(CONSTANTS.FIRST_NAME, "")
        if(!firstName.isNullOrEmpty()){
            binding.textView.text = "Welcome $firstName!!"
        }

        binding.loader.startAnimation(
            AnimationUtils.loadAnimation(activity, R.anim.rotate_but_not_fade)
        )

        binding.doshaClick.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToGlutesFragment()
            findNavController().navigate(action)
        }

        binding.prakriti.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToFullBodyWorkout()
            findNavController().navigate(action)
        }

        binding.dincharya.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToUpperBodyWorkout()
            findNavController().navigate(action)
        }

        binding.ritucharya.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToFragmentOthersWorkout()
            findNavController().navigate(action)
        }

        autoRefresh(requireContext());
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu_source, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.log_out -> {
                showConfirmation("Show")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun <T> showConfirmation(vararg params: T) {
        BottomSheetCustomDialog(title = "Confirmation",
            message = "Are you sure you want to Logout?",
            positiveActionTitle = "Yes",
            context = requireContext(),
            args = *arrayOf(params),
            actionPositive = this::performAction
        ).show()
    }

    private fun <T> performAction(vararg param: T) {
        val h = Handler()

        Toast.makeText(context, "Successfully logged out ", Toast.LENGTH_LONG ).show()

        h.postDelayed(Runnable {
            (context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .clearApplicationUserData() // note: it has a return value!
        }, 1000)
    }

    fun autoRefresh(context: Context) {
        val h = Handler()
        val delay : Long = 4000 //milliseconds
        h.postDelayed(Runnable {
//            Log.e("Counter", "The counter value is $counter")
            if(activity != null){
                when(counter){
                    1 -> {
                        binding.textView3.text = "Limit sugar,caffeine and processed foods."
                        binding.textView3.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    }

                    2 -> {
                        binding.textView3.text = "Snack on local seasonal fruits & nuts."
                        binding.textView3.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    }

                    3 -> {
                        binding.textView3.text = "Walk for atleast 10 mins after main meals."
                        binding.textView3.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    }

                    4 -> {
                        binding.textView3.text = "Only drink warm water after 45 mins of any meal. Little sips can be taken between meals if thirsty."
                        binding.textView3.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    }

                    5 -> {
                        binding.textView3.text = "Get atleast 2 hours of movement everyday - 1 hour in the morning & 1 hour in the evening."
                        binding.textView3.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    }

                    6 -> {
                        binding.textView3.text = "Exercise in fresh air."
                        binding.textView3.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    }

                    7 -> {
                        binding.textView3.text = "Never exercise on a full stomach."
                        binding.textView3.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    }

                    8 -> {
                        binding.textView3.text = "Don't use technology after 10 pm."
                        binding.textView3.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    }

                    9 -> {
                        binding.textView3.text = "Be thankful for the food you eat."
                        binding.textView3.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    }

                    10 -> {
                        binding.textView3.text = "Eat your meals without any distraction."
                        binding.textView3.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    }
                }
                counter += 1
                //Activity written here will be called every 2 sec
                autoRefresh(context = context)
            }
        }, delay)
    }
}