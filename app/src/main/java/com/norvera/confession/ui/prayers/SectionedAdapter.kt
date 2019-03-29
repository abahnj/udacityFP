package com.norvera.confession.ui.prayers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.norvera.confession.ListAdapterWithHeader
import com.norvera.confession.R
import com.norvera.confession.data.models.PrayersEntry
import com.norvera.confession.databinding.FragmentPrayerItemBinding
import com.norvera.confession.databinding.ListHeaderBinding

class SectionedAdapter internal constructor() :
    ListAdapterWithHeader<PrayersEntry, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER_VIEW_TYPE) {
            val binding =
                ListHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HeaderViewHolder(binding)
        } else {
            val binding = FragmentPrayerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            HEADER_VIEW_TYPE -> {
                (holder as HeaderViewHolder).onBind(position)
            }
            LIST_VIEW_TYPE -> {
                (holder as ViewHolder).onBind(
                    createOnClickListener(getItem(position)),
                    getItem(position)
                )
            }
            else -> {

            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (HEADER_POSITION.any { it == position }) {
            HEADER_VIEW_TYPE
        } else {
            LIST_VIEW_TYPE
        }

    }

    private fun createOnClickListener(prayerEntry: PrayersEntry): View.OnClickListener {

        return View.OnClickListener { view ->
            val toPrayerFragmentDetail =
                PrayerListDirections.actionPrayerFragmentListToPrayerFragmentDetail(
                    prayerEntry.prayerName,
                    prayerEntry
                )

            findNavController(view).navigate(toPrayerFragmentDetail)
        }

    }

    inner class ViewHolder internal constructor(private val binding: FragmentPrayerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun onBind(clickListener: View.OnClickListener, item: PrayersEntry) {
            binding.prayerEntry = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    inner class HeaderViewHolder internal constructor(private val binding: ListHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val context: Context? = binding.root.context

        internal fun onBind(position: Int) {
            //todo move to string resource
            binding.sectionHeader.text =
                if (position == 0) context?.getString(R.string.act_of_contrition) else context?.getString(
                    R.string.traditional_prayers
                )
            binding.executePendingBindings()
        }
    }


    companion object {

        private val HEADER_POSITION = intArrayOf(0, 7)
        const val HEADER_VIEW_TYPE = R.layout.list_header
        const val LIST_VIEW_TYPE = R.layout.fragment_prayer_item

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PrayersEntry>() {
            override fun areItemsTheSame(PrayersEntry: PrayersEntry, t1: PrayersEntry): Boolean {
                return PrayersEntry.id == t1.id
            }

            override fun areContentsTheSame(PrayersEntry: PrayersEntry, t1: PrayersEntry): Boolean {
                return PrayersEntry == t1
            }
        }
    }

}
