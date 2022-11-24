package com.example.finalproject.ui

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.finalproject.MainActivity
import com.example.finalproject.PreferencesViewModel
import com.example.finalproject.databinding.FragmentPreferencesBinding
import com.example.finalproject.model.CuisineRepository
import com.example.finalproject.model.PreferenceTypes
import com.example.finalproject.model.UserPreferences
import com.google.firebase.auth.FirebaseAuth

class PreferencesFragment: Fragment() {
    private val cuisineRepository = CuisineRepository()
    private var _binding: FragmentPreferencesBinding? = null
    private val binding get() = _binding!!
    private val preferencesViewModel: PreferencesViewModel by activityViewModels()
    private val cuisineList = cuisineRepository.fetchData()
    private var preferencesList: List<UserPreferences> = emptyList()

    companion object {
        const val tag = "preferencesFragTag"
        fun newInstance(): PreferencesFragment {
            return PreferencesFragment()
        }
    }

    private fun initAdapter() {
        val adapter = ArrayAdapter(binding.cuisineOptions.context, R.layout.simple_spinner_item, cuisineList)
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
        preferencesViewModel.fetchInitialPreferences()

        preferencesViewModel.observePreferences().observe(viewLifecycleOwner) {
            preferencesList = it
            it.forEach { preference ->
               when (preference.preferenceType) {
                   PreferenceTypes.CUISINE.type -> {
                      binding.cuisineOptions.setSelection(cuisineList.indexOf(preference.preferenceValue))
                   }
                   PreferenceTypes.PRICE.type -> {
                       try {
                           val price = preference.preferenceValue.toFloat()
                           binding.priceSlider.value = price
                       } catch (e: Error) {
                           Log.d("PreferencesFragment: ", "invalid value for price", e)
                       }
                   }
                   PreferenceTypes.DISTANCE.type -> {
                       try {
                           val price = preference.preferenceValue.toFloat()
                           binding.distanceSlider.value = price
                       } catch (e: Error) {
                           Log.d("PreferencesFragment: ", "invalid value for distance", e)
                       }
                   }
               }
            }
        }

        binding.save.setOnClickListener {
            savePreferences()
            submitPreferences()
        }

        binding.cancel.setOnClickListener {
            cancel()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun savePreferences() {
        if (preferencesList.isNotEmpty()) {
            preferencesList.forEach {
                when (it.preferenceType) {
                    PreferenceTypes.CUISINE.type -> {
                        it.preferenceValue = binding.cuisineOptions.selectedItem.toString()
                    }
                    PreferenceTypes.PRICE.type -> {
                        it.preferenceValue = binding.priceSlider.value.toString()
                    }
                    PreferenceTypes.DISTANCE.type -> {
                        it.preferenceValue = binding.distanceSlider.value.toString()
                    }
                }
            }
            preferencesViewModel.updatePreferences(preferencesList)
        } else {
            val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
            currentUserUid?.let {
                val price = UserPreferences(
                    null,
                    null,
                    currentUserUid,
                    PreferenceTypes.PRICE.type,
                    binding.priceSlider.value.toString()
                )
                val distance = UserPreferences(
                    null,
                    null,
                    currentUserUid,
                    PreferenceTypes.DISTANCE.type,
                    binding.distanceSlider.value.toString()
                )
                val cuisine = UserPreferences(
                    null,
                    null,
                    currentUserUid,
                    PreferenceTypes.CUISINE.type,
                    binding.cuisineOptions.selectedItem.toString()
                )
                preferencesViewModel.updatePreferences(listOf(price, distance, cuisine))
            }
        }
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