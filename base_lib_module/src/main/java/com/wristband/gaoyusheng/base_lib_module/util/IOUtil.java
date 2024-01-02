package com.wristband.gaoyusheng.base_lib_module.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by gaoyusheng on 17/9/16.
 */

public class IOUtil {
    public static void closeAll(Closeable... closeables){
        if(closeables == null){
            return;
        }
        for (Closeable closeable : closeables) {
            if(closeable!=null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
