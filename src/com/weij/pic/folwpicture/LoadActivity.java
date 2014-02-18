package com.weij.pic.folwpicture;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import cn.waps.AppConnect;

import com.weij.pic.flowpicture.waps.LoadingPopAd;

public class LoadActivity extends Activity {

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.gc();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.gc();
	}

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);
	// 设置界面跳转时间为5秒
	int time = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		AppConnect.getInstance(this);

		// 初始化插屏广告数据
		AppConnect.getInstance(this).initPopAd(this);

		// 开屏广告
		View loadingAdView = LoadingPopAd.getInstance().getContentView(this,
				time);
		if (loadingAdView != null) {
			// 将开屏广告的布局View设置到当前Activity的整体布局中
			this.setContentView(loadingAdView);
		}

		// 设置延时跳转到主界面
		scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(LoadActivity.this,
						MainActivity.class);
				LoadActivity.this.startActivity(intent);
				LoadActivity.this.finish();
				AppConnect.getInstance(LoadActivity.this).close();
			}
		}, time, TimeUnit.SECONDS);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		}
		return true;
	}
}
