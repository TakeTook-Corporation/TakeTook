package com.example.taketook2.ui.profile.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.emoji.fragments.delegateItem.MainAdapter
import com.example.taketook2.databinding.FragmentProfileBinding
import com.example.taketook2.ui.delegate.toCharacteristicsDelegateItemList
import com.example.taketook2.ui.delegate.toRatingDelegateItemList
import com.example.taketook2.ui.home.recycler.stories.StoryDelegate
import com.example.taketook2.ui.profile.info.delegate.characteristic.CommonCharacteristicsDelegate
import com.example.taketook2.ui.profile.info.delegate.characteristic.CommonCharacteristicsModel
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

        val rating = listOf(
            RatingModel(
                1,
                3
            )
        )

        val characteristics = listOf(
            CommonCharacteristicsModel(
                id = 1,
                title = "City:",
                description = "Voronezh"
            )
        )

        profileAdapter.submitList(rating.toRatingDelegateItemList() + characteristics.toCharacteristicsDelegateItemList())
    }

    private fun initAdapter() {
        profileAdapter = MainAdapter()

        profileAdapter.apply {
            addDelegate(CommonCharacteristicsDelegate())
            addDelegate(RatingDelegate())
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
