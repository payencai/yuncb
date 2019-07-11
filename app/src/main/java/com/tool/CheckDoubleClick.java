package com.tool;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：凌涛 on 2019/3/1 12:11
 * 邮箱：771548229@qq..com
 */
public class CheckDoubleClick {
    private static Map<String, Long> records = new HashMap<>();

    public static boolean isFastDoubleClick() {
        if (records.size() > 1000) {
            records.clear();
        }

        //本方法被调用的文件名和行号作为标记
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        String key = ste.getFileName() + ste.getLineNumber();

        Long lastClickTime = records.get(key);
        long thisClickTime = System.currentTimeMillis();
        records.put(key, thisClickTime);
        if (lastClickTime == null) {
            lastClickTime = 0L;
        }
        long timeDuration = thisClickTime - lastClickTime;
        return 0 < timeDuration && timeDuration < 500;
    }
}
