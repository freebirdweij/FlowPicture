package com.weij.pic.folwpicture;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.origamilabs.library.views.StaggeredGridView;
import com.weij.pic.flowpicture.R;
import com.weij.pic.flowpicture.views.CircleViewFlow;

public class MainActivity extends Activity {

	int time = 5;
	private StaggeredGridView gview;
	public String[] urls;
	public Integer[] imags;
	public ArrayList<Integer> imaglist = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gview = (StaggeredGridView) findViewById(R.id.gridView1);
		int margin = getResources().getDimensionPixelSize(R.dimen.margin);

		gview.setItemMargin(margin); // set the GridView margin

		gview.setPadding(margin, 0, margin, 0); // have the margin on the sides
												// as well

		Field[] fields = R.drawable.class.getDeclaredFields();
		List<String> list = new ArrayList<String>();
		for (Field field : fields) {
			if (!"ic_launcher".equals(field.getName())&&!"icon".equals(field.getName())) {
				try {
					list.add(String.valueOf(field.getInt(field.getName())));
					imaglist.add(field.getInt(field.getName()));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		urls = new String[list.size()];
		urls = list.toArray(urls);

		StaggeredAdapter adapter = new StaggeredAdapter(MainActivity.this,
				R.id.imageView1, urls);
		gview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		gview.setOnItemClickListener(new StaggeredGridView.OnItemClickListener() {

			@Override
			public void onItemClick(StaggeredGridView parent, View view,
					int position, long id) {
				Intent intent = new Intent(MainActivity.this,
						CircleViewFlow.class);
				Bundle bundle = new Bundle();
				bundle.putInt("position", position);
				bundle.putIntegerArrayList("imaglist", imaglist);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
