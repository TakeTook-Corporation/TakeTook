package com.example.profile.module.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.profile.module.databinding.FragmentProfileBinding
import com.example.profile.module.info.delegate.characteristic.UserCharacteristicsDelegate
import com.example.profile.module.info.delegate.characteristic.UserCharacteristicsModel
import com.example.profile.module.info.delegate.mappers.toProfileDelegateItemList
import com.example.profile.module.info.delegate.model.UserModel
import com.example.profile.module.info.delegate.profileheader.HeaderModel
import com.example.profile.module.info.delegate.profileheader.HeaderModelDelegate
import com.example.profile.module.info.delegate.ratedegate.RatingDelegate
import com.example.profile.module.info.delegate.ratedegate.RatingModel
import com.example.recycler_utils.MainAdapter

/**
 * @author y.gladkikh
 */
const val STUB_USER_ICON_URL = "https://sun1-15.userapi.com/impg/N_KDRQrSx7i57VSJnN0Fs-RtnZktfPkpmY7zmg/LMTvDxbrrX0.jpg?size=736x919&quality=95&sign=c21ad2637ce435c497792c6c55494513&type=album"

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
            ),
            UserCharacteristicsModel(
                id = 1,
                title = "Email:",
                description = "monsterglad12@gmail.com"
            ),
            UserCharacteristicsModel(
                id = 1,
                title = "Phone:",
                description = "+7(930) 410-46-11"
            ),
            UserCharacteristicsModel(
                id = 1,
                title = "Preferences:",
                description = "Using sells"
            ),
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
