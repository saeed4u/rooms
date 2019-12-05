package com.ut.iot.rooms.ui.home.hotel.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ut.iot.rooms.R
import com.ut.iot.rooms.adapter.HotelDetailItemAdapter
import com.ut.iot.rooms.data.model.*
import com.ut.iot.rooms.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_hotel_detail.*
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Saeed on 02/12/2019.
 */
class HotelDetailActivity : BaseActivity() {

    @Inject
    lateinit var hotelDetailViewModel: HotelDetailViewModel

    private val hotelItems = mutableListOf<HotelDetail>()

    private lateinit var hotelDetailItemAdapter: HotelDetailItemAdapter

    override fun handleResourceLoading(resourceLoading: ResourceLoading?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.title = null
        hotelDetailItemAdapter = HotelDetailItemAdapter((hotelItems))
        items.adapter = hotelDetailItemAdapter
        hotelDetailViewModel.hotelResponse.observe(this, Observer {
            Timber.d("It $it")
            if (it.status == Status.SUCCESS && it.data != null) {
                with(it.data) {
                    init(this)
                }
                4
            }
        })
        hotelDetailViewModel.getHotel(intent.getIntExtra("hotel", 0))
        items.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                Timber.d("new state $newState")
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    book_a_room.extend()
                }else{
                    book_a_room.shrink()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && book_a_room.isExtended) {
                    book_a_room.shrink()
                }
            }
        })
    }

    private fun initImage(image: Image) {
        Picasso.get().load(image.src)
            .error(R.drawable.hotel1)
            .into(hotel_image, object : Callback {
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {
                }

            })
    }

    private fun init(hotel: Hotel) {
        with(hotel) {
            supportActionBar?.title = name
            val image = images.first()

            hotelItems.add(HotelDetail("Rating", HotelDetailType.TITLE))
            hotelItems.add(HotelDetail((4..5).random().toString(), HotelDetailType.RATING))
            hotelItems.add(HotelDetail("Price", HotelDetailType.TITLE))
            hotelItems.add(HotelDetail("${this.price}",HotelDetailType.PRICE))
            hotelItems.add(HotelDetail("Rooms", HotelDetailType.TITLE))
            rooms.forEach {
                hotelItems.add(HotelDetail(it.name, HotelDetailType.ROOM))
            }
            hotelDetailItemAdapter.notifyDataSetChanged()
            initImage(image)
        }
    }

}