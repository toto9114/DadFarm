package rnd.plani.co.kr.dadfarm;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareDialog extends DialogFragment {


    public ShareDialog() {
        // Required empty public constructor
    }

    LinearLayout facebook, kakao, band;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog_share, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        facebook = (LinearLayout) view.findViewById(R.id.linear_facebook);
        kakao = (LinearLayout) view.findViewById(R.id.linear_kakao);
        band = (LinearLayout) view.findViewById(R.id.linear_band);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        band.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

}
