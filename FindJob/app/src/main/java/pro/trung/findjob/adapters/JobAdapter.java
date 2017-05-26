package pro.trung.findjob.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import pro.trung.findjob.R;
import pro.trung.findjob.model.Job;

/**
 * Created by Trung on 25/03/2017.
 */

public class JobAdapter extends BaseAdapter {

    private Context mContext;
    private List<Job> jobList;
    Bitmap bitmap;

    public JobAdapter(Context mContext, List<Job> jobList) {
        this.mContext = mContext;
        this.jobList = jobList;
//        Log.e("trung.tq", "Job is good");
    }

    @Override
    public int getCount() {
//        Log.e("trung.tq", "getCount");
        return jobList.size();
    }

    @Override
    public Object getItem(int position) {
//        Log.e("trung.tq", "getItem");
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        Log.e("trung.tq", "getView");
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.job_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.ivJob = (ImageView)convertView.findViewById(R.id.ivJob);
            holder.tvJobName = (TextView) convertView.findViewById(R.id.tvJobName);
            holder.tvSalary = (TextView) convertView.findViewById(R.id.tvSalary);
            holder.tvCompany = (TextView) convertView.findViewById(R.id.tvCompany);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
            holder.tvTags = (TextView) convertView.findViewById(R.id.tvTags);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final Job job = jobList.get(position);

        URL url = null;

//        try {
////            url = new URL(job.getImage());
//            url = new URL("https://jobtown.co:8080/img/production/company/1161/photo/d88eb140bfecd14666d9ff5affea47c67a744684fc134f63bbcc305a9ba0ded8.jpg?p=crop_small");
//            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            holder.ivJob.setImageBitmap(bitmap);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//        new LoadImage().execute("https://jobtown.co:8080/img/production/company/1161/photo/d88eb140bfecd14666d9ff5affea47c67a744684fc134f63bbcc305a9ba0ded8.jpg?p=crop_small");
//        holder.ivJob.setImageResource(R.drawable.job1);
//        holder.ivJob.setImageBitmap(bitmap);

        holder.tvJobName.setText(job.getJobName());
        holder.tvSalary.setText("Mức lương: " + job.getSalary());
        holder.tvCompany.setText(job.getCompany());
        holder.tvAddress.setText("Địa điểm: " + job.getAddress());
        holder.tvTags.setText(job.getTags());

        return convertView;
    }

    public class ViewHolder{
        ImageView ivJob;
        TextView tvJobName;
        TextView tvSalary;
        TextView tvCompany;
        TextView tvAddress;
        TextView tvTags;

    }

    private class LoadImage extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);

                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

}


