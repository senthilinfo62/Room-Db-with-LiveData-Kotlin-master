package com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.model.Food
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.data.FoodDb

/**
 * Created by ThinkSoft on 21/12/2017.
 */
class FoodListViewModel(application: Application) : AndroidViewModel(application) {

    var listFood: LiveData<List<Food>>
    private val appDb: FoodDb

    init {
        appDb = FoodDb.getDataBase(this.getApplication())
        listFood = appDb.daoFood().getAllfoods()
    }

    fun getListFoods(): LiveData<List<Food>> {
        return listFood
    }


    fun getmonth(month:String):LiveData<List<Food>> {
        listFood = appDb.daoFood().findByMonth(month)

        return listFood
    }


    fun addFood(Food: Food) {
        addAsynTask(appDb).execute(Food)
    }


    class addAsynTask(db: FoodDb) : AsyncTask<Food, Void, Void>() {
        private var FoodDb = db
        override fun doInBackground(vararg params: Food): Void? {
            FoodDb.daoFood().insertfoods(params[0])
            return null
        }

    }

}