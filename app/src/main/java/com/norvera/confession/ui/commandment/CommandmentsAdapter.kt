package com.norvera.confession.ui.commandment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norvera.confession.data.models.CommandmentEntry
import com.norvera.confession.databinding.FragmentCommandmentsBinding

/**
 * [RecyclerView.Adapter] that can display a [CommandmentEntry] and makes a call to the
 */
internal class CommandmentsAdapter internal constructor() :
    ListAdapter<CommandmentEntry, CommandmentsAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentCommandmentsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commandmentEntry = getItem(position)
        holder.onBind(createOnClickListener(commandmentEntry.id), commandmentEntry)
    }


    private fun createOnClickListener(commandmentId: Long): View.OnClickListener {

        return View.OnClickListener {
            val commandmentsFragmentDirections =
                CommandmentsFragmentDirections.commandmentFragmentToExaminationFragment(
                    commandmentId
                )
            Navigation.findNavController(it).navigate(commandmentsFragmentDirections)
        }

    }

    internal inner class ViewHolder(var binding: FragmentCommandmentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(clickListener: View.OnClickListener, item: CommandmentEntry) {
            itemView.tag = item
            binding.clickListener = clickListener
            binding.commandmentEntry = item
            binding.executePendingBindings()
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommandmentEntry>() {
            override fun areItemsTheSame(
                commandmentEntry: CommandmentEntry,
                t1: CommandmentEntry
            ): Boolean {
                return commandmentEntry.id == t1.id
            }

            override fun areContentsTheSame(
                commandmentEntry: CommandmentEntry,
                t1: CommandmentEntry
            ): Boolean {
                return commandmentEntry == t1
            }
        }
    }

}
