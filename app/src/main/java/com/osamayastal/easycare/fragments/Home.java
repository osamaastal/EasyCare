package com.osamayastal.easycare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.github.islamkhsh.CardSliderViewPager;
import com.osamayastal.easycare.R;
import com.osamayastal.easycare.classes.items.Card;
import com.osamayastal.easycare.classes.adapters.CardAdapter;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ArrayList<Card> movies = new ArrayList<>();
        // add items to arraylist
        movies.add(new Card());
        movies.add(new Card());
        movies.add(new Card());
        movies.add(new Card());
        movies.add(new Card());
        CardSliderViewPager cardSliderViewPager = view.findViewById(R.id.viewPager);
        cardSliderViewPager.setAdapter(new CardAdapter(movies));
        show_bottomSheet();
        return view;
    }

    private void show_bottomSheet(){
        RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View sheetView = inflater.inflate(R.layout.bottom_sheet_choose_city, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }
}
