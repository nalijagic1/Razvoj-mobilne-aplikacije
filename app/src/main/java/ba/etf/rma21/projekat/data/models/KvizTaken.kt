package ba.etf.rma21.projekat.data.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
@Entity
data class KvizTaken(@PrimaryKey @SerializedName("id")val id:Int,
                     @ColumnInfo(name = "student")@SerializedName("student") val student:String,
                     @ColumnInfo(name = "osvojeniBodovi") @SerializedName("osvojeniBodovi") val osvojeniBodovi:Int,
                     @ColumnInfo(name = "datumRada") @SerializedName("datumRada") var datumRada:String,
                     @ColumnInfo(name = "KvizId") @SerializedName("KvizId") var KvizId:Int,
                     @Ignore @SerializedName("message")val message:String?,
                     @Ignore var pitanja:List<Pitanje>?) {
    constructor(id:Int,student:String, osvojeniBodovi:Int,datumRada:String, KvizId:Int) : this(id, student, osvojeniBodovi, datumRada, KvizId, null, null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KvizTaken

        if (id != other.id) return false
        if (student != other.student) return false
        if (osvojeniBodovi != other.osvojeniBodovi) return false
        if (datumRada != other.datumRada) return false
        if (KvizId != other.KvizId) return false
        return true
    }
}

