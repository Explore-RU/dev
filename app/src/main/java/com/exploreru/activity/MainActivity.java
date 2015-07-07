package com.exploreru.activity;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.exploreru.R;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the ViewPager which will hold all of the fragment activities
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        //The FragmentParser will take in and keep track of all of the fragments and tab titles
        FragmentParser frag = new FragmentParser();

        //ADD FRAGMENT PAGES (ACTIVITIES) HERE:
        frag.addFragment(DiningFragment.newInstance(), "Dining");
        frag.addFragment(BlankFragment.newInstance(2), "Tab 2");
        frag.addFragment(BlankFragment.newInstance(3), "Tab 3");
        frag.addFragment(BlankFragment.newInstance(4), "Tab 4");

        //Create the FragmentPagerAdapter from the FragmentParser to generate tabs and pass it to the ViewPager to link the tabs
        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this, frag));

        // Get the TabLayout and give the TabLayout the ViewPager to generate the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        applyFontedTab(this, viewPager, tabLayout);


    }

    public static void applyFontedTab(Activity activity, ViewPager viewPager, TabLayout tabLayout) {
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            LinearLayout l = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.tab, null);
            TextView tv = (TextView)l.findViewById(R.id.tab_view);
            tv.setText(viewPager.getAdapter().getPageTitle(i));
            tabLayout.getTabAt(i).setCustomView(l);

        }
    }

}
