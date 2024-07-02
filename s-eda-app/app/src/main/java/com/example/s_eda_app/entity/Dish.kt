package com.example.s_eda_app.entity

import android.os.Parcel
import android.os.Parcelable



class Dish(val id: Int,
           val title:String?,
           val photoLink: String?,
           val  time: String?,
           val additionTime: String?,
           val recipe: String?,
           val calories:Float?,
           val p:Float?,
           val f:Float?,
           val c:Float?,
          val iter:Int = 0): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(photoLink)
        parcel.writeString(time)
        parcel.writeString(additionTime)
        parcel.writeString(recipe)
        parcel.writeValue(calories)
        parcel.writeValue(p)
        parcel.writeValue(f)
        parcel.writeValue(c)
        parcel.writeInt(iter)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Dish> {
        override fun createFromParcel(parcel: Parcel): Dish {
            return Dish(parcel)
        }

        override fun newArray(size: Int): Array<Dish?> {
            return arrayOfNulls(size)
        }
    }

}