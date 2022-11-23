package com.example.finalproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.finalproject.MainActivity
import com.example.finalproject.RVDiffAdapter
import com.example.finalproject.databinding.FragmentPreferencesBinding
import com.example.finalproject.model.CuisineRepository
import kotlin.math.max

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

    // Todo: Init cuisine dropdown
    private fun initAdapter() {
//        val recyclerView = binding.cuisineOptions
//        adapter = RVDiffAdapter()
//        adapter.submitList(cuisineRepository.fetchData())
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(context)
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
        val cuisine = ""
        val maxPrice = binding.priceSlider.value.toInt().toString()

        // Launch Restaurant Activity
        val mainActivity: MainActivity = activity as MainActivity
        mainActivity.launchRestaurantDetailActivity(maxDistance, maxPrice)
    }

    private fun cancel () {
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun milesToMeters(miles: Double): Double {
        return miles * 1609.34
    }
}