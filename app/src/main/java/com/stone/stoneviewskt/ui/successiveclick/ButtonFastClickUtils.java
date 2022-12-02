package com.stone.stoneviewskt.ui.successiveclick;

import android.util.Log;

public class ButtonFastClickUtils {
    private static long lastClickTime = 0;
    private static long DIFF = 1500;
    private static int lastButtonId = -1;

    /**
     * 判断两次点击的间隔，如果小于1500，则认为是多次无效点击;
     *
     * 快速连续点击时， 每超过 间隔1500ms，就会被视为新的一次
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于1500，则认为是多次无效点击
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     */
    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            Log.v("isFastDoubleClick", "短时间内按钮多次触发");
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }

}
