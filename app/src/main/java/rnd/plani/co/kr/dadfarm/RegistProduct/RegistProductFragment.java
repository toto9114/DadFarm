package rnd.plani.co.kr.dadfarm.RegistProduct;


import android.content.Intent;
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
public class RegistProductFragment extends Fragment {


    public RegistProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_regist_product, container, false);

        Button btn = (Button) view.findViewById(R.id.btn_regist);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),RegistProductInfoActivity.class));
            }
        });
        return view;
    }
}
