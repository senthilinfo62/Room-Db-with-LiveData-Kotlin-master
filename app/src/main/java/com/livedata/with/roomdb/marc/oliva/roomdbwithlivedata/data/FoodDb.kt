package com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.model.Food

/**
 * Created by axier on 7/2/18.
 */

@Database(entities = [(Food::class)], version = 5, exportSchema = false)
abstract class FoodDb : RoomDatabase() {

    companion object {
        private var INSTANCE: FoodDb? = null
        fun getDataBase(context: Context): FoodDb {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, FoodDb::class.java, "food")
                        .allowMainThreadQueries().build()
            }
            return INSTANCE as FoodDb
        }
    }

    abstract fun daoFood():DaoFood

}
