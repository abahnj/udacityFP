package com.norvera.confession.ui.confession;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.R;
import com.norvera.confession.databinding.FragmentConfessionThreeBinding;
import com.norvera.confession.ui.main.MainViewModel;
import com.norvera.confession.ui.main.MainViewModelFactory;
import com.norvera.confession.utils.InjectorUtils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfessionFragmentThree extends Fragment {

    private static final String KEY_TITLE = "title";
    private static final String KEY_INSPIRATION = "inspiration";

    private MainViewModel mViewModel;

    public ConfessionFragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setupViewModel(getContext());

        // Inflate the layout for this fragment
        FragmentConfessionThreeBinding binding = FragmentConfessionThreeBinding.inflate(inflater, container, false);

        binding.btnFinish.setOnClickListener(v -> displayDialog());
        return binding.getRoot();
    }

    private void setupViewModel(Context context) {
        MainViewModelFactory factory = InjectorUtils.provideViewModelFactory(context);
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }


    private void displayDialog() {
        mViewModel.getInspirationForId(ThreadLocalRandom.current().nextInt(1, 20)).observe(this, inspirationEntry -> {
            DialogFragment newFragment = new InspirationsDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_TITLE, inspirationEntry.author);
            bundle.putString(KEY_INSPIRATION, inspirationEntry.text);
            newFragment.setArguments(bundle);
            newFragment.show(getFragmentManager(), "inspiration");
        });

    }

    public static class InspirationsDialogFragment extends DialogFragment {


        @NotNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            Bundle bundle = getArguments();
            String title = bundle.getString(ConfessionFragmentThree.KEY_TITLE);
            String inspiration = bundle.getString(ConfessionFragmentThree.KEY_INSPIRATION);

            builder.setTitle(title).setMessage(inspiration)
                    .setPositiveButton(R.string.finish_confession, this::goHome);
            // Create the AlertDialog object and return it
            return builder.create();
        }

        @Override
        public void onDismiss(@NonNull DialogInterface dialog) {
            super.onDismiss(dialog);
            goHome(dialog, 1);
        }

        private void goHome(DialogInterface dialog, int which) {
            NavHostFragment.findNavController(getParentFragment()).navigate(R.id.commandment_fragment);
        }
    }


}
