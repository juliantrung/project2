package pro.trung.findjob.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import pro.trung.findjob.JobViewActivity;
import pro.trung.findjob.R;
import pro.trung.findjob.adapters.JobAdapter;
import pro.trung.findjob.model.Job;

/**
 * Created by Trung on 25/03/2017.
 */

public class FragmentOne extends Fragment implements FragmentDemo.Contact{

    private List<Job> jobList;
    private ListView listView;
    private JobAdapter jobAdapter;
    private FragmentManager fragmentManager;
    private View fragmentJob;
    private Context context;
    Intent intent;
    private int numPage1 = 1;
    private int numPage2 = 1;
    private int numPage3 = 1;
    private String nextLink;
    private String linkWeb = "";
    String itemJob;
    int numJob2 = 19;
    String tempJob;
    String itemLocation;
    String itemSalary = "0";
    String itemWeb;
    String itemExp = "";
    int numLocation = 1;

    private String strDiaDiem = "";
    private String strExp = "";
    private String strSalary = "";
    private String strNganh = "";
    private String strTrinhDo = "";
    private String strChucVu = "";
    private String strKyNang = "";
    private boolean check = false;


    String[] jobsArray = {"Tất cả ngành", "Lao động phổ thông", "It phần mềm", "It phần cứng",
                            "Cơ khí", "Kế toán kiểm toán", "Marketing" ,"Bán hàng", "Thực tập", "Sinh viên làm thêm"};

    String[] salaryArray = {"Tất cả lương", "> 3 triệu", "> 5 triệu", "> 10 triệu", "> 20 triệu"};

    String[] expArray = {"Tất cả kinh nghiệm", " < 1 năm", "1 - 2 năm", "2 - 5 năm", "5-10 năm", " > 10 năm"};

