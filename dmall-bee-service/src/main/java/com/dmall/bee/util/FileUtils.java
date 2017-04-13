package com.dmall.bee.util;

import java.io.File;

public class FileUtils {
	/**
     * 删除非空目录
     * @param dir
     * @return
     */
    public static boolean deleteNotDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteNotDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
 
        // The directory is now empty so now it can be smoked
        return dir.delete();
    }
    
}
