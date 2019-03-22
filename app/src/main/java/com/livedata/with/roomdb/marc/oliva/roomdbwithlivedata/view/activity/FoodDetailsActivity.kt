package com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.view.activity

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.R
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.data.DaoFood
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.model.Food
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.data.FoodDb
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.activity_contact_details.*
import java.text.SimpleDateFormat
import java.util.*

class FoodDetailsActivity : AppCompatActivity() {


    private var daoFood: DaoFood? = null
    private var viewModel: FoodListViewModel? = null

    private var currentContact: Int? = null
    private var food: Food? = null
    var cal = Calendar.getInstance()
    var month: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)
        var db: FoodDb = FoodDb.getDataBase(this)

        daoFood = db.daoFood()

        viewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        currentContact = intent.getIntExtra("idContact", -1)
        if (currentContact != -1) {
            setTitle(R.string.edit_contact_title)
            food = daoFood!!.getFootById(currentContact!!.toInt())
            name_edit_text.setText(food!!.date)
            number_edit_text.setText(food!!.amount.toString())
            this.month = food!!.month

        } else {
            setTitle(R.string.add_contact_title)
            invalidateOptionsMenu()
            name_edit_text.setOnClickListener {
                cal()
            }

        }
    }


    fun cal() {

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                    view: DatePicker, year: Int, monthOfYear: Int,
                    dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }


        DatePickerDialog(
                this@FoodDetailsActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show()

    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val month = "MM"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val sdf1 = SimpleDateFormat(month, Locale.US)
        Log.d("date", sdf.format(cal.time))
        this.month = sdf1.format(cal.time).toInt()
        name_edit_text.setText(sdf.format(cal.time))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.done_item -> {
                if (currentContact == -1) {
                    saveContact()
                    Toast.makeText(this, getString(R.string.save_contact), Toast.LENGTH_SHORT).show()
                } else {
                    updateContact()
                    Toast.makeText(this, getString(R.string.update_contact), Toast.LENGTH_SHORT).show()
                }

                finish()
            }
            R.id.delete_item -> {
                deleteContact()
                Toast.makeText(this, getString(R.string.delete_contact), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        if (currentContact == -1) {
            menu.findItem(R.id.delete_item).isVisible = false
        }
        return true
    }

    private fun saveContact() {
        var date = name_edit_text.text.toString()
        var amount = number_edit_text.text.toString()
        var food = Food(0, "sankar", date, month, amount.toInt())
        viewModel!!.addFood(food)
    }

    private fun deleteContact() {
        daoFood!!.delete(this!!.food!!)
    }

    private fun updateContact() {
        var date = name_edit_text.text.toString()
        var amount = number_edit_text.text.toString()
        var food = Food(food!!.id, "sankar", date, month, amount.toInt())
        daoFood!!.updatefoods(food)
    }
}
