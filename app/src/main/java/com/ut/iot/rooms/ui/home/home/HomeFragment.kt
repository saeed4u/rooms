package com.ut.iot.rooms.ui.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ut.iot.rooms.R
import com.ut.iot.rooms.adapter.BookingAdapter
import com.ut.iot.rooms.data.model.Booking
import com.ut.iot.rooms.data.model.Status
import com.ut.iot.rooms.ui.BaseFragment
import kotlinx.android.synthetic.main.list_fragment.*
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    private val bookings = mutableListOf<Booking>()
    private lateinit var bookingAdapter: BookingAdapter

    @Inject
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookingAdapter = BookingAdapter(bookings)
        items.layoutManager =
            GridLayoutManager(baseActivity, 2)
        items.adapter = bookingAdapter
        homeViewModel.bookingResponse.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                Timber.d("Bookings ${it.data}")
                it.data?.apply {
                    bookings.clear()
                    bookings.addAll(this)
                    if (bookings.isEmpty()) {
                        no_items.visibility = View.VISIBLE
                        items.visibility = View.GONE
                        return@apply
                    }
                    no_items.visibility = View.GONE
                    items.visibility = View.VISIBLE
                    bookingAdapter.notifyDataSetChanged()
                }
            }
        })

        swipe_to_refresh.apply {

            setOnRefreshListener {
                isRefreshing = false
                homeViewModel.getBookings(true)
            }

        }

        homeViewModel.getBookings()

    }
}
