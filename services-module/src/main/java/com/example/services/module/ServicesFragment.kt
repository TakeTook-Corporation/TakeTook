package com.example.services.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recycler_utils.MainAdapter
import com.example.services.module.databinding.FragmentServicesBinding
import com.example.services.module.mappers.toListingDelegateItemList
import com.example.services.module.recycler.listing.ListingDelegate
import com.example.services.module.recycler.listing.ListingModel

const val STUB_ICON_URL = "https://sun1-15.userapi.com/impg/N_KDRQrSx7i57VSJnN0Fs-RtnZktfPkpmY7zmg/LMTvDxbrrX0.jpg?size=736x919&quality=95&sign=c21ad2637ce435c497792c6c55494513&type=album"

/**
 * @author y.gladkikh
 */
class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!

    private lateinit var listingsAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        val stubListings = listOf(
            ListingModel(
                id = 1,
                title = "Футболка Sell-Cell фирменный мерч",
                price = 700,
                iconLink = STUB_ICON_URL,
                city = "Масква",
                usingAutomatSystem = false,
            ),
            ListingModel(
                id = 2,
                title = "Продам гараж",
                price = 200000,
                iconLink = STUB_ICON_URL,
                city = "Дубайск",
                usingAutomatSystem = true,
            ),
            ListingModel(
                id = 1,
                title = "Футболка Sell-Cell фирменный мерч",
                price = 700,
                iconLink = STUB_ICON_URL,
                city = "Масква",
                usingAutomatSystem = false,
            ),
            ListingModel(
                id = 2,
                title = "Продам гараж",
                price = 200000,
                iconLink = STUB_ICON_URL,
                city = "Дубайск",
                usingAutomatSystem = true,
            ),
            ListingModel(
                id = 1,
                title = "Футболка Sell-Cell фирменный мерч",
                price = 700,
                iconLink = STUB_ICON_URL,
                city = "Масква",
                usingAutomatSystem = false,
            ),
            ListingModel(
                id = 2,
                title = "Продам гараж",
                price = 200000,
                iconLink = STUB_ICON_URL,
                city = "Дубайск",
                usingAutomatSystem = true,
            ),
            ListingModel(
                id = 1,
                title = "Футболка Sell-Cell фирменный мерч",
                price = 700,
                iconLink = STUB_ICON_URL,
                city = "Масква",
                usingAutomatSystem = false,
            ),
            ListingModel(
                id = 2,
                title = "Продам гараж",
                price = 200000,
                iconLink = STUB_ICON_URL,
                city = "Дубайск",
                usingAutomatSystem = true,
            ),
            ListingModel(
                id = 1,
                title = "Футболка Sell-Cell фирменный мерч",
                price = 700,
                iconLink = STUB_ICON_URL,
                city = "Масква",
                usingAutomatSystem = false,
            ),
            ListingModel(
                id = 2,
                title = "Продам гараж",
                price = 200000,
                iconLink = STUB_ICON_URL,
                city = "Дубайск",
                usingAutomatSystem = true,
            ),
        )

        listingsAdapter.submitList(stubListings.toListingDelegateItemList())
    }

    private fun initAdapter() {
        listingsAdapter = MainAdapter()

        listingsAdapter.apply {
            addDelegate(ListingDelegate())
        }
        binding.listingsRecycler.adapter = listingsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
