package com.example.dates.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.dates.MainViewModel
import com.example.dates.MainViewModelFactory
import com.example.dates.R
import com.example.dates.model.Date
import com.example.dates.repository.Repository
import com.example.dates.secretary.EditDate
import com.example.dates.secretary.InfoToDate
import com.example.dates.util.NetworkConnection

@SuppressLint("NotifyDataSetChanged")
class SecretaryDatesAdapter(
    val context: Context,
    private val activity: Activity,
    private var list: ArrayList<Date>
) : RecyclerView.Adapter<SecretaryDatesAdapter.ViewHolder>() {

    private lateinit var dates: Date
    private val isConnected = NetworkConnection(context)
    private var repository = Repository()
    private var viewModelFactory = MainViewModelFactory(repository)
    private var viewModel = ViewModelProvider(
        context as ViewModelStoreOwner,
        viewModelFactory
    )[MainViewModel::class.java]

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val topic: TextView = itemView.findViewById(R.id.topic)
        val date: TextView = itemView.findViewById(R.id.date)
        val delete: ImageButton = itemView.findViewById(R.id.delete)
        val edit: ImageButton = itemView.findViewById(R.id.edit)
        val info: ImageButton = itemView.findViewById(R.id.info)
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

        holder.delete.setOnClickListener {
            if (isConnected.isInternetAvailable()) {
                dates = list[position]

                viewModel.deleteDateFromSecretary(dates.id)
            }
        }

        holder.edit.setOnClickListener {
            dates = list[position]

            val i = Intent(context, EditDate::class.java)
            i.putExtra("id", dates.id.toString())
            i.putExtra("date", dates.ap_date)
            i.putExtra("time", dates.ap_time)
            i.putExtra("person", dates.personOrSide)
            i.putExtra("topic", dates.topic)
            i.putExtra("inOrOut", dates.insideOrOutside)
            i.putExtra("address", dates.address)
            i.putExtra("completed", dates.completed)
            context.startActivity(i)
            activity.finish()
        }

        holder.info.setOnClickListener {
            dates = list[position]

            val i = Intent(context, InfoToDate::class.java)
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

    init {
        viewModel.defaultResponse.observe(context as LifecycleOwner) { response ->
            if (response.isSuccessful) {
                val res = response.body()!!

                if (res.message == "Date deleted") {
                    Toast.makeText(context, "تم حذف المعاد", Toast.LENGTH_SHORT).show()

                    list.remove(dates)
                    notifyDataSetChanged()
                } else if (res.message == "Date not deleted") {
                    Toast.makeText(context, "لم يتم حذف المعاد", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun filterList(filterList: ArrayList<Date>) {
        list = filterList
        notifyDataSetChanged()
    }
}