package com.example.baskara.customlauncher;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AmazonFeed.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AmazonFeed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AmazonFeed extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Data> albumList;
    View pb;

    private OnFragmentInteractionListener mListener;

    private SwipeRefreshLayout swipeContainer;


    public AmazonFeed() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_scrolling, container, false);
        recyclerView = view.findViewById(R.id.rvItems);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        pb = view.findViewById(R.id.loadingPanel);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAppPresent("com.amazon.dee.app")) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.amazon.dee.app", "com.amazon.dee.app" +
                            ".ui.voice.VoiceActivity"));
                    startActivity(intent);
                } else {
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
                            ("market://details?id=com.amazon.dee.app"));
                    startActivity(marketIntent);
                }
            }
        });

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this.getContext(), albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        new GetArticleData().execute();
        return view;

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class GetArticleData extends AsyncTask<Void, Void, Void> {

        final private String urlArticle = "https://brxj3qfbs6.execute-api.us-west-2.amazonaws" +
                ".com/Beta?q=ARTCLH*";
        final private String urlMovies = "https://6e45f4em8g.execute-api.us-east-1.amazonaws.com/beta/stories/video";
        final private String urlMusic = "https://6e45f4em8g.execute-api.us-east-1.amazonaws.com/beta/stories/music";


        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            String responseArticle = httpHandler.makeServiceCall(urlArticle);

            if (responseArticle != null) {
                try {
                    JSONObject jsonObject = new JSONObject(responseArticle);
                    JSONObject successHit = (JSONObject) jsonObject.get("hits");
                    int count = successHit.getInt("found");

                    if (count != 0) {
                        JSONArray list = (JSONArray) successHit.getJSONArray("hit");
                        for(int i = 0; i < list.length(); i++) {
                            JSONObject articleJSON = ((JSONObject) list.get(i)).getJSONObject
                                    ("fields");
                            String title = articleJSON.getString("title");
                            String description = articleJSON.getString("description");
                            String productImageURL = null;
                            try {
                                productImageURL = articleJSON.getString("product_image_uri");
                            } catch (JSONException e) {
                                continue;
                            }
                            String asin = articleJSON.getString("asin");
                            Article article = new Article(title, productImageURL, description, asin);
                            albumList.add(article);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //populate kindle information
            try {
                KindleInfo kindleInfo = readKindleFile();
                albumList.add(kindleInfo);
            } catch (Exception e) {
                //supressing all the exceptions
                e.printStackTrace();
            }

            String responseMovies = httpHandler.makeServiceCall(urlMovies);

            if(responseMovies != null) {
                try {
                    JSONObject jsonObject = new JSONObject(responseMovies);
                    int count = jsonObject.getInt("Count");

                    if(count != 0) {
                        JSONArray list = (JSONArray)jsonObject.getJSONArray("Items");
                        for(int i = 0; i < count; i++) {
                            String imageURI = ((JSONObject)list.get(i)).getJSONObject
                                    ("imageurl").getString("S");
                            String title = ((JSONObject)list.get(i)).getJSONObject("title")
                                    .getString("S");
                            String asin = (((JSONObject) list.get(i)).getJSONObject
                                    ("redirecturl")).getString("S");

                            Video video = new Video(title, imageURI, asin);
                            albumList.add(video);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            String responseMusic = httpHandler.makeServiceCall(urlMusic);

            if(responseMovies != null) {
                try {
                    JSONObject jsonObject = new JSONObject(responseMusic);
                    int count = jsonObject.getInt("Count");

                    if(count != 0) {
                        JSONArray list = (JSONArray)jsonObject.getJSONArray("Items");
                        for(int i = 0; i < count; i++) {
                            String imageURI = ((JSONObject)list.get(i)).getJSONObject
                                    ("imageurl").getString("S");
                            String title = ((JSONObject)list.get(i)).getJSONObject("title")
                                    .getString("S");
                            String asin = (((JSONObject) list.get(i)).getJSONObject
                                    ("redirecturl")).getString("S");

                            Music music = new Music(title, imageURI, asin);
                            albumList.add(music);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //populate weather
            try {
                WeatherInfo weatherInfo = FetchWeather.fetchWeather(getContext(), "Chennai");
                albumList.add(0, weatherInfo);
            } catch (Exception e){
                //supressing all the exceptions
                e.printStackTrace();
            }

            //populate order
            albumList.add(1,new OrderInfo());

            return null;
        }

        private KindleInfo readKindleFile() {

            File root = android.os.Environment.getExternalStorageDirectory();
            File file = new File(root.getAbsolutePath() + "/myData.txt");
            String content = null;
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                content = br.readLine();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            String progress = content.split(",")[1];
            String asin = content.split(",")[0];
            int num = 0;
            if (progress != null) {
                num = Integer.parseInt(progress);
            }
            return new KindleInfo(root.getAbsolutePath() + "/cover", num, asin);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
            pb.setVisibility(View.GONE);
        }
    }

    private boolean checkAppPresent(String packageName) {
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = this.getContext().getPackageManager();
        PackageInfo info;
        try {
            info = pm.getPackageInfo(packageName,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private class SpinnerAdapter {



    }
}
