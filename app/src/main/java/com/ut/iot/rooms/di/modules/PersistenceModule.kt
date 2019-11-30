package com.ut.iot.rooms.di.modules

import androidx.room.Room
import com.ut.iot.rooms.R
import com.ut.iot.rooms.Rooms
import com.ut.iot.rooms.db.RoomsDB
import com.ut.iot.rooms.db.dao.*
import com.ut.iot.rooms.state.StateManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Saeed on 2019-10-18.
 */
@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun providesStateManager(rooms: Rooms): StateManager {
        return StateManager(rooms)
    }


    @Provides
    @Singleton
    fun databaseProvider(rooms: Rooms): RoomsDB {
        return Room.databaseBuilder(
            rooms,
            RoomsDB::class.java,
            rooms.getString(R.string.app_name)
        ).addMigrations().fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun userDaoProvider(db: RoomsDB): UserDao {
        return db.userDao()
    }

    @Provides
    @Singleton
    fun bookingDaoProvider(db: RoomsDB): BookingDao {
        return db.bookingDao()
    }

    @Provides
    @Singleton
    fun roomDaoProvider(db: RoomsDB): RoomDao {
        return db.roomDao()
    }

    @Provides
    @Singleton
    fun roomTypeDaoProvider(db: RoomsDB): RoomTypeDao {
        return db.roomTypeDao()
    }

    @Provides
    @Singleton
    fun roomImageDaoProvider(db: RoomsDB): ImageDao {
        return db.imageDao()
    }


}