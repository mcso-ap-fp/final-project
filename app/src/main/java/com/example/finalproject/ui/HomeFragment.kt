package com.example.finalproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val tag = "homeFragTag"
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Todo probably need to store this as Live Data. Home Fragment is created before login is completed.
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (currentUser?.displayName != null) {
            binding.welcomeMessage.text = String.format(getString(R.string.welcome_message), currentUser.displayName!!.substringBefore(" "))
        } else {
            binding.welcomeMessage.text = "Welcome!"
        }

        super.onViewCreated(view, savedInstanceState)
    }
}