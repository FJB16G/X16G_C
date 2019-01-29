package jp.ac.x16g023chiba_fjb.spareplanning;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenreCustomFragment extends Fragment implements View.OnClickListener {





    public GenreCustomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genre_custom, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.library).setOnClickListener(this);
        view.findViewById(R.id.bookstore).setOnClickListener(this);
        view.findViewById(R.id.clothshop).setOnClickListener(this);
        view.findViewById(R.id.museum).setOnClickListener(this);
        view.findViewById(R.id.park).setOnClickListener(this);
        view.findViewById(R.id.rentalshop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bookstore){
            ((MainActivity)getActivity()).setSearchText("book_store");
        }else if (v.getId() == R.id.library){
            ((MainActivity)getActivity()).setSearchText("library");
        }else if (v.getId() == R.id.clothshop){
            ((MainActivity)getActivity()).setSearchText("clothing_store");
        }else if (v.getId() == R.id.museum){
            ((MainActivity)getActivity()).setSearchText("museum");
        }else if (v.getId() == R.id.park){
            ((MainActivity)getActivity()).setSearchText("park");
        }else if (v.getId() == R.id.rentalshop){
            ((MainActivity)getActivity()).setSearchText("movie_rental");
        }
        ((MainActivity)getActivity()).changeFragment(MapViewFragment2.class);
    }
}
