package com.weij.pic.flowpicture;

import java.util.Map.Entry;

import com.weij.pic.flowpicture.R;
import com.weij.pic.flowpicture.loader.NetLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private Entry<String, String>[] data;
    private static LayoutInflater inflater=null;
    public NetLoader imageLoader; //用来下载图片的类，后面有介绍
    
    public LazyAdapter(Activity a,Entry<String, String>[] d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new NetLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return data[position].getKey();
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_raw, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // 标题
        TextView artist = (TextView)vi.findViewById(R.id.artist); // 歌手名
        //TextView duration = (TextView)vi.findViewById(R.id.duration); // 时长
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // 缩略图
        
        Entry<String, String> dir = data[position];;
        
        // 设置ListView的相关值
        String[] str = dir.getKey().split("/");
        title.setText(str[2]);
        artist.setText(dir.getValue());
        //duration.setText(song.get(CustomizedListView.KEY_DURATION));
        imageLoader.DisplayImage(dir.getKey(), thumb_image);
        return vi;
    }
}