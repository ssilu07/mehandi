package com.android.mehndidesigns.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.android.mehndidesigns.MehndiList;
import com.android.mehndidesigns.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private final Context ctx;
    private final List<String> sliderList;

    public SliderAdapter(Context ctx) {
        this.ctx = ctx;
        sliderList = new MehndiList().getSlider();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        @SuppressLint("InflateParams")
        View sliderLayout = LayoutInflater.from(ctx).inflate(R.layout.slider,null);
        ImageView image = sliderLayout.findViewById(R.id.my_featured_image);
        TextView title = sliderLayout.findViewById(R.id.my_caption_title);
        try {
            InputStream ims = ctx.getAssets().open("img/" + sliderList.get(position));
            Drawable d = Drawable.createFromStream(ims, null);
            image.setImageDrawable(d);ims.close();title.setText("");
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        container.addView(sliderLayout);
        return sliderLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return sliderList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
