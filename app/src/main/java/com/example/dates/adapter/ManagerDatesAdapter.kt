package com.example.dates.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dates.R
import com.example.dates.manager.InfoDate
import com.example.dates.model.Date

class ManagerDatesAdapter(
    val context: Context,
    private val activity: Activity,
    private var list: ArrayList<Date>
) :
    RecyclerView.Adapter<ManagerDatesAdapter.ViewHolder>() {

    private lateinit var dates: Date

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val delete: ImageButton = item.findViewById(R.id.delete)
        val edit: ImageButton = item.findViewById(R.id.edit)
        val info: ImageButton = item.findViewById(R.id.info)
        val topic: TextView = item.findViewById(R.id.topic)
        val date: TextView = item.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.date_secretary, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dates = list[position]

        holder.topic.text = dates.topic
        holder.date.text = dates.ap_date

        holder.delete.visibility = View.GONE
        holder.edit.visibility = View.GONE

        holder.info.setOnClickListener {
            dates = list[position]

            val i = Intent(context, InfoDate::class.java)
            i.putExtra("date", dates.ap_date)
            i.putExtra("time", dates.ap_time)
            i.putExtra("person", dates.personOrSide)
            i.putExtra("topic", dates.topic)
            i.putExtra("inOrOut", dates.insideOrOutside)
            i.putExtra("address", dates.address)
            i.putExtra("completed", dates.completed)
            i.putExtra("note", dates.note)
            context.startActivity(i)
            activity.finish()
        }
    }

    fun filterList(filterList: ArrayList<Date>) {
        list = filterList
        notifyDataSetChanged()
    }
}