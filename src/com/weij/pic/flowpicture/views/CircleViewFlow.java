package com.weij.pic.flowpicture.views;

import java.util.ArrayList;

import org.taptwo.android.widget.ViewFlow;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import cn.waps.AppConnect;
import cn.waps.MiniAdView;
import cn.waps.UpdatePointsNotifier;

import com.weij.pic.flowpicture.R;

public class CircleViewFlow extends Activity implements View.OnClickListener,
		UpdatePointsNotifier {

	private ViewFlow viewFlow;
	private Integer[] images;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTitle(R.string.circle_title);
		setContentView(R.layout.circle_layout);
		// 初始化统计器，需要在AndroidManifest中注册WAPS_ID和WAPS_PID值
		//AppConnect.getInstance(this);
		// 以上两种统计器初始化方式任选其一，不要同时使用

		// 使用自定义的OffersWebView
		//AppConnect.getInstance(this)
		//		.setAdViewClassName("cn.waps.OffersWebView");

		// 禁用错误报告
		//AppConnect.getInstance(this).setCrashReport(false);
		// 初始化自定义广告数据
		//AppConnect.getInstance(this).initAdInfo();

		// 初始化插屏广告数据
		//AppConnect.getInstance(this).initPopAd(this);
		// 互动广告调用方式
		// LinearLayout container = (LinearLayout)
		// findViewById(R.id.AdLinearLayout);
		// new AdView(this, container).DisplayAd();
		// 迷你广告调用方式
		AppConnect.getInstance(this).setAdBackColor(
				Color.argb(50, 120, 240, 120));// 设置迷你广告背景颜色
		AppConnect.getInstance(this).setAdForeColor(Color.BLUE);// 设置迷你广告文字颜色
		LinearLayout miniLayout = (LinearLayout) findViewById(R.id.miniAdLinearLayout);
		new MiniAdView(this, miniLayout).DisplayAd(10);// 10秒刷新一次

		Intent intent = getIntent();
		int position = intent.getIntExtra("position", 0);
		ArrayList<Integer> result = intent.getIntegerArrayListExtra("imaglist");
		images = new Integer[result.size()];
		images = result.toArray(images);
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		ImageAdapter adapter = new ImageAdapter(this);
		adapter.setImages(images);
		viewFlow.setAdapter(adapter, position);
	}

	/*
	 * If your min SDK version is < 8 you need to trigger the
	 * onConfigurationChanged in ViewFlow manually, like this
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		viewFlow.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onDestroy() {
		// 释放资源，原finalize()方法名修改为close()
		AppConnect.getInstance(this).close();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void getUpdatePoints(String arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getUpdatePointsFailed(String arg0) {
		// TODO Auto-generated method stub

	}
}