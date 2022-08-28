package com.example.taketook2.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taketook2.IS_SIGNED_IN
import com.example.taketook2.R
import com.example.taketook2.databinding.FragmentProfileFlowBinding
import com.example.taketook2.ui.profile.info.ProfileFragment
import com.example.taketook2.ui.profile.signin.SignInFragment

/**
 * @author y.gladkikh
 */
class ProfileFlowFragment : Fragment() {

    private var _binding: FragmentProfileFlowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileFlowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (IS_SIGNED_IN) {
            navigate(ProfileFragment(), ProfileFragment.TAG)
        } else {
            navigate(SignInFragment(), SignInFragment.TAG)
        }
    }

    private fun navigate(fragment: Fragment, tag: String) {
        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "PROFILE_FLOW_TAG"
    }
}
