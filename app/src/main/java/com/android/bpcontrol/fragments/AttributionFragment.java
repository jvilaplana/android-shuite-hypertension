package com.android.bpcontrol.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.bpcontrol.HomeActivity;
import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.RobotoTextView;

/**
 * Created by Adrian on 26/3/15.
 */
public class AttributionFragment extends Fragment {

    private RobotoTextView attributions1;
    private RobotoTextView attributions12;
    private RobotoTextView attributions13;
    private RobotoTextView attributions14;
    private RobotoTextView attributions15;

    public static AttributionFragment getNewInstance(){

        AttributionFragment attributionFragment = new AttributionFragment();
        return attributionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.attributionlayout, null);
        attributions1 = (RobotoTextView) view.findViewById(R.id.attributions1);
        attributions12 = (RobotoTextView) view.findViewById(R.id.attributions12);
        attributions13 = (RobotoTextView) view.findViewById(R.id.attributions13);
        attributions14 = (RobotoTextView) view.findViewById(R.id.attributions14);
        attributions15 = (RobotoTextView) view.findViewById(R.id.attributions15);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] attributtionparts = new String[4];

        final String amitjakhuurlurl = getResources().getString(R.string.amitjakhuurl);
        final String principalmenuiconmenssage = getResources().getString(R.string.principalmenuiconmenssage);
        final String principalmenuiconmenssage2 = getResources().getString(R.string.principalmenuiconmenssage2);
        final String flaticon = getResources().getString(R.string.flaticonsurl);

        attributtionparts[0] = principalmenuiconmenssage;
        attributtionparts[1] = amitjakhuurlurl;
        attributtionparts[2] = principalmenuiconmenssage2;
        attributtionparts[3] = flaticon;

        prepareStylishText(attributions1,attributtionparts);

        final String icons8url = getResources().getString(R.string.icons8url);
        final String maptypeicon = getResources().getString(R.string.maptypeicons);
        final String maptypeicon2 = getResources().getString(R.string.maptypeicons2);

        attributtionparts[0] = maptypeicon;
        attributtionparts[1] = icons8url;
        attributtionparts[2] = maptypeicon2;
        attributtionparts[3] = flaticon;

        prepareStylishText(attributions12,attributtionparts);


        final String freepickicon = getResources().getString(R.string.freepikurl);
        final String lateralmenu = getResources().getString(R.string.maptypeicons);
        final String lateralmenu2 = getResources().getString(R.string.maptypeicons2);

        attributtionparts[0] = lateralmenu;
        attributtionparts[1] = freepickicon;
        attributtionparts[2] = lateralmenu2;
        attributtionparts[3] = flaticon;

        prepareStylishText(attributions13,attributtionparts);


        final String mensajesauthorurl = getResources().getString(R.string.mensajesautor);
        final String mensajes1 = getResources().getString(R.string.mensajes1);
        final String mensajes2 = getResources().getString(R.string.mensajes2);
        final String telegramrepo = getResources().getString(R.string.githuburl);

        attributtionparts[0] = mensajes1;
        attributtionparts[1] = mensajesauthorurl;
        attributtionparts[2] = mensajes2;
        attributtionparts[3] = telegramrepo;

        prepareStylishText(attributions14,attributtionparts);

        final String others = getResources().getString(R.string.lateral);

        attributions15.setText(others);



    }

    private void prepareStylishText (TextView view, String[] parts  ) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(parts[0]);

        spanTxt.append(" "+parts[1]);

        spanTxt.setSpan(new MyCustomSpannable(getActivity(),parts[1]),
                parts[0].length()+1, spanTxt.length(), 0 );

        spanTxt.append(" "+parts[2]);


        spanTxt.append(" "+parts[3]);
        spanTxt.setSpan(new MyCustomSpannable(getActivity(),parts[3]),
                spanTxt.length() - parts[3].length(), spanTxt.length(), 0 );

        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);

    }

    private static class MyCustomSpannable extends ClickableSpan{

        private HomeActivity context;
        private String executeUrl="";

        public MyCustomSpannable(Context context,String url){
            this.context = (HomeActivity)context;
            this.executeUrl = url;
        }

        @Override
        public void onClick(View widget) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+executeUrl));
            context.startActivity(browserIntent);
        }
    }


}
