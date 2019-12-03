package com.ut.iot.rooms.ui.home.hotel.detail

import android.os.Bundle
import androidx.lifecycle.Observer
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
        hotelDetailViewModel.hotelResponse.observe(this, Observer {
            Timber.d("It $it")
            if (it.status == Status.SUCCESS && it.data != null) {
                with(it.data) {
                   init(this)
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

    private fun init(hotel: Hotel){
        with(hotel){
            supportActionBar?.title = name
            val image = images.first()

            hotelItems.add(HotelDetail("Rating",))

            initImage(image)
        }
    }

}