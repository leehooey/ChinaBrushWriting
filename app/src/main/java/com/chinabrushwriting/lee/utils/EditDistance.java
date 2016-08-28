package com.chinabrushwriting.lee.utils;


import com.chinabrushwriting.lee.domain.PersonInfo;

import java.util.List;

/**
 * Created by Lee on 2016/7/23.
 */
public class EditDistance {
    private static int[] miniPosition = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};

    /**
     * 获取编辑距离
     *
     * @param str1
     * @param str2
     * @return 返回值越小 相似度越高
     */
    public static int getEditDistance(String str1, String str2) {
        int[][] d;
        int n;
        int m;
        int i;
        int j;
        char s_i;
        char t_j;
        int cost;

        n = str1.length();
        m = str2.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];

        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) {
            s_i = str1.charAt(i - 1);
            for (j = 1; j <= m; j++) {
                t_j = str2.charAt(j - 1);

                if (s_i == t_j) {
                    cost = 0;
                } else {
                    cost = 1;
                }

                d[i][j] = miniNum(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost);
            }
        }
        return d[n][m];
    }

    //获取三个值得最小值
    private static int miniNum(int v, int v1, int v2) {
        int mi = v;
        if (v1 < mi) {
            mi = v1;
        }
        if (v2 < mi) {
            mi = v2;
        }
        return mi;
    }


    /**
     * 获取最短编辑距离
     *
     * @param s
     * @return
     */
    public static int getSortEditDistance(String s, List<PersonInfo> mDatas) {
        miniPosition[0] = miniPosition[1] = Integer.MAX_VALUE;
        for (int i = 0; i < mDatas.size(); i++) {
            String dataPinyin = mDatas.get(i).getPinyin();
            //如果数据库中条目的内容包含匹配数据则直接返回位置无需再进行最短编辑距离运算
            if (s.length() < 3) {
                if(s.equals(dataPinyin.substring(0,s.length()))){
                    return i;
                }
            }
            if (s.length() >= 3) {
                if (dataPinyin.contains(s) || s.contains(dataPinyin)) {
                    //数据库中数据长度比较长
                    if (dataPinyin.length() >= s.length()) {
                        for (int j = 0; j < (dataPinyin.length() - s.length()); j++) {
                            if (s.equals(dataPinyin.substring(j, s.length() + j))) {
                                return i;
                            }
                        }
                    } else {
                        for (int j = 0; j < (s.length() - dataPinyin.length()); j++) {
                            if (dataPinyin.equals(s.substring(j, dataPinyin.length() + j))) {
                                return i;
                            }
                        }
                    }
                }
            }
            int index = getEditDistance(s, dataPinyin);

            if (miniPosition[1] > index) {
                miniPosition[1] = index;
                miniPosition[0] = i;
            }
        }
        if (miniPosition[0] != Integer.MAX_VALUE) {
            return miniPosition[0];
        }
        return 0;
    }
}
