package com.avmoga.dpixel.Messages;

import java.util.Locale;

public enum Languages {
    ENGLISH("english", "", Status.REVIEWED, null, null),
    CHINESE("中文", "zh", Status.UNREVIEWED, new String[]{"JDSALing"},
            new String[]{"JDSALing", "g2159687-_发芽汉化文本_","Catand-_告示牌翻译_","不败星辰-_杂项翻译_","洛小乐-_角色贴图处理-杂项翻译_","仓鼠-_主要翻译_\n" +
                    "\n_特别鸣谢：\n_" +
                    "-G21" +
                    "的ESPD" +
                    "的汉化参考\n" +
                    "-Catand" +
                    "的Eclipse项目迁移指导\n" +
                    "-小狐狸-九尾天狐的自主捐赠\n"+
                    "-Evan的多语言系统"});

    public enum Status {
        //below 60% complete languages are not added.
        INCOMPLETE, //60-99% complete
        UNREVIEWED, //100% complete
        REVIEWED    //100% reviewed
    }

    private String name;
    private String code;
    private Status status;
    private String[] reviewers;
    private String[] translators;

    Languages(String name, String code, Status status, String[] reviewers, String[] translators) {
        this.name = name;
        this.code = code;
        this.status = status;
        this.reviewers = reviewers;
        this.translators = translators;
    }

    public String nativeName() {
        return name;
    }

    public String code() {
        return code;
    }

    public Status status() {
        return status;
    }

    public String[] reviewers() {
        if (reviewers == null) return new String[]{};
        else return reviewers.clone();
    }

    public String[] translators() {
        if (translators == null) return new String[]{};
        else return translators.clone();
    }

    public static Languages matchLocale(Locale locale) {
        return matchCode(locale.getLanguage());
    }

    public static Languages matchCode(String code) {
        for (Languages lang : Languages.values()) {
            if (lang.code().equals(code))
                return lang;
        }
        return ENGLISH;
    }

}

