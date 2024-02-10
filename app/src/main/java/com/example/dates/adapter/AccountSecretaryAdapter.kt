package com.example.dates.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.dates.MainViewModel
import com.example.dates.MainViewModelFactory
import com.example.dates.R
import com.example.dates.admin.EditSecretary
import com.example.dates.model.UserSecretary
import com.example.dates.repository.Repository
import com.example.dates.util.NetworkConnection

@SuppressLint("NotifyDataSetChanged")
class AccountSecretaryAdapter(
    val context: Context,
    private val activity: FragmentActivity,
    private var list: ArrayList<UserSecretary>
) : RecyclerView.Adapter<AccountSecretaryAdapter.ViewHolder>() {

    private lateinit var user: UserSecretary
    private val isConnected = NetworkConnection(context)
    private var repository = Repository()
    private var viewModelFactory = MainViewModelFactory(repository)
    private var viewModel = ViewModelProvider(
        context as ViewModelStoreOwner,
        viewModelFactory
    )[MainViewModel::class.java]

    private var exId: Int = 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val email: TextView = itemView.findViewById(R.id.email)
        val delete: ImageButton = itemView.findViewById(R.id.delete)
        val edit: ImageButton = itemView.findViewById(R.id.edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_account, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        user = list[position]
        holder.name.text = user.name
        holder.email.text = user.email

        holder.edit.setOnClickListener {
            user = list[position]
            val i = Intent(context, EditSecretary::class.java)
            i.putExtra("id", user.id)
            i.putExtra("name", user.name)
            i.putExtra("email", user.email)
            i.putExtra("pass", user.password)
            context.startActivity(i)
            activity.finish()
        }

        holder.delete.setOnClickListener {
            if (isConnected.isInternetAvailable()) {
                user = list[position]
                exId = user.id

                viewModel.deleteSecretary(user.id)
            } else {
                Toast.makeText(context, R.string.no_connection, Toast.LENGTH_LONG).show()
            }
        }
    }

    init {
        viewModel.updateAndDeleteSecretaryResponse.observe(context as LifecycleOwner) { response ->
            if (response.isSuccessful) {
                val res = response.body()!!

                if (res.message == "User deleted.") {
                    Toast.makeText(context, "تم حذف المستخدم", Toast.LENGTH_SHORT).show()

                    list.remove(user)
                    notifyDataSetChanged()
                } else if (res.message == "User not deleted.") {
                    Toast.makeText(context, "لم يتم حذف المستخدم", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterList: ArrayList<UserSecretary>) {
        list = filterList
        notifyDataSetChanged()
    }
}