package com.weij.pic.flowpicture;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.weij.pic.flowpicture.R;
import com.weij.pic.flowpicture.loader.BucketCache;
import com.weij.pic.flowpicture.loader.XMLParser;
import com.weij.pic.flowpicture.views.ImageViewFlipper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NetListView extends Activity {
	// 所有的静态变量
	// XML 节点
	static final String KEY_SONG = "song"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_ARTIST = "artist";
	static final String KEY_DURATION = "duration";
	static final String KEY_THUMB_URL = "thumb_url";
	
	ListView list;
        LazyAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_listview);
		

		Map<String, String> dirs = BucketCache.listOnlyDir1("/picture/");

		

		list=(ListView)findViewById(R.id.list);
		
		
              adapter=new LazyAdapter(this,dirs.entrySet().toArray(new Map.Entry[dirs.size()]));        
              list.setAdapter(adapter);
        

        //为单一列表行添加单击事件

        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(NetListView.this,
						NetGridDir.class);
				
				SharedPreferences indexPrefs = getSharedPreferences(
						"currentDir", MODE_PRIVATE);

				SharedPreferences.Editor indexEditor = indexPrefs.edit();
				String[] str = ((String) parent.getAdapter().getItem(position)).split("/");
				indexEditor.putString("currentDir","/".concat(str[0]).concat("/").concat(str[1]).concat("/").concat(str[2]).concat("/"));
				indexEditor.commit();
				startActivity(intent);
				System.gc();
			}
		});		
	}	
}