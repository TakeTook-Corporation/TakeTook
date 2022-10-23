package com.example.home.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.home.module.databinding.FragmentHomeBinding
import com.example.home.module.recycler.mappers.toDelegateItemList
import com.example.recycler_utils.MainAdapter
import com.example.home.module.recycler.stories.StoryDelegate
import com.example.home.module.recycler.stories.StoryModel

/**
 * @author y.gladkikh
 */
const val STUB_ICON_URL = "https://shoptech.ru/images/catalog/konstruktory-lego/lego-creator-31117-priklyucheniya-na-kos_32088_full.jpg"
const val STUB_SITE_LINK = "https://github.com/YanaGlad"
const val STUB_USER_ICON_URL =
    "https://sun1-15.userapi.com/impg/N_KDRQrSx7i57VSJnN0Fs-RtnZktfPkpmY7zmg/LMTvDxbrrX0.jpg?size=736x919&quality=95&sign=c21ad2637ce435c497792c6c55494513&type=album"

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var storiesAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        submitStubData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun submitStubData() {
        val stubList = listOf(
            StoryModel(
                id = 1,
                iconUrl = STUB_ICON_URL,
                siteUrl = STUB_SITE_LINK,
            ),
            StoryModel(
                id = 2,
                iconUrl = STUB_ICON_URL,
                siteUrl = STUB_SITE_LINK,
            ),
            StoryModel(
                id = 3,
                iconUrl = STUB_ICON_URL,
                siteUrl = STUB_SITE_LINK,
            ),
            StoryModel(
                id = 4,
                iconUrl = STUB_ICON_URL,
                siteUrl = STUB_SITE_LINK,
            ),
            StoryModel(
                id = 5,
                iconUrl = STUB_ICON_URL,
                siteUrl = STUB_SITE_LINK,
            ),
            StoryModel(
                id = 6,
                iconUrl = STUB_ICON_URL,
                siteUrl = STUB_SITE_LINK,
            ),
        )

        storiesAdapter.submitList(stubList.toDelegateItemList())
    }

    private fun initAdapter() {
        storiesAdapter = MainAdapter()

        storiesAdapter.apply {
            addDelegate(StoryDelegate())
        }
        binding.storiesList.adapter = storiesAdapter
    }
}
