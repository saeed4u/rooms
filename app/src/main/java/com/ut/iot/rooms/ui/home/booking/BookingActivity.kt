package com.ut.iot.rooms.ui.home.booking

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ut.iot.rooms.R
import com.ut.iot.rooms.adapter.RoomItemAdapter
import com.ut.iot.rooms.data.model.ResourceLoading
import com.ut.iot.rooms.data.model.Room
import com.ut.iot.rooms.data.model.Status
import com.ut.iot.rooms.ui.BaseActivity
import com.ut.iot.rooms.ui.home.hotel.detail.HotelDetailViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_hotel_detail.toolbar
import kotlinx.android.synthetic.main.booking_activity.*
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject


/**
 * Created by Saeed on 07/12/2019.
 */
class BookingActivity : BaseActivity(), DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var bookingViewModel: BookingViewModel

    override fun onDateSet(
        view: DatePickerDialog?,
        year: Int,
        monthOfYear: Int,
        dayOfMonth: Int
    ) {
        val formatter = DecimalFormat("00")
        val month = formatter.format(monthOfYear + 1)
        val day = formatter.format(dayOfMonth)
        val date = "$year-$month-$day"
        bookingViewModel.formStateChanged(date)
    }

    @Inject
    lateinit var hotelDetailViewModel: HotelDetailViewModel

    private val rooms = mutableListOf<Room>()

    private lateinit var roomItemAdapter: RoomItemAdapter

    override fun handleResourceLoading(resourceLoading: ResourceLoading?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = null
        roomItemAdapter = RoomItemAdapter(rooms, object : RoomItemClicked {
            override fun onItemClicked(room: Room) {
                rooms.onEach { it.selected = false }
                if (!room.selected) {
                    room.selected = true
                }
                roomItemAdapter.notifyDataSetChanged()
            }

        })
        room_items.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        room_items.adapter = roomItemAdapter
        hotelDetailViewModel.hotelResponse.observe(this, Observer {
            if (it.status == Status.SUCCESS && it.data != null) {
                with(it.data) {
                    this@BookingActivity.rooms.clear()
                    this@BookingActivity.rooms.addAll(rooms)
                    roomItemAdapter.notifyDataSetChanged()
                }
            }
        })
        hotelDetailViewModel.getHotel(intent.getIntExtra("hotel", 0))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    private fun showCheckInDatePicker(){
        val now = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog.newInstance(
            this, now.get(Calendar.YEAR), now.get(
                Calendar.MONTH
            ), now.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.minDate = now
        datePickerDialog.show(supportFragmentManager, "DatePickerDialog")
    }

}