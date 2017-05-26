package pro.trung.findjob;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;

import java.util.List;

import pro.trung.findjob.adapters.JobAdapter;
import pro.trung.findjob.model.Job;
import pro.trung.findjob.ui.fragments.FragmentDemo;
import pro.trung.findjob.ui.fragments.FragmentSong;
import pro.trung.findjob.ui.fragments.FragmentOne;

public class MainActivity extends AppCompatActivity implements FragmentDemo.Contact{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private FragmentManager fragmentManager;

    private List<Job> jobList;
    private ListView listView;
    private JobAdapter jobAdapter;
    private String strDiaDiem = "";
    private String strExp = "";
    private String strSalary = "";
    private String strNganh = "";
    private String strTrinhDo = "";
    private String strChucVu = "";
    private String kyNang = "";
    private boolean check = false;
    private Bundle bundle = new Bundle();
    FragmentDemo fragmentDemo = new FragmentDemo();
    FragmentOne fragmentOne = new FragmentOne();
    FragmentSong fragmentSong = new FragmentSong();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        fragmentManager = getSupportFragmentManager();
//        FragmentSong fragmentJob = new FragmentSong();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(R.id.container, fragmentJob);
//        transaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        Log.e("trung.tq", R.id.lvJob + "");

//        listView = (ListView) findViewById(R.id.lvJob);
//        jobList = new ArrayList<Job>();
//        for (int i = 0; i < 20; i++) {
//            jobList.add(new Job("1", "Lập trình viên", "200-300", "google", "American", "java c++ android"));
//        }
//        jobAdapter = new JobAdapter(MainActivity.this, jobList);
//        listView.setAdapter(jobAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void changeTrinhDo(String trinhDo) {
        this.strTrinhDo = trinhDo;
        bundle.putString("trinhdo", strTrinhDo);
    }

    @Override
    public void changeNganh(String nganh) {
        this.strNganh = nganh;
        bundle.putString("nganh", strNganh);
    }

    @Override
    public void changeDiaDiem(String diaDiem) {
        this.strDiaDiem = diaDiem;
        bundle.putString("diadiem", strDiaDiem);
    }

    @Override
    public void changeExp(String exp) {
        this.strExp = exp;
        bundle.putString("exp", strExp);
    }

    @Override
    public void changeSalary(String salary) {
        this.strSalary = salary;
        bundle.putString("salary", strSalary);
    }

    @Override
    public void changeChucVu(String chucVu) {
        this.strChucVu = chucVu;
        bundle.putString("chucvu", strChucVu);
    }

    @Override
    public void changeKyNang(String kyNang) {
        this.kyNang = kyNang;
        bundle.putString("kynang", this.kyNang);
        Log.e("trung.tq", "ky nang1: " + this.kyNang);
    }

    @Override
    public void checkContact(boolean check) {
        this.check = check;
        bundle.putBoolean("check", this.check);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            final TextView textView = (TextView) rootView.findViewById(R.id.section_label1);
//            textView.setText(getString(R.string.section_format1, getArguments().getInt(ARG_SECTION_NUMBER)));
//
//            Button bt = (Button) rootView.findViewById(R.id.button);
//            bt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    textView.setText("I'm king");
//                }
//            });

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            if(position == 0){

                return fragmentDemo;
//                return new FragmentDemo();
            }else if(position == 1){
                Log.e("trung.tq","check: " + check );
                if (check){
                    fragmentOne.setArguments(bundle);
                    return fragmentOne;
                }else {
                    return new FragmentOne();
                }

//                return new FragmentSong();
            }else {
                return fragmentSong;
//                return new FragmentOne();
            }

//            return PlaceholderFragment.newInstance(position + 1);

//            return new FragmentSong();

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Tư vấn";
                case 1:
                    return "Công việc";
                case 2:
                    return "Nghe nhạc";
            }
            return null;
        }
    }


}
