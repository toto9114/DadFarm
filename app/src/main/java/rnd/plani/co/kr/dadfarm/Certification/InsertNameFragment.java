package rnd.plani.co.kr.dadfarm.Certification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;
import rnd.plani.co.kr.dadfarm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsertNameFragment extends Fragment {


    public InsertNameFragment() {
        // Required empty public constructor
    }

    EditText firstNameView, lastNameView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert_name, container, false);
        firstNameView = (EditText) view.findViewById(R.id.edit_first_name);
        lastNameView = (EditText) view.findViewById(R.id.edit_last_name);

        Button btn = (Button) view.findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameView.getText().toString();
                String lastName = lastNameView.getText().toString();
                if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)){
                    PropertyManager.getInstance().setFirstName(firstName);
                    PropertyManager.getInstance().setLastName(lastName);
                    ((RecieveCertificationActivity) getActivity()).changeFindFriends();
                }
            }
        });
        return view;
    }

}