    String[] webArray = {"www.careerlink.vn", "www.mywork.com.vn", "www.careerbuilder.vn", "www.topcv.vn", "www.timviecnhanh.com"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        Log.e("trung.tq", "First");


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        fragmentJob = inflater.inflate(R.layout.fragment_one, null);

        intent = new Intent(context, JobViewActivity.class);

        if (check){
            Bundle bundle = this.getArguments();
            strKyNang = bundle.getString("kynang");
            strChucVu = bundle.getString("chucvu");
            strDiaDiem = bundle.getString("diadiem");
            strSalary = bundle.getString("salary");
            strExp = bundle.getString("exp");
            strNganh = bundle.getString("nganh");
            strChucVu = bundle.getString("chucvu");
            strTrinhDo = bundle.getString("trinhdo");
            check = bundle.getBoolean("check");
        }


        Log.e("trung.tq", "Ky nang in 1: " + strKyNang);

        final EditText etFunction = (EditText) fragmentJob.findViewById(R.id.etFunction);

        final Spinner spJob = (Spinner) fragmentJob.findViewById(R.id.spJob);
        ArrayAdapter<String> jobArrayAdapter = new ArrayAdapter<String>(fragmentJob.getContext(),
                android.R.layout.simple_spinner_item, jobsArray);
        jobArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJob.setAdapter(jobArrayAdapter);
        spJob.setOnItemSelectedListener(new SpinnerJob());

        Spinner spLocation = (Spinner) fragmentJob.findViewById(R.id.spLocation);
        ArrayAdapter<CharSequence> locationArrayAdapter = ArrayAdapter.createFromResource(fragmentJob.getContext(),
                R.array.locations_array, android.R.layout.simple_spinner_item);
        jobArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(locationArrayAdapter);
        spLocation.setOnItemSelectedListener(new SpinnerLocation());

        Spinner spSalary = (Spinner) fragmentJob.findViewById(R.id.spSalary);
        ArrayAdapter<String> salaryArrayAdapter = new ArrayAdapter<String>(fragmentJob.getContext(),
                android.R.layout.simple_spinner_item, salaryArray);
        salaryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSalary.setAdapter(salaryArrayAdapter);
        spSalary.setOnItemSelectedListener(new SpinnerSalary());

        final Spinner spExp = (Spinner) fragmentJob.findViewById(R.id.spExp);
        ArrayAdapter<String> expArrayAdapter = new ArrayAdapter<String>(fragmentJob.getContext(),
                android.R.layout.simple_spinner_item, expArray);
        expArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spExp.setAdapter(expArrayAdapter);
        spExp.setOnItemSelectedListener(new SpinnerExp());

        Spinner spWeb = (Spinner) fragmentJob.findViewById(R.id.spWeb);
        ArrayAdapter<String> webArrayAdapter = new ArrayAdapter<String>(fragmentJob.getContext(),
                android.R.layout.simple_spinner_dropdown_item, webArray);
        webArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWeb.setAdapter(webArrayAdapter);
        spWeb.setOnItemSelectedListener(new SpinnerWeb());

        linkWeb = "https://www.careerlink.vn/viec-lam/cntt-phan-mem/19?";

        ImageButton ibSearch = (ImageButton) fragmentJob.findViewById(R.id.ibSearch);
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check){
                    etFunction.setText(strKyNang);
                    Log.e("trung.tq", "ky nang in: " + strKyNang);
                }
                if (itemWeb.equalsIgnoreCase("www.mywork.com.vn")) {
                    numPage1 = 1;
                    if (!etFunction.getText().toString().equalsIgnoreCase("Nhập từ khóa") &&
                            !(etFunction.getText().toString().isEmpty())) {
                        Log.e("trung.tq", "cv 1");
                        linkWeb = "https://mywork.com.vn/tim-viec-lam/" + removeAccent(etFunction.getText().toString().replace(" ", "-").replace("đ", "d").toLowerCase());
                    } else if (!itemJob.equalsIgnoreCase("Tất cả ngành")) {
                        if (itemLocation.equalsIgnoreCase("Tat ca dia diem")) {
                            Log.e("trung.tq", "cv2");
                            linkWeb = "https://mywork.com.vn/tuyen-dung" + "/38/" + removeAccent(itemJob.replace(" ", "-").replace("đ", "d").toLowerCase());
                        } else {
                            if (numLocation == 0) {
                                Log.e("trung.tq", "cv3");
                                linkWeb = "https://mywork.com.vn/tim-viec-lam/" + removeAccent(itemJob.replace(" ", "-").replace("đ", "d").toLowerCase());
                            } else {
                                Log.e("trung.tq", "cv4");
                                linkWeb = "https://mywork.com.vn/tim-viec-lam/" + removeAccent(itemJob.replace(" ", "-").replace("đ", "d").toLowerCase() + "-tai-" +
                                        removeAccent(itemLocation.replace(" ", "-").replace("đ", "d").toLowerCase())) + "-c1-w" + numLocation;
                            }
                        }
                    } else {
                        Log.e("trung.tq", "cv5");
                        linkWeb = "https://mywork.com.vn/tuyen-dung/dia-diem/" + numLocation + "/" +
                                removeAccent(itemLocation.replace(" ", "-").replace("đ", "d").toLowerCase());
                    }

                    Button btNext = (Button) fragmentJob.findViewById(R.id.btNext);
                    btNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            numPage1++;
                            nextLink = linkWeb +"/trang/" + numPage1 + "#list_job";
                            Log.e("trung.tq", "nextLink1: " + nextLink);
                            new ParselData().execute(nextLink);
                        }
                    });



                }else if (itemWeb.equalsIgnoreCase("www.careerlink.vn") || itemWeb.equalsIgnoreCase("www.careerbuilder.vn")){
                    numPage2 = 1;
                    if (!etFunction.getText().toString().equalsIgnoreCase("Nhập từ khóa") &&
                            !(etFunction.getText().toString().isEmpty())){
                        Log.e("trung.tq", "cv 1");
                        linkWeb = "https://www.careerlink.vn/viec-lam/k/" +
                                removeAccent(etFunction.getText().toString().replace(" ","-").replace("đ","d").toLowerCase()) + "?view=headline";
                    } else if (!itemJob.equalsIgnoreCase("Tất cả ngành")){
                        if (itemLocation.equalsIgnoreCase("Tat ca dia diem")){
                            Log.e("trung.tq", "cv2");
                            linkWeb = "https://www.careerlink.vn/viec-lam/" + removeAccent(itemJob.replace(" ", "-").replace("đ","d").toLowerCase() +
                                    "/" + numJob2 + "?");
                        } else{
                            if (numLocation == 0){
                                Log.e("trung.tq", "cv3");
                                if(removeAccent(itemJob.replace(" ", "-").replace("đ","d")).equalsIgnoreCase("it-phan-mem")){
                                    itemJob = "cntt-phan-mem";
                                }
                                linkWeb = "https://www.careerlink.vn/viec-lam/" +
                                        removeAccent(itemJob.replace(" ", "-").replace("đ","d").toLowerCase() + "/" + numJob2 + "?") ;
                            }else{
                                Log.e("trung.tq", "cv4");
                                linkWeb = "https://www.careerlink.vn/tim-viec-lam-tai/" +
                                        itemLocation + "c/" + removeAccent(itemJob.replace(" ", "-").replace("đ","d")) + "/" + numJob2 + "?";
                            }
                        }
                    } else{
                        Log.e("trung.tq", "cv5");
                        linkWeb = "https://www.careerlink.vn/tim-viec-lam-tai/" + itemLocation + "?";
                    }

                    if (!itemSalary.equalsIgnoreCase("0")){
                        linkWeb = "https://www.careerlink.vn/vieclam/tim-kiem-viec-lam?" + "keyword_use=A&category_ids=" +
                        numJob2 + "&salary_from=" + itemSalary + "&salary_from_money_code=VND&salary_type=M";
                    }

                    if (!itemExp.equalsIgnoreCase("")) {
                        linkWeb = "https://www.careerlink.vn/vieclam/tim-kiem-viec-lam?category_ids=" +
                                numJob2 + itemExp;
                        Log.e("trung.tq", "linkweb final: " + linkWeb);
                    }

                    Button btNext = (Button) fragmentJob.findViewById(R.id.btNext);
                    btNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            numPage2++;
                            nextLink = linkWeb +"&page=" + numPage2;
                            Log.e("trung.tq", "nextlink2: " + nextLink);
                            new ParselData().execute(nextLink);
                        }
                    });

                } else if(itemWeb.equalsIgnoreCase("www.jobmart.vn")){
                    // do something
                    numPage3 = 1;
                }

