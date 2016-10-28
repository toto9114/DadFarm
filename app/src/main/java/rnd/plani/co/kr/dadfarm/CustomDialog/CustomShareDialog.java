package rnd.plani.co.kr.dadfarm.CustomDialog;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import rnd.plani.co.kr.dadfarm.MyApplication;
import rnd.plani.co.kr.dadfarm.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomShareDialog extends DialogFragment {

    public static final String EXTRA_LINK_URL = "link";

    private static final int TYPE_FACEBOOK = 1;
    private static final int TYPE_KAKAO = 2;
    private static final int TYPE_BAND = 3;

    public CustomShareDialog() {
        // Required empty public constructor
    }

    LinearLayout facebook, kakao, band;
    KakaoLink kakaoLink;
    KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;

    String linkUrl = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            linkUrl = getArguments().getString(EXTRA_LINK_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog_share, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        facebook = (LinearLayout) view.findViewById(R.id.linear_facebook);
        kakao = (LinearLayout) view.findViewById(R.id.linear_kakao);
        band = (LinearLayout) view.findViewById(R.id.linear_band);

        try {
            kakaoLink = KakaoLink.getKakaoLink(MyApplication.getContext());
            kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .build();
                ShareDialog shareDialog = new ShareDialog(getActivity());
                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
                dismiss();
            }
        });

        kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    kakaoTalkLinkMessageBuilder.addText("test").build();
                    kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, getContext());
                } catch (KakaoParameterException e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        });

        band.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager manager = getContext().getPackageManager();
                Intent i = manager.getLaunchIntentForPackage("com.nhn.android.band");

                String serviceDomain = "www.bloter.net"; //  연동 서비스 도메인
                String encodedText = "%ED%85%8C%EC%8A%A4%ED%8A%B8+%EB%B3%B8%EB%AC%B8"; // 글 본문 (utf-8 urlencoded)
                Uri uri = Uri.parse("bandapp://create/post?text=" + encodedText + "&route=" + serviceDomain);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getActivity().startActivity(intent);
                dismiss();
            }
        });
        return view;
    }

}
