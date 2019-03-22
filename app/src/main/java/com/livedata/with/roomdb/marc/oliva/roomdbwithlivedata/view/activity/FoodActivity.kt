package com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.view.adapter.FoodRecyclerAdapter
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.R
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.model.Food
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.data.FoodDb
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class FoodActivity : AppCompatActivity(), FoodRecyclerAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {


    private var contactRecyclerView: RecyclerView? = null
    private var recyclerViewAdapter: FoodRecyclerAdapter? = null

    private var viewModel: FoodListViewModel? = null

    private var db: FoodDb? = null

    var month = arrayOf((Calendar.getInstance().get(Calendar.MONTH) + 1).toString(), "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")

    var spinner: Spinner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = FoodDb.getDataBase(this)

        spinner = this.spinner_sample
        spinner!!.setOnItemSelectedListener(this)

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, month)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(aa)

        contactRecyclerView = findViewById(R.id.recycler_view)
        recyclerViewAdapter = FoodRecyclerAdapter(arrayListOf(), this)

        contactRecyclerView!!.layoutManager = LinearLayoutManager(this)
        contactRecyclerView!!.adapter = recyclerViewAdapter

        viewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)

//        viewModel!!.getListFoods().observe(this, Observer { contacts ->
//            recyclerViewAdapter!!.addFoods(contacts!!)
//        })
        fab.setOnClickListener {
            var intent = Intent(applicationContext, FoodDetailsActivity::class.java)
            startActivity(intent)
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.delete_all_items -> {
////                deleteAllContacts()
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    private fun deleteAllContacts() {
//        db!!.daoFood().deleteAllContacts()
//    }

    override fun onItemClick(food: Food) {
        var intent = Intent(applicationContext, FoodDetailsActivity::class.java)
        intent.putExtra("idContact", food.id)
        startActivity(intent)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//       month[position]



        viewModel!!.getmonth(month[position]).observe(this, Observer { feed ->
            recyclerViewAdapter!!.addFoods(feed!!)

            var totals: Int = 0

            for (i in feed.indices) {
                Log.d("datas", feed[i].amount.toString())
//
                if (totals == 0) {
                    totals = feed[i].amount
                } else {
                    totals = totals + feed[i].amount
                }


            }
            total.setText("Total : " + totals)
        })


    }
}