//                linkWeb = linkWeb + ".html";
                Log.e("Trung.tq", "linkweb: " + linkWeb);
                jobList.clear();
                new ParselData().execute(linkWeb);
//                linkWeb = "https://mywork.com.vn/tuyen-dung";

            }
        });


//        if (linkWeb.equalsIgnoreCase("https://mywork.com.vn/tuyen-dung")){
////            new ParselData().execute(linkWeb);
//            ibSearch.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    numPage1 = 1;
//                    if (!etFunction.getText().toString().equalsIgnoreCase("Nhập từ khóa") &&
//                            !(etFunction.getText().toString().isEmpty())){
//                        Log.e("trung.tq", "cv 1");
//                        linkWeb = "https://mywork.com.vn/tim-viec-lam/" + removeAccent(etFunction.getText().toString().replace(" ","-").replace("đ","d").toLowerCase());
//                    } else if (!itemJob.equalsIgnoreCase("Tất cả ngành")){
//                        if (itemLocation.equalsIgnoreCase("Tat ca dia diem")){
//                            Log.e("trung.tq", "cv2");
//                            linkWeb = "https://mywork.com.vn/tuyen-dung" + "/38/" + removeAccent(itemJob.replace(" ", "-").replace("đ","d").toLowerCase());
//                        } else{
//                            if (numLocation == 0){
//                                Log.e("trung.tq", "cv3");
//                                linkWeb = "https://mywork.com.vn/tim-viec-lam/" + removeAccent(itemJob.replace(" ", "-").replace("đ","d").toLowerCase()) ;
//                            }else{
//                                Log.e("trung.tq", "cv4");
//                                linkWeb = "https://mywork.com.vn/tim-viec-lam/" + removeAccent(itemJob.replace(" ", "-").replace("đ","d").toLowerCase() + "-tai-" +
//                                        removeAccent(itemLocation.replace(" ", "-").replace("đ","d").toLowerCase())) + "-c1-w" + numLocation;
//                            }
//                        }
//                    } else{
//                        Log.e("trung.tq", "cv5");
//                        linkWeb = "https://mywork.com.vn/tuyen-dung/dia-diem/" + numLocation + "/" +
//                                removeAccent(itemLocation.replace(" ", "-").replace("đ","d").toLowerCase());
//                    }
//
////                else if (!itemJob.equalsIgnoreCase("Tất cả ngành") && itemLocation.equalsIgnoreCase("Tất cả địa điểm")){
////                    Log.e("trung.tq", "cv2");
////                    linkWeb = "https://mywork.com.vn/tuyen-dung" + "/38/" + itemJob.replace(" ", "-").replace("đ","d");
////                } else if(!itemJob.equalsIgnoreCase("Tất cả ngành") && !itemLocation.equalsIgnoreCase("Tất cả địa điểm")){
////                    if (numLocation == 0){
////                        Log.e("trung.tq", "cv3");
////                        linkWeb = "https://mywork.com.vn/tim-viec-lam/" + removeAccent(itemJob.replace(" ", "-").replace("đ","d")) + "-m38";
////                    }else{
////                        Log.e("trung.tq", "cv4");
////                        linkWeb = "https://mywork.com.vn/tim-viec-lam/" + removeAccent(itemJob.replace(" ", "-").replace("đ","d") + "-tai-" +
////                                itemLocation.replace(" ", "-").replace("đ","d")) + "-c1-m38-w" + numLocation;
////                    }
////
////                } else if(itemJob.equalsIgnoreCase("Tất cả ngành") && !itemLocation.equalsIgnoreCase("Tất cả địa điểm")){
////                    Log.e("trung.tq", "cv5");
////                    linkWeb = "https://mywork.com.vn/tuyen-dung/dia-diem/" + numLocation + "/" +
////                            removeAccent(itemLocation.replace(" ", "-").replace("đ","d"));
////                }
//                    linkWeb = linkWeb + ".html";
//                    Log.e("Trung.tq", "linkweb: " + linkWeb);
//                    jobList.clear();
//                    new ParselData().execute(linkWeb);
////                linkWeb = "https://mywork.com.vn/tuyen-dung";
//
//                }
//            });
//
//            Button btNext = (Button) fragmentJob.findViewById(R.id.btNext);
//            btNext.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    numPage1++;
//                    nextLink = linkWeb +"/trang/" + numPage1 + "#list_job";
//                    Log.e("trung.tq", "nextLink1: " + nextLink);
//                    new ParselData().execute(nextLink);
//                }
//            });
//        }else if(linkWeb.equalsIgnoreCase("https://www.careerlink.vn/viec-lam/cntt-phan-mem/19?")){
//
//            ibSearch.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    numPage2 = 1;
//                    if (!etFunction.getText().toString().equalsIgnoreCase("Nhập từ khóa") &&
//                            !(etFunction.getText().toString().isEmpty())){
//                        Log.e("trung.tq", "cv 1");
//                        linkWeb = "https://www.careerlink.vn/viec-lam/k/" + removeAccent(etFunction.getText().toString().replace(" ","-").replace("đ","d").toLowerCase());
//                    } else if (!itemJob.equalsIgnoreCase("Tất cả ngành")){
//                        if (itemLocation.equalsIgnoreCase("Tat ca dia diem")){
//                            Log.e("trung.tq", "cv2");
//                            linkWeb = "https://www.careerlink.vn/viec-lam/" + removeAccent(itemJob.replace(" ", "-").replace("đ","d").toLowerCase() +
//                                    "/19?");
//                        } else{
//                            if (numLocation == 0){
//                                Log.e("trung.tq", "cv3");
//                                linkWeb = "https://mywork.com.vn/tim-viec-lam/" + removeAccent(itemJob.replace(" ", "-").replace("đ","d").toLowerCase()) ;
//                            }else{
//                                Log.e("trung.tq", "cv4");
//                                linkWeb = "https://mywork.com.vn/tim-viec-lam/" + removeAccent(itemJob.replace(" ", "-").replace("đ","d").toLowerCase() + "-tai-" +
//                                        removeAccent(itemLocation.replace(" ", "-").replace("đ","d").toLowerCase())) + "-c1-w" + numLocation;
//                            }
//                        }
//                    } else{
//                        Log.e("trung.tq", "cv5");
//                        linkWeb = "https://mywork.com.vn/tuyen-dung/dia-diem/" + numLocation + "/" +
//                                removeAccent(itemLocation.replace(" ", "-").replace("đ","d").toLowerCase());
//                    }
//
//                    linkWeb = linkWeb + ".html";
//                    Log.e("Trung.tq", "linkweb: " + linkWeb);
//                    jobList.clear();
//                    new ParselData().execute(linkWeb);
////                linkWeb = "https://mywork.com.vn/tuyen-dung";
//
//                }
//            });
//
//
//            Button btNext = (Button) fragmentJob.findViewById(R.id.btNext);
//            btNext.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    numPage2++;
//                    nextLink = linkWeb +"/page=" + numPage2;
//                    Log.e("trung.tq", "nextlink2: " + nextLink);
//                    new ParselData().execute(nextLink);
//                }
//            });
//        }


        listView = (ListView) fragmentJob.findViewById(R.id.lvJob);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("trung.tq", "item " + (position + 1) + " was clicked.");
                Toast.makeText(context, jobList.get(position).getJobName(), Toast.LENGTH_SHORT).show();

                String link = jobList.get(position).getLink();
                intent.putExtra("KEY_LINK", link);

                startActivity(intent);
            }
        });

