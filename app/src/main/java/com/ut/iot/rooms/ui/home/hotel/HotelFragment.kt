package com.ut.iot.rooms.ui.home.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ut.iot.rooms.R
import com.ut.iot.rooms.adapter.HotelAdapter
import com.ut.iot.rooms.data.model.Hotel
import com.ut.iot.rooms.data.model.Status
import com.ut.iot.rooms.ui.BaseFragment
import kotlinx.android.synthetic.main.list_fragment.*
import timber.log.Timber
import javax.inject.Inject

class HotelFragment : BaseFragment() {

    @Inject
    lateinit var hotelViewModel: HotelViewModel

    private val hotels = mutableListOf<Hotel>()
    private lateinit var hotelAdapter: HotelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hotelAdapter = HotelAdapter(hotels, object : HotelItemClickListener{
            override fun onItemClicked(hotel: Hotel) {

            }
        })
        items.adapter = hotelAdapter
        hotelViewModel.hotelResponse.observe(viewLifecycleOwner, Observer {
            Timber.d("Resource $it")
            if (it.status == Status.SUCCESS){
                hotels.clear()
                it.data?.apply {
                    hotels.addAll(this)
                }
                Timber.d("Items ${hotels.size}")
                if (hotels.isEmpty()){
                    no_items.visibility = View.VISIBLE
                    items.visibility = View.GONE
                    return@Observer
                }
                items.visibility = View.VISIBLE
                no_items.visibility = View.GONE
                hotelAdapter.notifyDataSetChanged()
            }
        })
        swipe_to_refresh.apply {
            setOnRefreshListener {
                isRefreshing = false
                hotelViewModel.getHotels(true)
            }
        }
        hotelViewModel.getHotels()
        no_item_tv.text = getString(R.string.no_hotels)
    }
}
