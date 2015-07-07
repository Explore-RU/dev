package com.exploreru.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAM on 6/19/2015.
 */
public class FragmentParser {

    List<Fragment> fragarray = new ArrayList<Fragment>();
    List<String> fragtitles = new ArrayList<String>();

    public List<Fragment> getFragmentArray(){
        return fragarray;
    }

    public void addFragment(Fragment f, String title){
        fragarray.add(f);
        fragtitles.add(title);
    }

    public int getFragmentCount(){
        return fragarray.size();
    }

    public List<String> getFragmentTitleArray(){
        return fragtitles;
    }

    public Fragment getFragment(int index){
        return (Fragment)fragarray.get(index);
    }

    public String getFragmentTitle(int index){
        return (String)fragtitles.get(index);
    }

}
