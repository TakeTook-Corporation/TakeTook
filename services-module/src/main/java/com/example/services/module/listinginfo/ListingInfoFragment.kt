package com.example.services.module.listinginfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.services.module.R
import com.example.services.module.databinding.FragmentListingInfoBinding
import com.example.services.module.databinding.FragmentServicesBinding

/**
 * @author y.gladkikh
 */
class ListingInfoFragment : Fragment() {

    private var _binding: FragmentListingInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListingInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
