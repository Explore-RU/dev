package com.exploreru.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.exploreru.R;
import com.exploreru.net.DatabaseQuery;
import com.exploreru.net.JSONParser;

// In this case, the fragment displays simple text based on the page
public class BlankFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    DatabaseQuery q = new DatabaseQuery("admin","sqldb68","SELECT * FROM Test.test");

    public static BlankFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        BlankFragment fragment = new BlankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        q.start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView textView = (TextView) view;
        try {
            q.join();
        }catch(Exception e){
            e.printStackTrace();
        }
        textView.setText(JSONParser.parseBlankFrag(q.result));
        //textView.setText("Fragment #" + mPage);
        return view;
    }
}