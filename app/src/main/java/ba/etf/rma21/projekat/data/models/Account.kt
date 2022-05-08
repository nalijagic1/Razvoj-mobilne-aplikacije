package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Account (
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "acHash")var acHash:String,
    @ColumnInfo(name = "lastUpdate") var lastUpdate:String) {
}