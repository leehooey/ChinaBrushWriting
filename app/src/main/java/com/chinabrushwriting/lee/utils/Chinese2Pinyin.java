package com.chinabrushwriting.lee.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by Lee on 2016/7/22.
 */
public class Chinese2Pinyin {

    //将汉字转化成pinyin
    public static String getPinyinFromChinese(String chinese) {
        StringBuffer sb = new StringBuffer();
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//不带声调

        char[] chars = chinese.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            //跳过空格
            if(Character.isWhitespace(chars[i])){
                continue;
            }
            //可能是汉字，解析汉字
            if (chars[i] > 127) {
                try {
                    char c = chars[i];
                    //因为一个汉字可能含有多音字
                    String[] pinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(c, outputFormat);
                    //这里只将多音字的数组中排位靠前的输出
                    sb.append(pinyinStringArray[0]);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            } else {
                //不是汉子直接拼接
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }
}
