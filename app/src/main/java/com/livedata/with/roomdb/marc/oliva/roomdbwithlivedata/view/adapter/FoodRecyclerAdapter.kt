package com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.R
import com.livedata.with.roomdb.marc.oliva.roomdbwithlivedata.model.Food

/**
 * Created by ThinkSoft on 20/12/2017.
 */
class FoodRecyclerAdapter(Foods: ArrayList<Food>, listener: OnItemClickListener) : RecyclerView.Adapter<FoodRecyclerAdapter.RecyclerViewHolder>() {

    private var listFoods: List<Food> = Foods

    private var listenerFood: OnItemClickListener = listener

    interface OnItemClickListener {
        fun onItemClick(Food: Food)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int {
        return listFoods.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder?, position: Int) {
        var currentFood: Food = listFoods[position]

        var nameFood = currentFood.date
        var numberFood = currentFood.amount

        holder!!.mName.text = nameFood
        holder!!.mNumber.text = numberFood.toString()

        holder.bind(currentFood, listenerFood)

    }

    fun addFoods(listFoods: List<Food>) {
        this.listFoods = listFoods
        notifyDataSetChanged()
    }


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mName = itemView.findViewById<TextView>(R.id.name_contact)!!
        var mNumber = itemView.findViewById<TextView>(R.id.number_contact)!!

        fun bind(Food: Food, listener: OnItemClickListener) {
            itemView.setOnClickListener {
                listener.onItemClick(Food)
            }
        }

    }
}