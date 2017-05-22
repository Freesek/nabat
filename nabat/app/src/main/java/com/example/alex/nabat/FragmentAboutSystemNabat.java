package com.example.alex.nabat;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.StringRes;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAboutSystemNabat extends Fragment {


    public FragmentAboutSystemNabat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about_system_nabat, container, false);
        TextView whatNabat = (TextView) rootView.findViewById(R.id.what_nabat);
        TextView whoNabat = (TextView) rootView.findViewById(R.id.who_nabat);
        whoNabat.setTypeface(null, Typeface.BOLD);
        whatNabat.setTypeface(null, Typeface.BOLD);
        TextView textView = (TextView) rootView.findViewById(R.id.hyper_link_about_nabat);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href = 'http://www.ombudsmanbiz.ru'> "+"Уполномоченного при Президенте РФ по защите прав предпринимателей Бориса Юрьевича Титова"+" </a>";
        textView.setText(Html.fromHtml(text));
        textView.setHighlightColor(Color.TRANSPARENT);
        return rootView;
    }

}
