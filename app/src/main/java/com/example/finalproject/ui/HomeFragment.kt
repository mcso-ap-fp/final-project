package com.example.finalproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.finalproject.MainActivity
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val tag = "homeFragTag"
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.observeUserDisplayName().observe(viewLifecycleOwner) {
            if (it != null)  {
                binding.welcomeMessage.text = String.format(getString(R.string.welcome_message), it.substringBefore(" "))
            } else {
                binding.welcomeMessage.text = "Welcome!"
            }
        }

        preferencesFragment()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun preferencesFragment() {
        binding.findRestaurant.setOnClickListener {
            val mainActivity: MainActivity = activity as MainActivity
            mainActivity.loadPreferencesFragment()
        }
    }
}