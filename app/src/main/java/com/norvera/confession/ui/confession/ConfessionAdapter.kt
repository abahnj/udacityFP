package com.norvera.confession.ui.confession

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norvera.confession.data.models.ExaminationActiveEntry
import com.norvera.confession.databinding.FragmentConfessionEntryBinding

internal class ConfessionAdapter internal constructor() :
    ListAdapter<ExaminationActiveEntry, ConfessionAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentConfessionEntryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val examinationEntry = getItem(position)
        holder.onBind(examinationEntry)
    }


    internal inner class ViewHolder(private val binding: FragmentConfessionEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ExaminationActiveEntry) {
            itemView.tag = item
            binding.examinationEntry = item
            binding.executePendingBindings()

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ExaminationActiveEntry>() {
            override fun areItemsTheSame(
                ExaminationActiveEntry: ExaminationActiveEntry,
                t1: ExaminationActiveEntry
            ): Boolean {
                return ExaminationActiveEntry.id == t1.id
            }

            override fun areContentsTheSame(
                ExaminationActiveEntry: ExaminationActiveEntry,
                t1: ExaminationActiveEntry
            ): Boolean {
                return ExaminationActiveEntry == t1
            }
        }
    }
}
