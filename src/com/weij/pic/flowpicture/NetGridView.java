package com.weij.pic.flowpicture;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.origamilabs.library.views.StaggeredGridView;
import com.weij.pic.flowpicture.R;
import com.weij.pic.flowpicture.loader.BucketCache;
import com.weij.pic.flowpicture.views.ImageViewFlipper;
import com.weij.pic.flowpicture.views.NetViewFlipper;

public class NetGridView extends Activity {

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.gc();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		
		System.gc();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		System.gc();
	}

	public String[] urls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		StaggeredGridView gview = (StaggeredGridView) findViewById(R.id.gridView1);
		int margin = getResources().getDimensionPixelSize(R.dimen.margin);

		gview.setItemMargin(margin); // set the GridView margin

		gview.setPadding(margin, 0, margin, 0); // have the margin on the sides
												// as well

		String currentDir = "";
		SharedPreferences indexPrefs = getSharedPreferences("currentDir",
				MODE_PRIVATE);
		if (indexPrefs.contains("currentDir")) {
			currentDir = indexPrefs.getString("currentDir", "");
		}
		Map<String, Long> objs = BucketCache.listOnlyObjectByDir(currentDir);
		
		urls = new String[objs.size()];
		objs.keySet().toArray(urls);

		// DisplayMetrics dm = getResources().getDisplayMetrics();
		// gview.setLayoutParams(new
		// RelativeLayout.LayoutParams(dm.widthPixels,LayoutParams.MATCH_PARENT));
		// gview.setColumnCount(2);

		NetViewAdapter adapter = new NetViewAdapter(NetGridView.this, R.id.imageView1, urls);

		gview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		System.gc();

		gview.setOnItemClickListener(new StaggeredGridView.OnItemClickListener() {

			@Override
			public void onItemClick(StaggeredGridView parent, View view,
					int position, long id) {
				Intent intent = new Intent(NetGridView.this,
						NetViewFlipper.class);
				Bundle bundle = new Bundle();
				SharedPreferences indexPrefs = getSharedPreferences(
						"currentIndex", MODE_PRIVATE);

				bundle.putStringArray("urls", urls);
				intent.putExtras(bundle);
				SharedPreferences.Editor indexEditor = indexPrefs.edit();
				indexEditor.putInt("currentIndex", position);
				indexEditor.commit();
				startActivity(intent);
				System.gc();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