//        Log.e("trung.tq",getActivity().toString() + " " + R.id.lvJob);
        /* load list Jobs
        jobList = new ArrayList<Job>();
        for (int i = 0; i < 15; i++) {
            jobList.add(new Job("1", "Lập trình viên", "200-300", "google", "American", "java c++ android"));
//            jobList.add(new Job("Lập trình viên", "200-300", "google", "American", "java c++ android"));
        }
        */

        jobList = new ArrayList<Job>();

//        Log.e("trung.tq", "Second");

        String [] parram = {linkWeb};
        new ParselData().execute(parram);


//        Log.e("trung.tq", "Third" + jobList.size());
//        jobAdapter = new JobAdapter(fragmentJob.getContext(), jobList);
//        listView.setAdapter(jobAdapter);
//        Log.e("trung.tq", "Fourth");

//        Bundle arg = new Bundle();
//        arg = this.getArguments();
//
//        String ten = arg.getString("kuNang");
//        Log.e("trung.tq", "Name: " + ten);


        return fragmentJob;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    @Override
    public void changeTrinhDo(String trinhDo) {

    }

    @Override
    public void changeNganh(String nganh) {

    }

    @Override
    public void changeDiaDiem(String diaDiem) {

    }

    @Override
    public void changeExp(String exp) {

    }

    @Override
    public void changeSalary(String salary) {

    }

    @Override
    public void changeChucVu(String chucVu) {

    }

    @Override
    public void changeKyNang(String kyNang) {
        strKyNang = kyNang;
        Log.e("trung.tq", "Ky nang 11: " + strKyNang);
    }

    @Override
    public void checkContact(boolean check) {

    }


    public class SpinnerJob extends Activity implements AdapterView.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)

            itemJob = parent.getItemAtPosition(pos).toString();
            Toast.makeText(getContext(), "job : " + itemJob, Toast.LENGTH_SHORT).show();
            switch (itemJob){
                case "Lao động phổ thông": {
                    numJob2 = 20;
                    break;
                }
                case "It phần mềm":{
                    numJob2 = 19;
                    break;
                }
                case "It phần cứng":{
                    numJob2 = 130;
                    break;
                }
                case "Cơ khí":{
                    numJob2 = 11;
                    break;
                }
                case "Thực tập":{

                    break;
                }
                case "Sinh viên làm thêm":{

                    break;
                }

                case "Bán hàng":{
                    numJob2 = 31;
                    break;
                }

                case "Kế toán kiểm toán":{
                    numJob2 = 1;
                    break;
                }

                case "Marketing":{
                    numJob2 = 136;
                    break;
                }

            }

        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }

    }

    public class SpinnerSalary extends Activity implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            itemSalary = parent.getItemAtPosition(position).toString();
            switch (itemSalary){
                case "> 3 triệu": {
                    itemSalary = "3000000";
                    break;
                }
                case "> 5 triệu": {
                    itemSalary = "5000000";
                    break;
                }
                case "> 10 triệu": {
                    itemSalary = "10000000";
                    break;
                }
                case "> 20 triệu": {
                    itemSalary = "20000000";
                    break;
                }
                default:{
                    itemSalary = "0";
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public class SpinnerExp extends Activity implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            itemExp = parent.getItemAtPosition(position).toString();
            switch (itemExp){
                case "< 1 năm": {
                    itemExp = "&experience_levels=A";
                    break;
                }
                case "1 - 2 năm": {
                    itemExp = "&experience_levels=B";
                    break;
                }
                case "2 - 5 năm": {
                    itemExp = "&experience_levels=C";
                    break;
                }
                case "5-10 năm": {
                    itemExp = "&experience_levels=D";
                    break;
                }
                case "> 10 năm": {
                    itemExp = "&experience_levels=E";
                    break;
                }
                default:{
                    itemExp = "";
                }

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public class SpinnerLocation extends Activity implements AdapterView.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)

            itemLocation = removeAccent(parent.getItemAtPosition(pos).toString());

            switch (itemLocation){
                case "Ha Noi":{
                    itemLocation = "ha-noi/HN/";
                    numLocation = 1;
                    break;
                }

                case "Ho Chi Minh": {
                    itemLocation = "ho-chi-minh/HCM/";
                    numLocation = 2;
                    break;
                }
                case "Da Nang": {
                    itemLocation = "da-nang/DN/";
                    numLocation = 17;
                    break;
                }
                case "Can Tho": {
                    itemLocation = "can-tho/CT/";
                    numLocation = 16;
                    break;
                }
                case "Hai Phong": {
                    itemLocation = "hai-phong/HP/";
                    numLocation = 29;
                    break;
                }

                default: {
                    itemLocation = "ha-noi/HN/";
                    numLocation = 0;
                }
            }
            Toast.makeText(getContext(), "Location : " + itemLocation, Toast.LENGTH_SHORT).show();

        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }

    }

    public class SpinnerWeb extends Activity implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            itemWeb = removeAccent(parent.getItemAtPosition(position).toString());
            switch (itemWeb){
                case "www.mywork.com.vn": {
                    linkWeb = "https://mywork.com.vn/tuyen-dung";
                    jobList.clear();
                    new ParselData().execute(linkWeb);
                    break;
                }

                case "www.careerlink.vn":{
                    linkWeb = "https://www.careerlink.vn/viec-lam/cntt-phan-mem/19?";
                    jobList.clear();
                    new ParselData().execute(linkWeb);
                    break;
                }

                case "www.vieclam24h.vn":{
                    linkWeb = "https://vieclam24h.vn/it-phan-mem-c74.html";
                    jobList.clear();
                    new ParselData().execute(linkWeb);
                    break;
                }

                case "www.careerbuilder.vn":{
                    linkWeb = "https://www.careerlink.vn/viec-lam/ban-hang/31?";
                    jobList.clear();
                    new ParselData().execute(linkWeb);
                    break;
                }

                case "www.topcv.vn":{
                    linkWeb = "https://www.topcv.vn/viec-lam/it-phan-mem/moi-nhat";
                    jobList.clear();
                    new ParselData().execute(linkWeb);
                    break;
                }

                default: {
                    linkWeb = "https://www.careerlink.vn/viec-lam/cntt-phan-mem/19?";
                    new ParselData().execute(linkWeb);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


//    public  class  ParselData extends AsyncTask<String,Integer, Document> {
//
//        String url;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Document doInBackground(String... params) {
//            url = params[0];
//            Document doc = null;
//            try {
//                doc = Jsoup.connect(url).data("query", "Java")
//                        .userAgent("Mozilla")
//                        .cookie("auth", "token")
//                        .timeout(3000).get();
//                Log.e("trung.tq", "3");
//            }catch (Exception ex){
//                Log.e("trung.tq",ex.toString());
//            }
//
//            return doc;
//        }
//
//        @Override
//        protected void onPostExecute(Document document) {
//            super.onPostExecute(document);
////            Log.e("trung.tq", "4" + document.toString());
//            if(document!=null){
////                jobList = new ArrayList<Job>();
//                Elements jobs  = document.select("div.item");
////                    Elements jobs  = document.select("div.portlet-content");
////                Log.e("trung.tq", "5" + jobs.toString());
//                if(jobs != null){
//                    Log.e("trung.tq", "6 job size is : " + jobs.size() );
//
//                    for (int i = 0 ; i < jobs.size() ; i ++){
//                        Log.e("trung.tq", i + "");
//                        Element job = jobs.get(i);
//
//                        Element subLink = job.select("a.title").first();
//
//                        String link = "https://mywork.com.vn" + subLink.attr("href");
////                        Log.e("trungtq", "https://mywork.com.vn" + link.toString());
//
//                        String nameJob = job.select("a.title").first().text();
//                        Log.e("trung.tq", nameJob);
//
//                        String salary = "";
//                        try {
//                            salary = job.select("div.col-4.text-right span").first().text();
//                            if (job.select("div.col-4.text-right span").next() != null){
//                                salary = job.select("div.col-4.text-right span").first().text() + " - " +
//                                        job.select("div.col-4.text-right span").last().text();;
//                            }
//                        }catch (NullPointerException ex){
//                            salary = job.select("div.col-4.text-right").text();
//                        }
////                            String salary = job.select("div.col-4.text-right span").first().text();
//
//                        Log.e("trung.tq", salary);
//
//                        String location = job.select("p.desc > a").first().text();
//                        Log.e("trung.tq", location);
//
//                        String title = job.select("p.desc > a").attr("title").toString().replaceFirst("Tìm việc làm của nhà tuyển dụng ", "");
//                        Log.e("trung.tq", title);
//
//                        String time = job.select("div.col-3 > p.desc").first().text();
//                        Log.e("trung.tq", time);
//
//                        String address = job.select("p.desc.text-location > a").first().text();
//                        Log.e("trung.tq", address);
//
//                        jobList.add(new Job(nameJob, salary, title, address, time, link));
//
//
//                    }
//
//                    Log.e("trung.tq", numPage + "");
//
//                    if(numPage == 1){
//                        jobAdapter = new JobAdapter(fragmentJob.getContext(), jobList);
//                        listView.setAdapter(jobAdapter);
//                    }else{
//                        jobAdapter.notifyDataSetChanged();
////                        listView.setAdapter(jobAdapter);
//                    }
//
//                    Log.e("trung.tq", "Adapter was set");
//
//
//                    Log.e("trung.tq", "Adapter is over");
//
//                }
//            }
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//
//
//        }
//
//
//    }

    public  class  ParselData extends AsyncTask<String,Integer, Document> {

        String url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Document doInBackground(String... params) {
            url = params[0];
            Document doc = null;
            try {
                doc = Jsoup.connect(url).data("query", "Java")
                        .userAgent("Mozilla")
                        .cookie("auth", "token")
                        .timeout(6000).get();
                Log.e("trung.tq", "3");
            }catch (Exception ex){
                Log.e("trung.tq",ex.toString());
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
//            Log.e("trung.tq", "4" + document.toString());
            if(document!=null){
//                jobList = new ArrayList<Job>();
                if(itemWeb.equalsIgnoreCase("www.careerlink.vn")) {
                    Elements jobs = document.select("div.list-group-item");
//                    Elements jobs  = document.select("div.portlet-content");
//                Log.e("trung.tq", "5" + jobs.toString());
                    if (jobs != null) {
                        Log.e("trung.tq", "6 job size is : " + jobs.size());

                        for (int i = 0; i < jobs.size(); i++) {
                            Log.e("trung.tq", i + "");
                            Element job = jobs.get(i);


                            Element subLink = job.select("h2.list-group-item-heading > a").first();
//
                            String link = "https://www.careerlink.vn" + subLink.attr("href");
                            Log.e("trungtq", link.toString());
//
                            String nameJob = job.select("h2.list-group-item-heading > a").first().text();
                            Log.e("trung.tq", nameJob);
//
//                        String salary = "";
//                        try {
//                            salary = job.select("div.col-4.text-right span").first().text();
//                            if (job.select("div.col-4.text-right span").next() != null){
//                                salary = job.select("div.col-4.text-right span").first().text() + " - " +
//                                        job.select("div.col-4.text-right span").last().text();;
//                            }
//                        }catch (NullPointerException ex){
//                            salary = job.select("div.col-4.text-right").text();
//                        }
                            String salary = job.select("div.pull-left small").first().text();
//
                            Log.e("trung.tq", salary);
//
                            String location = job.select("p.priority-data > a").first().text();
                            Log.e("trung.tq", location);
//
//                        String title = job.select("p.desc > a").attr("title").toString().replaceFirst("Tìm việc làm của nhà tuyển dụng ", "");
//                        Log.e("trung.tq", title);
//
                            String time = "Ngày đăng: " + job.select("p.date.pull-right small").first().text();
                            Log.e("trung.tq", time);
//
                            String address = job.select("p.priority-data > a").last().text();
                            Log.e("trung.tq", address);

                            jobList.add(new Job(nameJob, salary, location, address, time, link));


                        }

                        Log.e("trung.tq", numPage2 + " abc");

                        if (numPage2 == 1) {
                            jobAdapter = new JobAdapter(fragmentJob.getContext(), jobList);
                            listView.setAdapter(jobAdapter);
                        } else {
                            jobAdapter.notifyDataSetChanged();
//                        listView.setAdapter(jobAdapter);
                        }

                        Log.e("trung.tq", "Adapter was set");


                        Log.e("trung.tq", "Adapter is over");

                    }
                }else if(itemWeb.equalsIgnoreCase("www.mywork.com.vn")) {
                    if (document != null) {
//                jobList = new ArrayList<Job>();
                        Elements jobs = document.select("div.item");
//                    Elements jobs  = document.select("div.portlet-content");
//                Log.e("trung.tq", "5" + jobs.toString());
                        if (jobs != null) {
                            Log.e("trung.tq", "6 job size is : " + jobs.size());

                            for (int i = 0; i < jobs.size(); i++) {
                                Log.e("trung.tq", i + "");
                                Element job = jobs.get(i);

                                Element subLink = job.select("a.title").first();

                                String link = "https://mywork.com.vn" + subLink.attr("href");
//                        Log.e("trungtq", "https://mywork.com.vn" + link.toString());

                                String nameJob = job.select("a.title").first().text();
                                Log.e("trung.tq", nameJob);

                                String salary = "";
                                try {
                                    salary = job.select("div.col-4.text-right span").first().text();
                                    if (job.select("div.col-4.text-right span").next() != null) {
                                        salary = job.select("div.col-4.text-right span").first().text() + " - " +
                                                job.select("div.col-4.text-right span").last().text();
                                        ;
                                    }
                                } catch (NullPointerException ex) {
                                    salary = job.select("div.col-4.text-right").text();
                                }
//                            String salary = job.select("div.col-4.text-right span").first().text();

                                Log.e("trung.tq", salary);

                                String location = job.select("p.desc > a").first().text();
                                Log.e("trung.tq", location);

                                String title = job.select("p.desc > a").attr("title").toString().replaceFirst("Tìm việc làm của nhà tuyển dụng ", "");
                                Log.e("trung.tq", title);

                                String time = "Hạn tuyển: " + job.select("div.col-3 > p.desc").first().text();
                                Log.e("trung.tq", time);

                                String address = job.select("p.desc.text-location > a").first().text();
                                Log.e("trung.tq", address);

                                jobList.add(new Job(nameJob, salary, title, address, time, link));


                            }

                            Log.e("trung.tq", numPage1 + "");

                            if (numPage1 == 1) {
                                jobAdapter = new JobAdapter(fragmentJob.getContext(), jobList);
                                listView.setAdapter(jobAdapter);
                            } else {
                                jobAdapter.notifyDataSetChanged();
//                        listView.setAdapter(jobAdapter);
                            }

                            Log.e("trung.tq", "Adapter was set");


                            Log.e("trung.tq", "Adapter is over");

                        }
                    }
                } else if(itemWeb.equalsIgnoreCase("www.vieclam24h.vn")){
                    if (document != null) {
//                jobList = new ArrayList<Job>();
                        Log.e("trung.tq", "vieclam24h");
                        Elements jobs = document.select("div.list-items");
//                    Elements jobs  = document.select("div.portlet-content");
//                Log.e("trung.tq", "5" + jobs.toString());
                        if (jobs != null) {
                            Log.e("trung.tq", "6 job size is : " + jobs.size());

                            for (int i = 0; i < jobs.size(); i++) {
                                Log.e("trung.tq", i + "");
                                Element job = jobs.get(i);

                                Element subLink =
                                        job.select("span.title-blockjob-main.truncate-ellipsis.font14.pos_relative.pr_28>a").first();

                                String link = subLink.attr("href");
                                Log.e("trungtq", "link" + link.toString());

                                String nameJob = subLink.attr("title");
                                Log.e("trung.tq", nameJob);

//                                String salary = "";
//                                try {
//                                    salary = job.select("div.col-4.text-right span").first().text();
//                                    if (job.select("div.col-4.text-right span").next() != null) {
//                                        salary = job.select("div.col-4.text-right span").first().text() + " - " +
//                                                job.select("div.col-4.text-right span").last().text();
//                                        ;
//                                    }
//                                } catch (NullPointerException ex) {
//                                    salary = job.select("div.col-4.text-right").text();
//                                }
                                String salary = " ";
                                salary = job.select("div.pos_absolute.list_note_icon > div").first().text();

                                Log.e("trung.tq", salary);

                                String location = job.select("div.note_mucluong.text-center.font12.text_black.floatLeft  > span").first().text();
                                Log.e("trung.tq", location);

                                String title = job.select("span.title-blockjob-sub.truncate-ellipsis.font14.text_grey > a").first().text();
                                Log.e("trung.tq", title);

                                String time = "Ngày hết hạn: " + job.select("div.note_mucluong.text-center.font12.text_black.floatLeft").next().text();
                                Log.e("trung.tq", time);

//                                String address = job.select("div.col-sm-9.box-vl-content > span").first().text();
//                                Log.e("trung.tq", address);

                                jobList.add(new Job(nameJob, salary, title, location, time, link));


                            }

                            Log.e("trung.tq", numPage3 + "");

                            if (numPage3 == 1) {
                                jobAdapter = new JobAdapter(fragmentJob.getContext(), jobList);
                                listView.setAdapter(jobAdapter);
                            } else {
                                jobAdapter.notifyDataSetChanged();
//                        listView.setAdapter(jobAdapter);
                            }

                            Log.e("trung.tq", "Adapter was set");


                            Log.e("trung.tq", "Adapter is over");

                        }
                    }
                } else if(itemWeb.equalsIgnoreCase("www.careerbuilder.vn")){
                    Elements jobs = document.select("div.list-group-item");
//                    Elements jobs  = document.select("div.portlet-content");
//                Log.e("trung.tq", "5" + jobs.toString());
                    if (jobs != null) {
                        Log.e("trung.tq", "6 job size is : " + jobs.size());

                        for (int i = 0; i < jobs.size(); i++) {
                            Log.e("trung.tq", i + "");
                            Element job = jobs.get(i);


                            Element subLink = job.select("h2.list-group-item-heading > a").first();
//
                            String link = "https://www.careerlink.vn" + subLink.attr("href");
                            Log.e("trungtq", link.toString());
//
                            String nameJob = job.select("h2.list-group-item-heading > a").first().text();
                            Log.e("trung.tq", nameJob);
//
//                        String salary = "";
//                        try {
//                            salary = job.select("div.col-4.text-right span").first().text();
//                            if (job.select("div.col-4.text-right span").next() != null){
//                                salary = job.select("div.col-4.text-right span").first().text() + " - " +
//                                        job.select("div.col-4.text-right span").last().text();;
//                            }
//                        }catch (NullPointerException ex){
//                            salary = job.select("div.col-4.text-right").text();
//                        }
                            String salary = job.select("div.pull-left small").first().text();
//
                            Log.e("trung.tq", salary);
//
                            String location = job.select("p.priority-data > a").first().text();
                            Log.e("trung.tq", location);
//
//                        String title = job.select("p.desc > a").attr("title").toString().replaceFirst("Tìm việc làm của nhà tuyển dụng ", "");
//                        Log.e("trung.tq", title);
//
                            String time = "Ngày đăng: " + job.select("p.date.pull-right small").first().text();
                            Log.e("trung.tq", time);
//
                            String address = job.select("p.priority-data > a").last().text();
                            Log.e("trung.tq", address);

                            jobList.add(new Job(nameJob, salary, location, address, time, link));


                        }

                        Log.e("trung.tq", numPage2 + " abc");

                        if (numPage2 == 1) {
                            jobAdapter = new JobAdapter(fragmentJob.getContext(), jobList);
                            listView.setAdapter(jobAdapter);
                        } else {
                            jobAdapter.notifyDataSetChanged();
//                        listView.setAdapter(jobAdapter);
                        }

                        Log.e("trung.tq", "Adapter was set");


                        Log.e("trung.tq", "Adapter is over");

                    }
                } else if (itemWeb.equalsIgnoreCase("www.topcv.vn")) {
                    if (document != null) {
//                jobList = new ArrayList<Job>();
                        Elements jobs = document.select("div.box.box-white.job");
//                    Elements jobs  = document.select("div.portlet-content");
//                Log.e("trung.tq", "5" + jobs.toString());
                        if (jobs != null) {
                            Log.e("trung.tq", "6 job size is : " + jobs.size());

                            for (int i = 0; i < jobs.size(); i++) {
                                Log.e("trung.tq", i + "");
                                Element job = jobs.get(i);

                                Element subLink = job.select("h4.job-title > a").first();

                                String link = subLink.attr("href");
                                Log.e("trungtq", "https://mywork.com.vn" + link.toString());

                                String nameJob = job.select("h4.job-title > span").first().text();
                                Log.e("trung.tq", nameJob);

//                                String salary = "";
//                                try {
//                                    salary = job.select("div.col-4.text-right span").first().text();
//                                    if (job.select("div.col-4.text-right span").next() != null) {
//                                        salary = job.select("div.col-4.text-right span").first().text() + " - " +
//                                                job.select("div.col-4.text-right span").last().text();
//                                        ;
//                                    }
//                                } catch (NullPointerException ex) {
//                                    salary = job.select("div.col-4.text-right").text();
//                                }
////                            String salary = job.select("div.col-4.text-right span").first().text();
//
//                                Log.e("trung.tq", salary);
//
//                                String location = job.select("p.desc > a").first().text();
//                                Log.e("trung.tq", location);
//
//                                String title = job.select("p.desc > a").attr("title").toString().replaceFirst("Tìm việc làm của nhà tuyển dụng ", "");
//                                Log.e("trung.tq", title);
//
//                                String time = "Hạn tuyển: " + job.select("div.col-3 > p.desc").first().text();
//                                Log.e("trung.tq", time);
//
//                                String address = job.select("p.desc.text-location > a").first().text();
//                                Log.e("trung.tq", address);
//
//                                jobList.add(new Job(nameJob, salary, title, address, time, link));


                            }

                            Log.e("trung.tq", numPage1 + "");

                            if (numPage1 == 1) {
                                jobAdapter = new JobAdapter(fragmentJob.getContext(), jobList);
                                listView.setAdapter(jobAdapter);
                            } else {
                                jobAdapter.notifyDataSetChanged();
//                        listView.setAdapter(jobAdapter);
                            }

                            Log.e("trung.tq", "Adapter was set");


                            Log.e("trung.tq", "Adapter is over");

                        }
                    }
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


        }


    }
}
