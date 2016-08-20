package com.ywg.androidcommon.widget.QuickIndexBar;

import java.util.Comparator;

/**
 * 自定义排序规则：对集合中的中的数据根据A-Z进行排序，前面两个if判断主要是将不是以汉字开头的数据放在后面
 */
public class PinyinComparator implements Comparator<ConatactModel> {

    @Override
    public int compare(ConatactModel lhs, ConatactModel rhs) {
        return sort(lhs, rhs);
    }

    private int sort(ConatactModel lhs, ConatactModel rhs) {
        // 获取ascii值
        int lhs_ascii = lhs.getFirstPinYin().toUpperCase().charAt(0);
        int rhs_ascii = rhs.getFirstPinYin().toUpperCase().charAt(0);
        // 判断若不是字母，则排在字母之后
        if (lhs_ascii < 65 || lhs_ascii > 90)
            return 1;
        else if (rhs_ascii < 65 || rhs_ascii > 90)
            return -1;
        else
            return lhs.getPinYin().compareTo(rhs.getPinYin());
    }
}