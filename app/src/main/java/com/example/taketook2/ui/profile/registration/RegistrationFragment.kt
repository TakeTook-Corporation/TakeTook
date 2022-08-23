package com.example.taketook2.ui.profile.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taketook2.R
import com.example.taketook2.databinding.FragmentProfileBinding
import com.example.taketook2.databinding.FragmentRegistrationBinding

/*
 * @author y.gladkikh
 */
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "REGISTRATION_FRAGMENT_TAG"
    }
}