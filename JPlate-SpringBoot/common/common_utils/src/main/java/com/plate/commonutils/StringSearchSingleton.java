package com.plate.commonutils;

import toolgood.words.StringSearch;

import java.util.List;

/**
 * create by chenzhihui on 2022/5/27.
 **/
public class StringSearchSingleton {
    private StringSearch iwords;
    // 声明私有对象
    private static StringSearchSingleton instance = new StringSearchSingleton();

    // 获取实例（单例对象）
    public static StringSearchSingleton instance() {
        return instance;
    }

    private StringSearchSingleton() {
        String path = Thread.currentThread().getContextClassLoader().getResource("SpamWords.min.txt").getPath();
        List<String> list = FileUtil.readTxt(path);
        iwords = new StringSearch();
        iwords.SetKeywords(list);
    }

    /**
     * 判断是否存在敏感词
     *
     * @param txt
     * @return
     */
    public Boolean judgeSensitivityWord(String txt) {
        return iwords.ContainsAny(txt);
    }

    /**
     * 敏感词过滤用*替代
     *
     * @param txt     内容
     * @param replace 敏感词替代字符 '*'
     * @return
     */
    public String filterSensitivityWord(String txt, char replace) {
        return iwords.Replace(txt, replace);
    }
}
