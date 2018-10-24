package jp.ac.x16g023chiba_fjb.spareplanning;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements View.OnClickListener {


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button2).setOnClickListener(this);
        TextView textView = view.findViewById(R.id.TitleText);
        textView.setText(String.valueOf(((MainActivity)getActivity()).getReHour()));
    }

    @Override
    public void onClick(View v) {
       // getView().findViewById()
//        ((MainActivity)getActivity()).changeFragment(CategoryFragment.class);
    }
}
