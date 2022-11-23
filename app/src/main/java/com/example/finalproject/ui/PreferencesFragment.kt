package com.example.finalproject.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.finalproject.MainActivity
import com.example.finalproject.RVDiffAdapter
import com.example.finalproject.databinding.FragmentPreferencesBinding
import com.example.finalproject.model.CuisineRepository

class PreferencesFragment: Fragment() {
    private lateinit var adapter: RVDiffAdapter
    private val cuisineRepository = CuisineRepository()
    private var _binding: FragmentPreferencesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    companion object {
        const val tag = "preferencesFragTag"
        fun newInstance(): PreferencesFragment {
            return PreferencesFragment()
        }
    }

    private fun initAdapter() {
        var adapter = ArrayAdapter(binding.cuisineOptions.context, R.layout.simple_spinner_item, cuisineRepository.fetchData())
        binding.cuisineOptions.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreferencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()

        binding.save.setOnClickListener {
            submitPreferences()
        }

        binding.cancel.setOnClickListener {
            cancel()
        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun submitPreferences() {
        val maxDistance = milesToMeters(binding.distanceSlider.value.toDouble()).toString()
        var cuisine: String? = binding.cuisineOptions.selectedItem.toString()
        val maxPrice = binding.priceSlider.value.toInt().toString()

        if (cuisine == "No Preference")
            cuisine = null

        // Launch Restaurant Activity
        val mainActivity: MainActivity = activity as MainActivity
        mainActivity.launchRestaurantDetailActivity(maxDistance, maxPrice, cuisine)
    }

    private fun cancel () {
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun milesToMeters(miles: Double): Double {
        return miles * 1609.34
    }
}