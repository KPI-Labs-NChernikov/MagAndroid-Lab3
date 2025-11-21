package dev.nikita_chernikov.lab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class ClassmateListViewAdapter(private val classmates: ArrayList<Classmate>) : RecyclerView.Adapter<ClassmateListViewAdapter.ViewHolderClass>() {
    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fullNameTextView: TextView = itemView.findViewById(R.id.id_full_name)
        val createdAtTextView: TextView = itemView.findViewById(R.id.created_at)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.classmate_item_layout, parent, false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentClassmate = classmates[position]

        holder.fullNameTextView.text = holder.itemView.context.getString(
            R.string.id_full_name_text,
            currentClassmate.id,
            currentClassmate.fullName
        )
        holder.createdAtTextView.text = holder.itemView.context.getString(
            R.string.created_at_text,
            SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(currentClassmate.createdAt)
        )
    }

    override fun getItemCount(): Int {
        return classmates.size
    }
}
