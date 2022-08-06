package com.example.taketook2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.emoji.fragments.delegateItem.MainAdapter
import com.example.taketook2.STUB_ICON_URL
import com.example.taketook2.STUB_SITE_LINK
import com.example.taketook2.databinding.FragmentHomeBinding
import com.example.taketook2.ui.delegate.toDelegateItemList
import com.example.taketook2.ui.home.recycler.stories.StoryDelegate
import com.example.taketook2.ui.home.recycler.stories.StoryModel

/**
 * @author y.gladkikh
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var storiesAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        submitStubData()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        storiesAdapter = MainAdapter()

        storiesAdapter.apply {
            addDelegate(StoryDelegate())
        }
        binding.storiesList.adapter = storiesAdapter
    }
}
