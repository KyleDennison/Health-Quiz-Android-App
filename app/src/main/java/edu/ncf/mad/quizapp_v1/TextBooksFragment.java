package edu.ncf.mad.quizapp_v1;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class TextBooksFragment extends Fragment {

    WebView webView;
    TextView chooseBook;

    Button book1;
    Button book2;
    Button book3;
    Button book4;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_textbooks, container, false);

        webView = rootView.findViewById(R.id.WebView);
        chooseBook = rootView.findViewById(R.id.chooseBook);

        book1 = rootView.findViewById(R.id.book1btn);
        book2 = rootView.findViewById(R.id.book2btn);
        book3 = rootView.findViewById(R.id.book3btn);
        book4 = rootView.findViewById(R.id.book4btn);


        book1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillWebview("file:///android_asset/Staying_Healthy_For_Beginners/shfb_html/shfb-html.html");
            }
        });

        book2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillWebview("file:///android_asset/Staying_Healthy/CompleteSEBook_html/CompleteSEBook-html.html");
            }
        });

        book3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillWebview("file:///android_asset/Womens_Health/WomensHealthSE_html/WomensHealthSE-html.html");
            }
        });

        book4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillWebview("file:///android_asset/Coping_With_Stress/StressSE_html/StressSE-html.html");
            }
        });

        return rootView;

    }

    public void fillWebview(String file) {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        webView.loadUrl(file);

        book1.setVisibility(View.GONE);
        book2.setVisibility(View.GONE);
        book3.setVisibility(View.GONE);
        book4.setVisibility(View.GONE);
        chooseBook.setVisibility(View.GONE);
    }
}




