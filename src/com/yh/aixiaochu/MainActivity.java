package com.yh.aixiaochu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yh.aixiaochu.R;
import com.yh.aixiaochu.system.PhoneInfo;
import com.yh.aixiaochu.system.Screenshot;
import com.yh.aixiaochu.system.PhoneInfo.PHONE;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 设置可截屏
		getWindow().getDecorView().setDrawingCacheEnabled(true);

		PhoneInfo.init(PHONE.miOne);
		
		// 初始化系统工具
		SystemUtil.init();
		Screenshot.init(this);
		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				while (true) {
//					String[] click = EventGetter.getDragEvents(518, 543, 100, 545);
//					Sendevent.onEvent(click);
//					try {
//						Thread.sleep(2000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}) {
//		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public GamePlay plays = new GamePlay();
	
	public void btnClick(View view) {
		TextView s = (TextView) findViewById(R.id.statusView);
		String status = s.getText().toString();
		if (status.equals(getResources().getString(R.string.runtext))) {
			// 正在运行切换到不运行
			// stopService(new Intent(this, GamePlayService.class));
			if(plays != null){
				plays.stopPlay();
			}
			s.setText(getResources().getString(R.string.noruntext));
		} else {
			// 未运行切花到运行
			// startService(new Intent(this, GamePlayService.class));
			plays = new GamePlay();
			plays.start();
			s.setText(getResources().getString(R.string.runtext));
		}
	}
	

	/**
	 * 菜单选择处理
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_exit:
			if(plays != null){
				plays.stopPlay();
			}
			this.finish();
			System.exit(0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
