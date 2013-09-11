package com.yh.aixiaochu;

import java.util.List;

import com.yh.aixiaochu.alg.run_py;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * @author gudh
 * 
 */
public class GameUtil {
	
	private static int gameFalseTimes = 0;
	
	public static void reset(){
		gameFalseTimes = 0;
	}
	
	/**
	 * ����һ��ɨ����
	 * 
	 * @return
	 */
	public static int run_time() {

		// ��Ļ��ͼ
		Bitmap bm = getScreenBitmap();
		
		if(!isGamePic(bm)){
			gameFalseTimes--;
			Log.d("Judge", "pic is not game screen, now false time is " + gameFalseTimes);
			return gameFalseTimes;
		}
		
		gameFalseTimes = 0;
		// ��ȡ����
		List<int[][]> steps = run_py.get_step(bm);

		if(steps != null){
			// drag����
			boolean res = goSteps(steps, 0);
			if(res){
				gameFalseTimes = 1;
			}
		}
		return gameFalseTimes;
	}

	/**
	 * ��ȡ��Ļ��ͼ��bitmap
	 * 
	 * @return
	 */
	private static Bitmap getScreenBitmap() {
		long s = System.currentTimeMillis();
		String path = SystemUtil.screenCap();
		Log.d("Screen", "screencap " + path);
		Bitmap bm = BitmapFactory.decodeFile(path);
		long e =System.currentTimeMillis();
		Log.i("Time", "getScreenBitmap use " + (e - s));
		return bm;
	}

	/**
	 * �ж��Ƿ������Ϸ״̬���ĸ��Ƕ���0
	 * @param bm
	 * @return
	 */
	private static boolean isGamePic(Bitmap bm){
		int[][] ps = {{0, 0}, {0, 853}, {479, 0}, {479, 853}};
		for(int[] p : ps){
			// ȫ��ɫ
			if(bm.getPixel(p[0], p[1]) != 0xff000000){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * ��������
	 * 
	 * @param steps
	 * @param s
	 *            �������ʱ
	 * @return
	 */
	private static boolean goSteps(List<int[][]> steps, int s) {
		boolean res = false;
		for (int[][] step : steps) {
			try {
				res |= SystemUtil.drag(step[0][0], step[0][1], step[1][0], step[1][1]);
				if (s > 0) {
					Thread.sleep(s);
				}
			} catch (Exception e) {
				res = false;
				e.printStackTrace();
			}
		}
		return res;
	}
}