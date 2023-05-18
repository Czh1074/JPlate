package com.plate.commonutils;

import toolgood.words.StringSearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * create by chenzhihui on 2022/5/27.
 **/
public class FileUtil {


    /**
     * 根据文件路径获取到list集合
     * @param filePath
     * @return
     */
    public static List<String> readTxt(String filePath) {
        List<String> list = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
