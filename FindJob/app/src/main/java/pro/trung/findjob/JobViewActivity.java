package pro.trung.findjob;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by Trung on 03/04/2017.
 */

public class JobViewActivity extends Activity {

    Intent intent;
    String link;
    static TextView tvJobCompanyInfor;
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        link = intent.getStringExtra("KEY_LINK");
        setContentView(R.layout.fragment_job_view);

//        new ParselData().execute(link);

//        tvJobCompanyInfor = (TextView) findViewById(R.id.tvJobCompanyInfor);
//        tvJobCompanyInfor.setText(link);
        webView = (WebView) findViewById(R.id.webView);
//        WebSettings settings = webView.getSettings();
//        settings.setDefaultTextEncodingName("utf-8");
        webView.loadUrl(link);


    }


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
                        .timeout(3000).get();
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
                Elements jobs  = document.select("div.item");
//                    Elements jobs  = document.select("div.portlet-content");
                Log.e("trung.tq", "5" + jobs.toString());
                if(jobs != null){
                    Log.e("trung.tq", "6 job size is : " + jobs.size() );

                    for (int i = 0 ; i < jobs.size() ; i ++){
                        Log.e("trung.tq", i + "");
                        Element job = jobs.get(i);

                        Element subLink = job.select("a.title").first();

                        String link = subLink.attr("href");
                        Log.e("trungtq", "https://mywork.com.vn" + link.toString());

                        String nameJob = job.select("a.title").first().text();
                        Log.e("trung.tq", nameJob);

                        String salary = "";
                        try {
                            salary = job.select("div.col-4.text-right span").first().text();
                            if (job.select("div.col-4.text-right span").next() != null){
                                salary = job.select("div.col-4.text-right span").first().text() + " - " +
                                        job.select("div.col-4.text-right span").last().text();;
                            }
                        }catch (NullPointerException ex){
                            salary = job.select("div.col-4.text-right").text();
                        }
//                            String salary = job.select("div.col-4.text-right span").first().text();

                        Log.e("trung.tq", salary);

                        String location = job.select("p.desc > a").first().text();
                        Log.e("trung.tq", location);

                        String title = job.select("p.desc > a").attr("title").toString().replaceFirst("Tìm việc làm của nhà tuyển dụng ", "");
                        Log.e("trung.tq", title);

                        String time = job.select("div.col-3 > p.desc").first().text();
                        Log.e("trung.tq", time);

                        String address = job.select("p.desc.text-location > a").first().text();
                        Log.e("trung.tq", address);



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
