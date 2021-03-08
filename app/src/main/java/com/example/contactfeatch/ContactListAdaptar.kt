package com.example.contactfeatch

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_contact_list.view.*
import java.util.ArrayList

class ContactListAdaptar(private var contactList: ArrayList<Contact>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var requestFilterList = ArrayList<Contact>()
    lateinit var mcontext: Context


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        requestFilterList = contactList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val atmListView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_contact_list, parent, false)
        val sch = MyViewHolder(atmListView)
        mcontext = parent.context
        return sch
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = requestFilterList[position]
        holder.itemView.tv_contact_name.text = currentItem.name.toString()
        holder.itemView.tv_contact_phone.text = currentItem.phone.toString()


    }

    override fun getItemCount(): Int {
        return requestFilterList.size
        notifyDataSetChanged()
        Log.e("atmlistValue--->", requestFilterList.size.toString())
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                if (constraint.toString().isEmpty()) {
                    requestFilterList = contactList
                } else {


                    val resultList = ArrayList<Contact>()
                    for (row in contactList) {
                        if (
                            row.phone.toString().toLowerCase()
                                ?.contains(constraint.toString().toLowerCase()) or
                            row.name.toString().toLowerCase()
                                ?.contains(constraint.toString().toLowerCase())
                        ) {
                            resultList.add(row)
                        }
                    }
                    requestFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = requestFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                requestFilterList = results?.values as ArrayList<Contact>
                notifyDataSetChanged()
            }

        }
    }
}