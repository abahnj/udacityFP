package com.norvera.confession.ui.examination


import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norvera.confession.MainViewModel
import com.norvera.confession.R
import com.norvera.confession.data.models.ExaminationEntry
import com.norvera.confession.databinding.FragmentExaminationentryBinding
import com.norvera.confession.ui.examination.ExaminationEntryAdapter.ViewHolder

/**
 * [ListAdapter] that can display a [ExaminationEntry] and makes a call to the
 * specified [View.OnClickListener].
 */
internal class ExaminationEntryAdapter internal constructor(private val viewModel: MainViewModel) :
    ListAdapter<ExaminationEntry, ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentExaminationentryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val examinationEntry = getItem(position)
        holder.onBind(
            createOnClickListener(examinationEntry.id, examinationEntry),
            examinationEntry
        )
    }

    private fun createOnClickListener(
        examinationId: Long,
        examinationEntry: ExaminationEntry
    ): View.OnClickListener {

        return View.OnClickListener{
            examinationEntry.count = examinationEntry.count + 1
            viewModel.updateCountForEntry(examinationEntry)
            /* CommandmentsFragmentDirections.CommandmentFragmentToExaminationFragment commandmentsFragmentDirections =
                    CommandmentsFragmentDirections.commandmentFragmentToExaminationFragment(commandmentId);
            Navigation.findNavController(view).navigate(commandmentsFragmentDirections);
            */
        }

    }

    internal inner class ViewHolder(private val binding: FragmentExaminationentryBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {
        private lateinit var examinationEntry: ExaminationEntry

        //Add an OnMenuItem Listener to execute commands OnClick of context menu task
        private val onMenuClick = MenuItem.OnMenuItemClickListener{ item ->

            when (item.itemId) {
                DECREMENT -> if (examinationEntry.count > 0) {
                    examinationEntry.count = examinationEntry.count - 1
                    viewModel.decrementCount(examinationEntry)
                }
                EDIT -> {
                    examinationEntry.description = ""
                }

                DELETE -> {
                }
                RESET_COUNT -> {
                }
            }//Do stuff
            //Do stuff
            true
        }


        init {
            binding.root.setOnCreateContextMenuListener(this) //Register OnCreate Menu Listener

        }

        fun onBind(clickListener: View.OnClickListener, item: ExaminationEntry) {
            this.examinationEntry = item
            itemView.tag = item
            binding.executePendingBindings()
            binding.clickListener = clickListener
            binding.examinationEntry = item
            binding.executePendingBindings()

            /*if (commandmentEntry.commandment != null ) {
                if (commandmentEntry.id < 11)
                    mTvCommandmentDescription.setText(commandmentEntry.text);
                else
                    mTvCommandmentDescription.setText(commandmentEntry.commandment);
            }

            mTvCommandmentTitle.setText(getNumberOrdinal((commandmentEntry.id)));

*/
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {


                val decrement = menu?.add(Menu.NONE, DECREMENT, Menu.FIRST, R.string.context_menu_count)
                val edit = menu?.add(Menu.NONE, EDIT, Menu.FIRST, R.string.context_menu_edit)
                val delete = menu?.add(Menu.NONE, DELETE, Menu.FIRST, R.string.context_menu_delete)
                val resetCount = menu?.add(Menu.NONE, RESET_COUNT, Menu.FIRST, R.string.context_menu_restore)


            edit?.setOnMenuItemClickListener(onMenuClick)
            delete?.setOnMenuItemClickListener(onMenuClick)
                decrement?.setOnMenuItemClickListener(onMenuClick)
                resetCount?.setOnMenuItemClickListener(onMenuClick)


        }
    }

    companion object {

        private const val DECREMENT = 1
        private const val EDIT = 2
        private const val DELETE = 3
        private const val RESET_COUNT = 4

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ExaminationEntry>() {
            override fun areItemsTheSame(
                examinationEntry: ExaminationEntry,
                t1: ExaminationEntry
            ): Boolean {
                return examinationEntry.id == t1.id
            }

            override fun areContentsTheSame(
                examinationEntry: ExaminationEntry,
                t1: ExaminationEntry
            ): Boolean {
                return examinationEntry == t1
            }
        }
    }

}


