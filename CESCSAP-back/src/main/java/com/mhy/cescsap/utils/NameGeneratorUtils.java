package com.mhy.cescsap.utils;

//import static javax.swing.text.html.parser.DTDConstants.NAMES;

import java.util.Random;

public class NameGeneratorUtils {

    private static final String[] NAMES = {
            "张伟", "王芳", "李强", "赵敏", "陈静", "刘磊", "杨丽", "黄军", "周勇", "吴杰",
            "徐涛", "孙明", "马超", "朱波", "胡刚", "郭辉", "何鹏", "高华", "林平", "罗亮",
            "郑建", "梁俊", "谢浩", "宋宇", "唐峰", "许斌", "韩凯", "冯晨", "邓阳", "曹林",
            "彭鑫", "曾帅", "肖成", "田翔", "董龙", "袁瑞", "潘岩", "于洋", "蒋彬", "蔡旭",
            "余诚", "杜宏", "叶达", "程康", "苏震", "魏哲", "吕宁", "丁栋", "任庆", "沈航",
            "姚泽", "卢越", "姜皓", "崔凡", "钟芳", "谭丽", "陆强", "汪敏", "范静", "金磊",
            "石军", "廖勇", "贾杰", "夏涛", "韦明", "付超", "方波", "白刚", "邹辉", "孟鹏",
            "熊华", "秦平", "邱亮", "江建", "尹俊", "薛浩", "闫宇", "段峰", "雷斌", "侯凯",
            "龙晨", "史阳", "陶林", "黎鑫", "贺帅", "顾成", "毛翔", "郝龙", "龚瑞", "邵岩",
            "万洋", "钱彬", "严旭", "覃诚", "武宏", "戴达", "莫康", "孔震", "欧阳哲", "上官宁"
    };

    // 线程安全的随机数生成器
    private static final Random random = new Random();

    /**
     * 获取随机中文姓名
     * @return 随机姓名字符串
     */
    public static String getRandomName() {
        // 生成 0 到 (数组长度-1) 的随机数
        int index = random.nextInt(NAMES.length);
        return NAMES[index];
    }

    // 可选：使用更高性能的 ThreadLocalRandom（Java 7+）
    /*
    public static String getRandomName() {
        int index = ThreadLocalRandom.current().nextInt(NAMES.length);
        return NAMES[index];
    }
    */
}
