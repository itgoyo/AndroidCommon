package com.ywg.androidcommon.widget.QuickIndexBar;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mgx on 2016/6/30.
 */
public class PinyinUtil {

    private static List<String> FirstLetter;

    public static List<ConatactModel> parsePinyin(String[] contacts)
    {
        List<ConatactModel> modelList = new ArrayList<>();
        //用户来存储出现的拼音首字母
        FirstLetter = new ArrayList<>();
        for (int i = 0; i < contacts.length; i++)
        {
            ConatactModel model = new ConatactModel();
            model.setName(contacts[i]);

            //将当前字符串转化为相应的拼音
            String mStrPinyin = getPingYin(contacts[i]);
            //然后截取第一个拼音字母，并且转换为大写字母
            String mStrFirstLetter = mStrPinyin.substring(0, 1).toUpperCase();

            if (mStrFirstLetter.matches("[A-Z]"))
            {
                model.setFirstPinYin(mStrFirstLetter);
                //判断集合里面是否已经存在了该首字母，如果不存在就将其保存下来
                if (!FirstLetter.contains(mStrFirstLetter))
                {
                    FirstLetter.add(mStrFirstLetter);
                }
            }
            modelList.add(model);
        }
        //最后统一对集合里面的首字母进行排序
        Collections.sort(FirstLetter);
        setFirstLetter(FirstLetter);
        return modelList;
    }

    /**
     * 将字符串中的中文转化为拼音,英文字符不变
     *
     * @param inputString 汉字
     * @return
     */
    public static String getPingYin(String inputString)
    {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String output = "";
        if (inputString != null && inputString.length() > 0 && !"null".equals(inputString))
        {
            char[] input = inputString.trim().toCharArray();
            try
            {
                for (int i = 0; i < input.length; i++)
                {
                    if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+"))
                    {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                        output += temp[0];
                    } else
                    {
                        output += Character.toString(input[i]);
                    }
                }
            } catch (BadHanyuPinyinOutputFormatCombination e)
            {
                e.printStackTrace();
            }
        } else
        {
            return "*";
        }
        return output;
    }

    public static List<String> getFirstLetter()
    {
        return FirstLetter;
    }

    public static void setFirstLetter(List<String> firstLetter)
    {
        FirstLetter = firstLetter;
    }
}