package com.norvera.confession.ui.guide

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norvera.confession.data.models.GuideEntry
import com.norvera.confession.databinding.FragmentGuideEntryBinding

class GuideListFragmentAdapter internal constructor() :
    ListAdapter<GuideEntry, GuideListFragmentAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val binding = FragmentGuideEntryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val guideEntry = getItem(position)
        holder.onBind(createOnClickListener(guideEntry.id, guideEntry), guideEntry)

    }

    private fun createOnClickListener(id: Long, guideEntry: GuideEntry): View.OnClickListener {
        return View.OnClickListener{
            val toGuideFragmentDetail =
                GuideListFragmentDirections.actionGuideFragmentListToGuideFragmentDetail(guideEntry.guideTitle, guideEntry)
            findNavController(it).navigate(toGuideFragmentDetail)
        }
    }

    inner class ViewHolder(private val binding: FragmentGuideEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(clickListener: View.OnClickListener, item: GuideEntry) {
            itemView.tag = item
            binding.clickListener = clickListener
            binding.guideEntry = item
            binding.executePendingBindings()


        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GuideEntry>() {
            override fun areItemsTheSame(guideEntry: GuideEntry, t1: GuideEntry): Boolean {
                return guideEntry.id == t1.id
            }

            override fun areContentsTheSame(guideEntry: GuideEntry, t1: GuideEntry): Boolean {
                return guideEntry == t1
            }
        }
    }
}
