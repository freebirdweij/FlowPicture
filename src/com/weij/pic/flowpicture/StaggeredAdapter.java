package com.weij.pic.flowpicture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.weij.pic.flowpicture.R;
import com.weij.pic.flowpicture.loader.ImageLoader;
import com.weij.pic.flowpicture.views.ScaleImageView;

public class StaggeredAdapter extends ArrayAdapter<String> {

	private ImageLoader mLoader;

	public StaggeredAdapter(Context context, int textViewResourceId,
			String[] objects) {
		super(context, textViewResourceId, objects);
		mLoader = new ImageLoader(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			convertView = layoutInflator.inflate(R.layout.row_staggered_demo,
					null);
			holder = new ViewHolder();
			holder.imageView = (ScaleImageView) convertView
					.findViewById(R.id.imageView1);
			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();

		mLoader.DisplayImage(getItem(position), holder.imageView);

		return convertView;
	}

	static class ViewHolder {
		ScaleImageView imageView;
	}
}
