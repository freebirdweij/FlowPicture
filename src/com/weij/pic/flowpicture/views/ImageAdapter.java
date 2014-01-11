/*
 * Copyright (C) 2011 Patrik �kerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weij.pic.flowpicture.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.polites.android.GestureImageView;
import com.weij.pic.flowpicture.R;

public class ImageAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Integer[] images;

	public Integer[] getImages() {
		return images;
	}

	public void setImages(Integer[] images) {
		this.images = images;
	}

	public ImageAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.image_item, null);
		}
		GestureImageView gimagv = (GestureImageView) convertView
				.findViewById(R.id.imgView);
		gimagv.setImageResource(images[position]);
		parent.setLayoutParams(new LinearLayout.LayoutParams(parent
				.getLayoutParams().width, gimagv.getImageHeight()));

		return convertView;
	}

}
