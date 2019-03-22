package com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by axier on 7/2/18.
 */

@Entity(tableName = "foods")
data class Food(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "date")
    var date: String = "",

    @ColumnInfo(name = "month")
    var month: Int = 0,

    @ColumnInfo(name = "amount")
    var amount: Int = 0

)
