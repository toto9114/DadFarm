package rnd.plani.co.kr.dadfarm.Certification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;
import rnd.plani.co.kr.dadfarm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecieveCertFragment extends Fragment {


    public RecieveCertFragment() {
        // Required empty public constructor
    }

    EditText phoneView;
    TextView alertView;
    View underLine;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recieve_cert, container, false);

        phoneView = (EditText) view.findViewById(R.id.edit_phone);
        alertView = (TextView) view.findViewById(R.id.text_alert);
        underLine = (View) view.findViewById(R.id.under_line);
        Button btn = (Button) view.findViewById(R.id.btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneView.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    underLine.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.alert_under_line));
                    alertView.setVisibility(View.VISIBLE);
                    alertView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
                    return;
                }
                PropertyManager.getInstance().setPhoneNum(phone);
                ((RecieveCertificationActivity)getActivity()).changeInsertCert(phone);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((RecieveCertificationActivity)getActivity()).setToolbar("취소",getString(R.string.app_name));
    }
}
