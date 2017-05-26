package pro.trung.findjob;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import pro.trung.findjob.adapters.JobAdapter;
import pro.trung.findjob.model.Job;

/**
 * Created by Trung on 15/05/2017.
 */

public class TuVanActivity extends Activity {

    Context context;
    Intent intent;
    Intent intent1;
    ListView lvTuVan;
    TextView tvTuVan;
    private List<Job> jobList;
    private JobAdapter jobAdapter;
    String linkWeb = "";
    String strNganhHoc = "";
    String strDiaDiem = "";
    String strKyNang = "";
    String strSalary = "";
    String strExp = "";
    String strTrinhDo = "";
    String itemNganhNghe;
    String itemDiaDiem;
    int numLocation = 0;
    int numPage2 = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_tu_van);
        lvTuVan = (ListView) findViewById(R.id.lvTuVan);
        tvTuVan = (TextView) findViewById(R.id.tvTuVan);

        intent = getIntent();
        strNganhHoc = intent.getStringExtra("nganh_hoc");
        strDiaDiem = intent.getStringExtra("dia_diem");
        strKyNang = intent.getStringExtra("ky_nang");
        if (strKyNang.equalsIgnoreCase("Kỹ năng của bạn:")) {
            strKyNang = "không có";
        }
        strSalary = intent.getStringExtra("luong");
        strExp = intent.getStringExtra("kinh_nghiem");
        strTrinhDo = intent.getStringExtra("trinh_do");

        String strTuVan = "Với trình độ " + strTrinhDo
                + " và những gì bạn có chúng tôi khuyên bạn nên chọn việc làm liên quan đến ngành: "
                + strNganhHoc + " và phù hợp với các kỹ năng " + strKyNang + " của bạn.\n"
                + "Địa điểm làm việc thích hợp cho bạn là " + strDiaDiem + "\n"
                + "Với mức kinh nghiệm " + strExp + " của bạn thì mức lương " + strSalary
                + "sẽ thích hợp dành cho bạn.";
        tvTuVan.setText(strTuVan);


        if (!strKyNang.equalsIgnoreCase("không có")) {
            linkWeb = "https://www.careerlink.vn/viec-lam/k/" +
                    removeAccent(strKyNang.replace(" ", "-").replace("đ", "d").toLowerCase()) + "?view=headline";
        } else {
            linkWeb = "https://www.careerlink.vn/tim-viec-lam-tai/" +
                    checkDiaDiem() + "c/" + removeAccent(strNganhHoc.replace(" ", "-").replace("đ", "d")) + "/" + checkNganhNghe() + "?";
        }

//        jobList.clear();
        jobList = new ArrayList<Job>();
        do {
            new ParselData().execute(linkWeb);
            Log.e("trung.tq", "" + jobList.size());
        }while (!jobList.isEmpty());
        Log.e("trung.tq", "" + jobList.size());


        intent1 = new Intent(getApplicationContext(), JobViewActivity.class);
        lvTuVan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), jobList.get(position).getJobName(), Toast.LENGTH_SHORT).show();

                String link = jobList.get(position).getLink();
                intent1.putExtra("KEY_LINK", link);
                startActivity(intent1);
            }
        });

    }

    public String checkNganhNghe() {
        switch (strNganhHoc) {
            case "Lao động phổ thông": {
                itemNganhNghe = "20";
                break;
            }
            case "CNTT phần mềm": {
                itemNganhNghe = "19";
                break;
            }
            case "CNTT phần cứng": {
                itemNganhNghe = "130";
                break;
            }
            case "Cơ khí": {
                itemNganhNghe = "11";
                break;
            }
            case "Thực tập": {

                break;
            }
            case "Sinh viên làm thêm": {

                break;
            }

            case "Bán hàng": {
                itemNganhNghe = "31";
                break;
            }

            case "Kế toán kiểm toán": {
                itemNganhNghe = "1";
                break;
            }

            case "Marketing": {
                itemNganhNghe = "136";
                break;
            }
        }

        return itemNganhNghe;
    }

    public String checkDiaDiem() {

        switch (strDiaDiem) {
            case "Hà Nội": {
                itemDiaDiem = "ha-noi/HN/";
                numLocation = 1;
                break;
            }

            case "Hồ Chí Minh": {
                itemDiaDiem = "ho-chi-minh/HCM/";
                numLocation = 2;
                break;
            }
            case "Đà Nẵng": {
                itemDiaDiem = "da-nang/DN/";
                numLocation = 17;
                break;
            }
            case "Cần Thơ": {
                itemDiaDiem = "can-tho/CT/";
                numLocation = 16;
                break;
            }
            case "Hải Phòng": {
                itemDiaDiem = "hai-phong/HP/";
                numLocation = 29;
                break;
            }

            default: {
                itemDiaDiem = "ha-noi/HN/";
                numLocation = 0;
            }
        }
        return itemDiaDiem;
    }

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public class ParselData extends AsyncTask<String, Integer, Document> {

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
            } catch (Exception ex) {
                Log.e("trung.tq", ex.toString());
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
//            Log.e("trung.tq", "4" + document.toString());
            if (document != null) {
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
                        jobAdapter = new JobAdapter(getApplicationContext(), jobList);
                        lvTuVan.setAdapter(jobAdapter);
                    } else {
                        jobAdapter.notifyDataSetChanged();
//                        listView.setAdapter(jobAdapter);
                    }

                    Log.e("trung.tq", "Adapter was set");


                    Log.e("trung.tq", "Adapter is over");


                }
            }
        }

            @Override
            protected void onProgressUpdate (Integer...values){
                super.onProgressUpdate(values);


            }


        }

    }