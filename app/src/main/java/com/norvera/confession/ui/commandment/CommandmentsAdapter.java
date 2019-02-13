package com.norvera.confession.ui.commandment;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.norvera.confession.R;
import com.norvera.confession.data.models.CommandmentEntry;
import com.norvera.confession.ui.main.dummy.DummyContent.DummyItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link CommandmentsFragment.CommandmentClickListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CommandmentsAdapter extends ListAdapter<CommandmentEntry, CommandmentsAdapter.ViewHolder> {

    private final CommandmentsFragment.CommandmentClickListener mListener;

    /*public CommandmentsAdapter(List<DummyItem> items, CommandmentClickListener listener) {
        super(DIFF_CALLBACK);
        mValues = items;
        mListener = listener;
    }*/

    private static final DiffUtil.ItemCallback<CommandmentEntry> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CommandmentEntry>() {
                @Override
                public boolean areItemsTheSame(@NonNull CommandmentEntry commandmentEntry, @NonNull CommandmentEntry t1) {
                    return (commandmentEntry._id == t1._id);
                }

                @Override
                public boolean areContentsTheSame(@NonNull CommandmentEntry commandmentEntry, @NonNull CommandmentEntry t1) {
                    return commandmentEntry.equals(t1);
                }
            };


    protected CommandmentsAdapter(CommandmentsFragment.CommandmentClickListener listener) {
        super(DIFF_CALLBACK);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_commandments, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.onBind(position);
    }


    public static String getNumberOrdinal(Number number) {
        if (number == null) {
            return null;
        }

        //todo move to string resource
        String format = "Sins against the {0,ordinal} commandment";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return android.icu.text.MessageFormat.format(format, number);
        } else {
            return com.ibm.icu.text.MessageFormat.format(format, number);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_commandment_title)
        TextView mTvCommandmentTitle;

        @BindView(R.id.tv_commandment_description)
        TextView mTvCommandmentDescription;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void onBind(final int position) {

            final CommandmentEntry commandmentEntry = getItem(position);

            if (commandmentEntry.commandment != null ) {
                if (commandmentEntry._id < 11)
                    mTvCommandmentDescription.setText(commandmentEntry.text);
                else
                    mTvCommandmentDescription.setText(commandmentEntry.commandment);
            }

            mTvCommandmentTitle.setText(getNumberOrdinal((commandmentEntry._id)));


        }

        @OnClick
        void onClick(View view) {
            if (mListener != null)
                mListener.onListFragmentInteraction(getItem(getAdapterPosition()));
        }
    }

}
