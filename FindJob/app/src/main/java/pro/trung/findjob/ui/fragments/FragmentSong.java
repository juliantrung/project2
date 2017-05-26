package pro.trung.findjob.ui.fragments;


import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

import pro.trung.findjob.R;

/**
 * Created by Trung on 25/03/2017.
 */

public class FragmentSong extends Fragment implements View.OnClickListener{

    private TextView tvStart;
    private TextView tvEnd;
    private SeekBar sbDuration;
    private Button btShuffle;
    private Button btPrev;
    private Button btPlay;
    private Button btNextSong;
    private Button btRepeat;
    private ListView lvSongs;
    private MediaPlayer mMediaPlayer;
    private String[] mMusicList;
    private int numSong = 10;
    private Boolean bShuffle = false;

    private FragmentManager fragmentManager;
    private Context context;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
//        mMediaPlayer = new MediaPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View fragmentSong = inflater.inflate(R.layout.fragment_music, null);

        Log.e("trung.tq", "media player");
        mMediaPlayer = new MediaPlayer();

        ListView mListView = (ListView) fragmentSong.findViewById(R.id.lvSongs);
        final Button btPlay = ( Button) fragmentSong.findViewById(R.id.btPlay);
        Boolean bPlay = false;
        final Button btNextSong = ( Button) fragmentSong.findViewById(R.id.btNextSong);
        final Button btPrev = ( Button) fragmentSong.findViewById(R.id.btPrev);
        final Button btRepeat = ( Button) fragmentSong.findViewById(R.id.btRepeat);
        final Button btShuffle = ( Button) fragmentSong.findViewById(R.id.btShuffle);


        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()){
                    mMediaPlayer.pause();
                    btPlay.setBackgroundResource(R.drawable.ic_play);
                } else{
                    try {
                        playSong(mMusicList[numSong]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    btPlay.setBackgroundResource(R.drawable.ic_play_pressed);
                }
            }
        });
        btNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btNextSong.setBackgroundResource(R.drawable.ic_next_pressed);
                try {
                    playSong(mMusicList[++numSong]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                btNextSong.setBackgroundResource(R.drawable.ic_next);

            }
        });
        btPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btPrev.setBackgroundResource(R.drawable.ic_prev_pressed);
                    playSong(mMusicList[--numSong]);
                    btPrev.setBackgroundResource(R.drawable.ic_prev);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mMediaPlayer.isLooping()){
                    btRepeat.setBackgroundResource(R.drawable.ic_repeat_all);
                    mMediaPlayer.setLooping(true);
                } else {
                    btRepeat.setBackgroundResource(R.drawable.ic_repeat_off);
                    mMediaPlayer.setLooping(false);
                }
            }
        });
        btShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bShuffle = !bShuffle;
                if (bShuffle){
                    btShuffle.setBackgroundResource(R.drawable.ic_shuffle_on);
                }else{
                    btShuffle.setBackgroundResource(R.drawable.ic_shuffle_off);
                }
            }
        });


        Log.e("trung.tq", "get music");
        mMusicList = getMusic();

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(fragmentSong.getContext(),
                android.R.layout.simple_list_item_1, mMusicList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                try {
                    numSong = arg2;
                    playSong(mMusicList[arg2]);
                    btPlay.setBackgroundResource(R.drawable.ic_play_pressed);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        return fragmentSong;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.btPlay: {
//                if (mMediaPlayer.isPlaying()){
//                    mMediaPlayer.pause();
//                    btPlay.setBackgroundResource(R.drawable.ic_play);
//                } else{
//                    try {
//                        playSong(mMusicList[numSong]);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    btPlay.setBackgroundResource(R.drawable.ic_play_pressed);
//                }
//                break;
//            }
//            case R.id.btNextSong: {
//                try {
//                    btNextSong.setBackgroundResource(R.drawable.ic_next_pressed);
//                    playSong(mMusicList[numSong + 1]);
//                    btNextSong.setBackgroundResource(R.drawable.ic_next);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
//            case R.id.btPrev: {
//                try {
//                    btPrev.setBackgroundResource(R.drawable.ic_prev_pressed);
//                    playSong(mMusicList[numSong - 1]);
//                    btPrev.setBackgroundResource(R.drawable.ic_prev);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
//            case R.id.btRepeat: {
//                if(!mMediaPlayer.isLooping()){
//                    btRepeat.setBackgroundResource(R.drawable.ic_repeat_all);
//                    mMediaPlayer.setLooping(true);
//                } else {
//                    btRepeat.setBackgroundResource(R.drawable.ic_repeat_off);
//                    mMediaPlayer.setLooping(false);
//                }
//                break;
//            }
//            case R.id.btShuffle: {
//                bShuffle = !bShuffle;
//                if (bShuffle){
//                    btShuffle.setBackgroundResource(R.drawable.ic_shuffle_on);
//                }else{
//                    btShuffle.setBackgroundResource(R.drawable.ic_shuffle_off);
//                }
//
//                break;
//            }
        }

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
//                Log.e("trung.tq", "3");
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
                    Elements jobs  = document.select("div.item");
//                    Elements jobs  = document.select("div.portlet-content");
//                    Log.e("trung.tq", "5" + jobs.toString());
                    if(jobs != null){
//                        Log.e("trung.tq", "6 job size is : " + jobs.size() );

                        for (int i = 0 ; i < jobs.size() ; i ++){
//                            Log.e("trung.tq", i + "");
                            Element job = jobs.get(i);
                            String nameJob = job.select("a.title").first().text();
//                            Log.e("trung.tq", nameJob);

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

//                            Log.e("trung.tq", salary);

                            String location = job.select("p.desc > a").first().text();
//                            Log.e("trung.tq", location);

                            String title = job.select("p.desc > a").attr("title").toString().replaceFirst("Tìm việc làm của nhà tuyển dụng ", "");
//                            Log.e("trung.tq", title);

                            String time = job.select("div.col-3 > p.desc").first().text();
//                            Log.e("trung.tq", time);

                            String address = job.select("p.desc.text-location > a").first().text();
//                            Log.e("trung.tq", address);



                        }
                    }
                }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


        }


    }
    private String[] getMusic() {
        final Cursor mCursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media.DISPLAY_NAME }, null, null,
                "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

        int count = mCursor.getCount();

        String[] songs = new String[count];
        int i = 0;
        if (mCursor.moveToFirst()) {
            do {
                songs[i] = mCursor.getString(0);
                Log.e("trung.tq", songs[i]);
                i++;
            } while (mCursor.moveToNext());
        }

        mCursor.close();

        return songs;
    }

    private void playSong(String path) throws IllegalArgumentException,
            IllegalStateException, IOException {
        String extStorageDirectory = Environment.getExternalStorageDirectory()
                .toString();

        path = extStorageDirectory + File.separator + path;

        mMediaPlayer.reset();
        mMediaPlayer.setDataSource(path);
        mMediaPlayer.prepare();
        mMediaPlayer.start();
    }
}
