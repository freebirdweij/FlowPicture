package com.weij.pic.flowpicture;

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

public class NetGridDir extends Activity {

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
		imaglist = null;
		System.gc();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		imaglist = null;
		System.gc();
	}

	public ArrayList<String> imaglist = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_gridview);

		StaggeredGridView gview = (StaggeredGridView) findViewById(R.id.netGridDir);
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
		Map<String, Integer> dirs = BucketCache.listOnlyDir2(currentDir);
		
		String[] urls = new String[dirs.size()];
		urls = dirs.keySet().toArray(urls);

		// DisplayMetrics dm = getResources().getDisplayMetrics();
		// gview.setLayoutParams(new
		// RelativeLayout.LayoutParams(dm.widthPixels,LayoutParams.MATCH_PARENT));
		// gview.setColumnCount(2);

		GridDirAdapter adapter = new GridDirAdapter(NetGridDir.this, R.id.dirText, urls);

		gview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		System.gc();

		gview.setOnItemClickListener(new StaggeredGridView.OnItemClickListener() {

			@Override
			public void onItemClick(StaggeredGridView parent, View view,
					int position, long id) {
				Intent intent = new Intent(NetGridDir.this,
						NetGridView.class);
				
				SharedPreferences indexPrefs = getSharedPreferences(
						"currentDir", MODE_PRIVATE);

				SharedPreferences.Editor indexEditor = indexPrefs.edit();
				String[] str = ((String) parent.getAdapter().getItem(position)).split("/");
				indexEditor.putString("currentDir","/".concat(str[0]).concat("/").concat(str[1]).concat("/").concat(str[2]).concat("/").concat(str[3]).concat("/"));
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
