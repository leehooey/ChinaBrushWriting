package com.chinabrushwriting.lee.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Lee on 2016/7/31.
 */
public class IOUtils {
    /**
     * 所有的流都继承自Closeable
     * @param io
     */
    public static void closeIO(Closeable io) {
        try {
            if (io != null) {
                io.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
