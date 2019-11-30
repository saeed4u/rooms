package customer.ecorewards.android.db.typeconverter

import androidx.room.Room
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.ut.iot.rooms.data.model.RoomType
import customer.ecorewards.android.data.model.auth.User
import customer.ecorewards.android.data.model.main.Company
import customer.ecorewards.android.data.model.main.Group
import customer.ecorewards.android.data.model.main.ecopartner.EcoPartner
import customer.ecorewards.android.data.model.main.request.Bin
import customer.ecorewards.android.data.model.main.request.Driver
import customer.ecorewards.android.data.model.main.request.ServiceType
import customer.ecorewards.android.data.model.store.product.Product
import customer.ecorewards.android.data.model.store.product.ProductCategory
import customer.ecorewards.android.data.model.store.product.ProductStock




/**
 * Created by Saeed on 2019-06-21.
 */
class RoomsDBConverter {

    var moshi: Moshi = Moshi.Builder().build()


    @TypeConverter
    fun fromRoomType(roomType: RoomType?): String {
        return moshi.adapter(RoomType::class.java).toJson(roomType)
    }

    @TypeConverter
    fun toRoomType(roomType: String): RoomType? {
        return moshi.adapter(RoomType::class.java).fromJson(roomType)
    }

    @TypeConverter
    fun fromRooms(rooms: List<Room>): String {
        val type = Types.newParameterizedType(List::class.java, Room::class.java)
        val adapter = moshi.adapter<List<Room>>(type)
        return adapter.toJson(rooms)
    }

    @TypeConverter
    fun toRoomss(rooms: String): List<Room>? {
        val type = Types.newParameterizedType(List::class.java, Room::class.java)
        val adapter = moshi.adapter<List<Room>>(type)
        return adapter.fromJson(rooms)
    }


}