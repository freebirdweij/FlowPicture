package com.weij.pic.flowpicture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weij.pic.flowpicture.R;
import com.weij.pic.flowpicture.loader.ImageLoader;
import com.weij.pic.flowpicture.loader.NetLoader;
import com.weij.pic.flowpicture.views.ScaleImageView;

public class GridDirAdapter extends ArrayAdapter<String> {

	private NetLoader mLoader;

	public GridDirAdapter(Context context, int textViewResourceId,
			String[] objects) {
		super(context, textViewResourceId, objects);
		mLoader = new NetLoader(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			convertView = layoutInflator.inflate(R.layout.dir_grid_view,
					null);
			holder = new ViewHolder();
			holder.imageView = (ScaleImageView) convertView
					.findViewById(R.id.dirImage);
			holder.textView = (TextView) convertView
					.findViewById(R.id.dirText);
	        String[] str = getItem(position).split("/");
			holder.textView.setText(str[3]);
			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();

		mLoader.DisplayImage(getItem(position), holder.imageView);

		return convertView;
	}

	static class ViewHolder {
		ScaleImageView imageView;
		TextView textView;
	}
}
