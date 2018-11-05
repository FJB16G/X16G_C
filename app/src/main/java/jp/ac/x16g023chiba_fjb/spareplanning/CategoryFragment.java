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

//import jp.ac.chiba_fjb.x16g_c.test09202.R;


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
        //開発例
        view.findViewById(R.id.coffie).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //getView().findViewById();
        //((MainActivity)getActivity()).changeFragment(遷移先フラグメント名.class);

        if (v.getId() == R.id.coffie) {
            ((MainActivity) getActivity()).setSearchText("convenience_store");
            ((MainActivity) getActivity()).changeFragment(MapViewFragment.class);
        }/*else if(v.getID()==R.id){
            ・
            ・
            ・
        }
         */
    }
}
