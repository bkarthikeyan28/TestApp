package com.example.baskara.customlauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Data> dataList;


    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView title, description, ctype;

        public ArticleViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            thumbnail = view.findViewById(R.id.thumbnail);
            ctype = view.findViewById(R.id.ctype);
            title.setTypeface(Typeface.DEFAULT_BOLD);
            title.setTextSize(20);
            description.setTextSize(15);
            setGoogleSansBold(title);
            setGoogleSans(description);
            setGoogleSans(ctype);
        }
    }


    public class KindleViewHolder extends RecyclerView.ViewHolder {
        public ImageView bookCover;
        public TextView progress;

        public KindleViewHolder(View view) {
            super(view);
            progress = view.findViewById(R.id.progress);
            bookCover = view.findViewById(R.id.bookCover);
            progress.setTypeface(Typeface.DEFAULT_BOLD);
            progress.setTextSize(20);
            setGoogleSansBold(progress);
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView title,ctype1;

        public VideoViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.description);
            thumbnail = view.findViewById(R.id.thumbnail);
            title.setTextSize(15);
            title.setTypeface(Typeface.DEFAULT_BOLD);
            setGoogleSansBold(title);
            ctype1 = view.findViewById(R.id.ctype1);
            setGoogleSans(ctype1);
        }
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView cityField;
        TextView updatedField;
        TextView detailsField;
        TextView currentTemperatureField;
        TextView weatherIcon;

        public WeatherViewHolder(View view) {
            super(view);
            cityField = view.findViewById(R.id.city_field);
            detailsField = view.findViewById(R.id.details_field);
            currentTemperatureField = view.findViewById(R.id.current_temperature_field);

            setGoogleSansBold(cityField, currentTemperatureField);
            setGoogleSans(detailsField);
        }
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView title,ctype1;

        public MusicViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.description);
            thumbnail = view.findViewById(R.id.thumbnail);
            title.setTextSize(15);
            title.setTypeface(Typeface.DEFAULT_BOLD);
            setGoogleSansBold(title);
            ctype1 = view.findViewById(R.id.ctype1);
            setGoogleSans(ctype1);
        }
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public OrderViewHolder(View view) {
            super(view);
        }
    }


    public AlbumsAdapter(Context mContext, List<Data> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        if(viewType == 0) {
             itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_card, parent, false);
            return new ArticleViewHolder(itemView);
        } else if(viewType == 2) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_card,
                    parent, false);
            return new VideoViewHolder(itemView);
        }
        else if(viewType == 1) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.kindle_card, parent, false);
            return new KindleViewHolder(itemView);
        }
        else if(viewType == 4) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.weather_card, parent, false);
            return new WeatherViewHolder(itemView);
        }
        else if(viewType == 6) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_card, parent, false);
            return new OrderViewHolder(itemView);
        } else if(viewType == 10) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.music_card, parent, false);
            return new MusicViewHolder(itemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ArticleViewHolder) {
            final Article data = (Article)dataList.get(position);
            ((ArticleViewHolder) holder).title.setText(data.getTitle());
            ((ArticleViewHolder) holder).description.setText(data.getDescription());

            Glide.with(mContext)
                    .load(data.getThumbnail())
                    .into(((ArticleViewHolder) holder).thumbnail);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //intent.setComponent(new ComponentName("com.amazon.avod.thirdpartyclient",
                      //      "com.amazon.avod.client.activity.LauncherActivity"));


                    //intent.setComponent(new ComponentName("com.amazon.mp3", "com.amazon" +
                            //".mp3.client.activity.LauncherActivity"));

                    //intent.setComponent(new ComponentName("com.amazon.mShop.android.shopping",
                     //       "com.amazon.mShop.order.ViewOrderActivity"));


                    //intent.setComponent(new ComponentName("com.amazon.kindle", ""));

                    //Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
                      //      ("market://details?id=com.amazon.kindle"));
                    //mContext.startActivity(marketIntent);
                    Intent intent = new Intent(mContext, WebviewActivity.class);
                    intent.putExtra("asin", data.getAsin());
                    mContext.startActivity(intent);
                }
            });

        }
        else if (holder instanceof VideoViewHolder) {
            final Video data = (Video)dataList.get(position);
            ((VideoViewHolder)holder).title.setText(data.getTitle());
            Glide.with(mContext)
                    .load(data.getImageUri())
                    .into(((VideoViewHolder) holder).thumbnail);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkAppPresent("com.amazon.avod.thirdpartyclient")) {
                        Intent intent = new Intent();
                        //intent.setData(Uri.parse(uri));
                        intent.setComponent(new ComponentName("com.amazon.avod" +
                                ".thirdpartyclient", "com.amazon.avod.thirdpartyclient.ThirdPartyPlaybackActivity"));
                        intent.putExtra("asin", data.getAsin());
                        mContext.startActivity(intent);
                    } else {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
                                ("market://details?id=com.amazon.avod.thirdpartyclient"));
                        mContext.startActivity(marketIntent);
                    }
                }
            });
        }
        else if(holder instanceof KindleViewHolder) {
            final KindleInfo data = (KindleInfo) dataList.get(position);
            ((KindleViewHolder) holder).progress.setText(Integer.toString(data.getProgress()) + "%");

            Glide.with(mContext)
                    .load(data.getBookCover())
                    .into(((KindleViewHolder) holder).bookCover);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WebviewActivity.class);
                    intent.putExtra("asin", data.getAsin());
                    mContext.startActivity(intent);
                }
            });

        }
        else if(holder instanceof WeatherViewHolder) {
            final WeatherInfo data = (WeatherInfo) dataList.get(position);
            ((WeatherViewHolder) holder).cityField.setText(data.getCityField());
            ((WeatherViewHolder) holder).currentTemperatureField.setText(data.getCurrentTemperatureField());
            ((WeatherViewHolder) holder).detailsField.setText(data.getDetailsField());
        }
        else if(holder instanceof OrderViewHolder) {
            final OrderInfo data = (OrderInfo) dataList.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkAppPresent("com.amazon.mShop.android.shopping")) {
                        Intent intent = new Intent();
                        //intent.setData(Uri.parse(uri));
                        intent.setComponent(new ComponentName("com.amazon.mShop.android.shopping",
                                "com.amazon.mShop.splashscreen.StartupActivity"));
                        mContext.startActivity(intent);
                    } else {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
                                ("market://details?id=com.amazon.mShop.android.shopping"));
                        mContext.startActivity(marketIntent);
                    }
                }
            });
        } else if(holder instanceof MusicViewHolder) {
            final Music music = (Music)dataList.get(position);
            ((MusicViewHolder)holder).title.setText(music.getTitle());
            Glide.with(mContext)
                    .load(music.getImageUri())
                    .into(((MusicViewHolder) holder).thumbnail);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkAppPresent("com.amazon.mp3")) {
                        Intent intent = new Intent();
                        //intent.setData(Uri.parse(uri));
                        intent.setComponent(new ComponentName("com.amazon.mp3",
                                "com.amazon.mp3.client.activity.LauncherActivity"));
                        mContext.startActivity(intent);
                    } else {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
                                ("market://details?id=com.amazon.mp3"));
                        mContext.startActivity(marketIntent);
                    }

                }
            });
        }
    }

    private void setGoogleSans(TextView... views) {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/googlesans_regular" +
                ".ttf");
        for (TextView view : views) {
            if (view != null) {
                view.setTypeface(tf);
            }
        }
    }

    private void setGoogleSansBold(TextView... views) {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/googlesans-bold" +
                ".ttf");
        for (TextView view : views) {
            if (view != null) {
                view.setTypeface(tf);
            }
        }
    }

    private boolean checkAppPresent(String packageName) {
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = mContext.getPackageManager();
        PackageInfo info;
        try {
            info = pm.getPackageInfo(packageName,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
