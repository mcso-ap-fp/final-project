package com.example.finalproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.RVDiffAdapter
import com.example.finalproject.databinding.FragmentPreferencesBinding
import com.example.finalproject.model.CuisineRepository

class PreferencesFragment: Fragment() {
    private lateinit var adapter: RVDiffAdapter
    private val cuisineRepository = CuisineRepository()
    private var _binding: FragmentPreferencesBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val tag = "preferencesFragTag"
        fun newInstance(): PreferencesFragment {
            return PreferencesFragment()
        }
    }

    private fun initAdapter() {
        val recyclerView = binding.cuisineOptions
        adapter = RVDiffAdapter()
        adapter.submitList(cuisineRepository.fetchData())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
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
        super.onViewCreated(view, savedInstanceState)
    }
}