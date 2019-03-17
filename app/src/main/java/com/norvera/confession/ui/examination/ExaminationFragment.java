package com.norvera.confession.ui.examination;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.R;
import com.norvera.confession.data.models.User;
import com.norvera.confession.databinding.FragmentExaminationentryListBinding;
import com.norvera.confession.ui.main.MainViewModel;
import com.norvera.confession.ui.main.MainViewModelFactory;
import com.norvera.confession.utils.InjectorUtils;
import com.norvera.confession.utils.SharedPreferencesHelper;

import org.jetbrains.annotations.NotNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;


/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class ExaminationFragment extends Fragment {

    private MainViewModel mViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExaminationFragment() {
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        long commandmentId = ExaminationFragmentArgs.fromBundle(getArguments()).getCommandmentId();
        FragmentExaminationentryListBinding binding = FragmentExaminationentryListBinding.inflate(inflater, container, false);

        binding.setLifecycleOwner(this);
        // todo refactor
        setupViewModel(requireActivity());

        ExaminationEntryAdapter adapter = new ExaminationEntryAdapter(mViewModel);
        binding.rvExamination.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), VERTICAL);
        binding.rvExamination.addItemDecoration(decoration);

        subscribeUi(adapter, commandmentId);

        return binding.getRoot();

    }

    private void subscribeUi(ExaminationEntryAdapter adapter, long commandmentId) {
        String vocation = SharedPreferencesHelper.getSharedPreferenceString(getContext(), getString(R.string.pref_vocation_key), "0");
        String age = SharedPreferencesHelper.getSharedPreferenceString(getContext(), getString(R.string.pref_age_key), "0");
        String gender = SharedPreferencesHelper.getSharedPreferenceString(getContext(), getString(R.string.pref_gender_key), "0");

        //todo refactor raw sql query out of user class
        User user = new User(Integer.valueOf(vocation), Integer.valueOf(age), Integer.valueOf(gender));

        mViewModel.allExaminationsForCommandmentAndUser(user.getUserSelectionForCommandment(commandmentId)).observe(this, examinationEntries -> {
            mViewModel.examinationEntries.set(examinationEntries);
            adapter.submitList(mViewModel.examinationEntries.get());
        });
    }

    private void setupViewModel(Context context) {
        MainViewModelFactory factory = InjectorUtils.provideViewModelFactory(context);
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(MainViewModel.class);
    }


}
