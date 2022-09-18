package com.example.taketook2.ui.profile.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.emoji.fragments.delegateItem.MainAdapter
import com.example.taketook2.STUB_USER_ICON_URL
import com.example.taketook2.databinding.FragmentProfileBinding
import com.example.taketook2.ui.delegate.toProfileDelegateItemList
import com.example.taketook2.ui.profile.info.delegate.characteristic.UserCharacteristicsDelegate
import com.example.taketook2.ui.profile.info.delegate.characteristic.UserCharacteristicsModel
import com.example.taketook2.ui.profile.info.delegate.model.UserModel
import com.example.taketook2.ui.profile.info.delegate.profileheader.HeaderModel
import com.example.taketook2.ui.profile.info.delegate.profileheader.HeaderModelDelegate
import com.example.taketook2.ui.profile.info.delegate.ratedegate.RatingDelegate
import com.example.taketook2.ui.profile.info.delegate.ratedegate.RatingModel

/**
 * @author y.gladkikh
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        //TODO remove stubs
        val rating = RatingModel(
            1,
            3
        )

        val characteristics = listOf(
            UserCharacteristicsModel(
                id = 1,
                title = "City:",
                description = "Voronezh"
            )
        )

        val user = UserModel(
            userId = 12,
            header = HeaderModel(
                iconUrl = STUB_USER_ICON_URL,
                userName = "Yana Glad"
            ),
            rating = rating,
            characteristics = characteristics,
        )

        profileAdapter.submitList(user.toProfileDelegateItemList())
    }

    private fun initAdapter() {
        profileAdapter = MainAdapter()

        profileAdapter.apply {
            addDelegate(UserCharacteristicsDelegate())
            addDelegate(RatingDelegate())
            addDelegate(HeaderModelDelegate())
        }
        binding.characteristicsRecycler.adapter = profileAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "PROFILE_FRAGMENT_TAG"
    }
}
