package com.weij.pic.folwpicture;


import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.weij.pic.flowpicture.R;
import com.weij.pic.flowpicture.loader.XMLParser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CustomizedListView extends Activity {
	// 所有的静态变量
	static final String URL = "http://api.androidhive.info/music/music.xml";//xml目的地址,打开地址看一下
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
		

		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // 从网络获取xml
		Document doc = parser.getDomElement(xml); // 获取 DOM 节点
		
		NodeList nl = doc.getElementsByTagName(KEY_SONG);
		// 循环遍历所有的歌节点 <song>
		for (int i = 0; i < nl.getLength(); i++) {
			// 新建一个 HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			//每个子节点添加到HashMap关键= >值
			map.put(KEY_ID, parser.getValue(e, KEY_ID));
			map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
			map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
			map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
			map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

			// HashList添加到数组列表
			songsList.add(map);
		}
		

		list=(ListView)findViewById(R.id.list);
		
		
              adapter=new LazyAdapter(this, songsList);        
              list.setAdapter(adapter);
        

        //为单一列表行添加单击事件

        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
							
                        //这里可以自由发挥，比如播放一首歌曲等等
			}
		});		
	}	
}