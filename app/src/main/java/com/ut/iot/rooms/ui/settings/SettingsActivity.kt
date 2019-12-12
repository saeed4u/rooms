package com.ut.iot.rooms.ui.settings

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import com.ut.iot.rooms.R
import com.ut.iot.rooms.data.model.ResourceLoading
import com.ut.iot.rooms.db.dao.*
import com.ut.iot.rooms.ui.BaseActivity
import kotlinx.android.synthetic.main.settings_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Saeed on 12/12/2019.
 */
class SettingsActivity: BaseActivity() {

    @Inject
    lateinit var bookingDao: BookingDao

    @Inject
    lateinit var hotelDao: HotelDao

    @Inject
    lateinit var imageDao: ImageDao

    @Inject
    lateinit var roomDao: RoomDao

    @Inject
    lateinit var roomTypeDao: RoomTypeDao

    @Inject
    lateinit var userDao: UserDao

    override fun handleResourceLoading(resourceLoading: ResourceLoading?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        val displayMode = AppCompatDelegate.getDefaultNightMode()
        night_mode.isChecked = displayMode == AppCompatDelegate.MODE_NIGHT_YES
        night_mode.setOnCheckedChangeListener{ _: CompoundButton, b: Boolean ->
            if (b){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
        clear_cache.setOnClickListener {
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                imageDao.deleteAll()
                roomTypeDao.deleteAll()
                roomDao.deleteAll()
                hotelDao.deleteAll()
                bookingDao.deleteAll()
                userDao.deleteAll()

            }
        }
    }
}