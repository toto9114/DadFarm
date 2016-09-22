package rnd.plani.co.kr.dadfarm.Certification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import rnd.plani.co.kr.dadfarm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFriendsFragment extends Fragment {


    public FindFriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_friends, container, false);

        Button btn = (Button)view.findViewById(R.id.btn_find);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RecieveCertificationActivity)getActivity()).changeGetContact();
            }
        });
        return view;
    }

}
