package com.ut.iot.rooms.ui.home.booking

import android.os.Bundle
import android.view.MenuItem
import android.view.View
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
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


/**
 * Created by Saeed on 07/12/2019.
 */
class BookingActivity : BaseActivity() {


    private val formatter = DecimalFormat("00")

    private var startDate = ""
    private var endDate = ""
    private var selectedRoom: Room? = null

    @Inject
    lateinit var bookingViewModel: BookingViewModel


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
                if (selectedRoom != null) {
                    selectedRoom!!.selected = false
                }
                roomItemAdapter.notifyItemChanged(rooms.indexOf(selectedRoom))
                if (!room.selected) {
                    room.selected = true
                    selectedRoom = room
                }
                roomItemAdapter.notifyItemChanged(rooms.indexOf(selectedRoom))
                bookingViewModel.formStateChanged(startDate, endDate, room.id)
            }

        })
        room_items.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
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
        check_out_date.setOnClickListener {
            showCheckOutDatePicker()
        }

        check_in_date.setOnClickListener {
            showCheckInDatePicker()
        }

        bookingViewModel.bookingFormStateLiveData_.observe(this, Observer {
            book_a_room.isEnabled = it.isValid
            if (it.startDate.isNotEmpty()) {
                check_in_date.text = it.startDateFormatted
                check_out_date.visibility = View.VISIBLE
                to.visibility = View.VISIBLE
            }

            if (it.endDate.isNotEmpty()) {
                check_out_date.text = it.endDateFormatted
            }
            if (it.isValid) {
                var dayString = "${it.numberOfDays} day"
                if (it.numberOfDays > 1) {
                    dayString = "${it.numberOfDays} days"
                }
                book_a_room.text = getString(R.string.book_for_days, selectedRoom!!.name, dayString)

                val totalPrice = selectedRoom?.type?.price?.toDouble()?.times(it.numberOfDays)
                price.text = DecimalFormat("0.00").format(totalPrice)

                book_a_room_layout.visibility = View.VISIBLE
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    private fun showCheckInDatePicker() {
        val now = Calendar.getInstance()
        val startDateCal = getStartDateAsCalendar()
        val datePickerDialog = DatePickerDialog.newInstance(
            { _, year, monthOfYear, dayOfMonth ->
                val month = formatter.format(monthOfYear + 1)
                val day = formatter.format(dayOfMonth)
                startDate = "$year-$month-$day"
                bookingViewModel.formStateChanged(startDate, endDate, selectedRoom?.id)
            }, startDateCal.get(Calendar.YEAR), startDateCal.get(
                Calendar.MONTH
            ), startDateCal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.minDate = now
        datePickerDialog.show(supportFragmentManager, "StartDatePickerDialog")
    }

    private fun showCheckOutDatePicker() {
        if (startDate.isEmpty()) {
            return
        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startDateObj = dateFormat.parse(startDate)!!
        val endDateCal = getEndDateAsCalendar()
        val datePickerDialog = DatePickerDialog.newInstance(
            { _, year, monthOfYear, dayOfMonth ->
                val month = formatter.format(monthOfYear + 1)
                val day = formatter.format(dayOfMonth)
                endDate = "$year-$month-$day"
                bookingViewModel.formStateChanged(startDate, endDate, selectedRoom?.id)
            }, endDateCal.get(Calendar.YEAR), endDateCal.get(
                Calendar.MONTH
            ), endDateCal.get(Calendar.DAY_OF_MONTH)
        )
        val calendar = Calendar.getInstance()
        calendar.time = startDateObj
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        datePickerDialog.minDate = calendar
        datePickerDialog.show(supportFragmentManager, "EndDatePickerDialog")
    }

    private fun getStartDateAsCalendar(): Calendar {
        if (startDate.isBlank()) {
            return Calendar.getInstance()
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val now = Calendar.getInstance()
        now.time = dateFormat.parse(startDate)!!
        return now
    }

    private fun getEndDateAsCalendar(): Calendar {
        if (endDate.isBlank()) {
            return Calendar.getInstance()
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val now = Calendar.getInstance()
        now.time = dateFormat.parse(endDate)!!
        return now
    }

}