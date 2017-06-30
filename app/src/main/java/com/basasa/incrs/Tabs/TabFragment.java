package com.basasa.incrs.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basasa.incrs.R;
import com.basasa.incrs.posts.Lecturer_Post;
import com.basasa.incrs.posts.Student_Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by basasa on 2/27/2017.
 */
public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public TabFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_layout, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Student_Post(),"STUDENT POSTS");
        adapter.addFragment(new Lecturer_Post(),"LECTURER POSTS");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList=new ArrayList<>();
        private final  List<String>mfragmentTitleList=new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }
        @Override
        public Fragment getItem(int position){
            return fragmentList.get(position);
        }
        @Override
        public int getCount(){
            return fragmentList.size();
        }
        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            mfragmentTitleList.add(title);
        }
        @Override
        public  CharSequence getPageTitle(int position){
            return mfragmentTitleList.get(position);
        }
    }

}
