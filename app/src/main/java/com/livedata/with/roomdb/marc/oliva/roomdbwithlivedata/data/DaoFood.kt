package com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.model.Food

/**
 * Created by ThinkSoft on 19/12/2017.
 */
@Dao
interface DaoFood {

    @Query("select * from foods")
    fun getAllfoods(): LiveData<List<Food>>



//    @Query("SELECT * FROM customer WHERE month LIKE :month AND " + "last_name LIKE :last LIMIT 1")
//    fun findByName(month: String, last: String): foods

    @Query("select * from foods where id in (:id)")
    fun getFootById(id: Int): Food

    @Query("SELECT * FROM foods WHERE month IN(:month)")
    fun findByMonth(month: String):  LiveData<List<Food>>

    @Query("SELECT * FROM foods WHERE date = :date")
    fun findBydate(date: String): Food

    @Query("UPDATE foods SET amount = :amount WHERE id = :id")
    fun updateamount(id: Int, amount: Int): Int

    @Insert
    fun insertAll(foodss: List<Food>)

    @Delete
    fun delete(client: Food)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertfoods(foods: Food)




    @Update
    fun updatefoods(foods: Food)
}