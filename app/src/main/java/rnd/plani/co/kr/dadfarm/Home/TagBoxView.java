package rnd.plani.co.kr.dadfarm.Home;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-22.
 */
public class TagBoxView extends FrameLayout {
    TextView contentView;
    public TagBoxView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_tag_box,this);
        contentView = (TextView) findViewById(R.id.text_content);
    }

    public TagBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_tag_box,this);
        contentView = (TextView) findViewById(R.id.text_content);
    }

    public void setContent(String content){
        contentView.setText(content);
    }
}
