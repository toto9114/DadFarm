package rnd.plani.co.kr.dadfarm.Certification;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import rnd.plani.co.kr.dadfarm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsertCertFragment extends Fragment {


    public InsertCertFragment() {
        // Required empty public constructor
    }

    public static final String EXTRA_PHONE_NUMBER = "phone" ;

    private String phone;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phone = getArguments().getString(EXTRA_PHONE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert_cert, container, false);
        ((RecieveCertificationActivity)getActivity()).setToolbar("번호 수정하기", phone);

        Button btn = (Button)view.findViewById(R.id.btn_continue);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RecieveCertificationActivity)getActivity()).changeInsertName();
            }
        });

        return view;
    }
}
