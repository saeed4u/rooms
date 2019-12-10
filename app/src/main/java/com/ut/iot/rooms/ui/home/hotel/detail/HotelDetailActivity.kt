package com.ut.iot.rooms.ui.home.hotel.detail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ut.iot.rooms.R
import com.ut.iot.rooms.adapter.HotelDetailItemAdapter
import com.ut.iot.rooms.data.model.*
import com.ut.iot.rooms.ui.BaseActivity
import com.ut.iot.rooms.ui.home.booking.BookingActivity
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = null
        hotelDetailItemAdapter = HotelDetailItemAdapter((hotelItems))
        items.adapter = hotelDetailItemAdapter

        hotelDetailViewModel.hotelResponse.observe(this, Observer {
            Timber.d("It $it")
            if (it.status == Status.SUCCESS && it.data != null) {
                with(it.data) {
                    init(this)
                }
            }
        })
        hotelDetailViewModel.getHotel(intent.getIntExtra("hotel", 0))
        items.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
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
        book_a_room.setOnClickListener {
            val bookingIntent = Intent(this, BookingActivity::class.java)
            bookingIntent.putExtra("hotel",intent.getIntExtra("hotel", 0))
            startActivity(bookingIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }
    private fun init(hotel: Hotel) {
        with(hotel) {
            supportActionBar?.title = name
            val image = images.first()

            hotelItems.add(HotelDetail("Rating", HotelDetailType.TITLE))
            hotelItems.add(HotelDetail((4..5).random().toString(), HotelDetailType.RATING))
            hotelItems.add(HotelDetail("Rooms", HotelDetailType.TITLE))
            rooms.forEach {
                hotelItems.add(HotelDetail(it.name, HotelDetailType.ROOM, it.type!!.price, it))
            }
            hotelDetailItemAdapter.notifyDataSetChanged()
            initImage(image)
        }
    }

    private fun initImage(image: Image) {
        Picasso.get().load(image.src)
            .error(R.drawable.hotel1)
            .into(hotel_image, object : Callback {
                override fun onSuccess() {
                   val bitmap =  (hotel_image.drawable as BitmapDrawable).bitmap
                    Palette.from(bitmap).generate {
                        it?.apply {
                            book_a_room.backgroundTintList = ColorStateList.valueOf(getDominantColor(resources.getColor(R.color.colorSecondary)))
                        }
                    }
                }

                override fun onError(e: Exception?) {
                }

            })
    }


}