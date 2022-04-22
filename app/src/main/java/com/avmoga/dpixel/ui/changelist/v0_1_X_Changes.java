package com.avmoga.dpixel.ui.changelist;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.scenes.ChangesScene;
import com.avmoga.dpixel.ui.Icons;
import com.avmoga.dpixel.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class v0_1_X_Changes{

    public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
        add_v0_1_0_Changes(changeInfos);
    }

    public static void add_v0_1_0_Changes( ArrayList<ChangeInfo> changeInfos ){

        ChangeInfo changes7 = new ChangeInfo("CN-0.50自然之神的像素地牢", true,
                "");
        changes7.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes7);

        changes7 = new ChangeInfo( Messages.get( ChangesScene.class, "new"), false, null);
        changes7.hardlight( 0x00ff00 );
        changeInfos.add(changes7);

        changes7.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
                "-1.更多的翻译被追加了\n" +
                        "-2.Boss血条可视化出现！！！\n" +
                        "-3.简化了英雄属性界面"));

        changes7 = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes7.hardlight( 0xffff00 );
        changeInfos.add(changes7);

        changes7.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16),
                Messages.get(ChangesScene.class, "bugfixes"),
                "-1.修复了一系列Bug,自然作者你出来啊，指定没有你好果子吃\n" +
                        "-2.其他性能修复"));

        ChangeInfo changes6 = new ChangeInfo("CN-0.48自然之神的像素地牢", true,
                "");
        changes6.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes6);

        changes6 = new ChangeInfo( Messages.get( ChangesScene.class, "new"), false, null);
        changes6.hardlight( 0x00ff00 );
        changeInfos.add(changes6);

        changes6.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
                "-1.更多的翻译被追加了\n" +
                        "-2.BGM音乐系统升级，特别感谢无名地牢作者的音乐和破碎地牢的音乐\n" +
                        "-3.修复了图鉴电锯和螃蟹王的湮灭波刃存在的Bug\n" +
                        "-4.挑战现在可以直接选择！\n" +
                        "-5.为棕色蝙蝠死亡尖叫添加了音效（显得更加真实）"));

        changes6 = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes6.hardlight( 0xffff00 );
        changeInfos.add(changes6);

        changes6.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16),
                Messages.get(ChangesScene.class, "bugfixes"),
                "-1.修复了法杖闪退的Bug\n" +
                        "-2.修复了部分界面贴图渲染崩溃的Bug"));

        ChangeInfo changes5 = new ChangeInfo("CN-0.46自然之神的像素地牢", true,
                "");
        changes5.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes5);

        changes5 = new ChangeInfo( Messages.get( ChangesScene.class, "new"), false, null);
        changes5.hardlight( 0x00ff00 );
        changeInfos.add(changes5);

        changes5.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
                "-1.更多的翻译被追加了\n" +
                        "-2.图鉴二次优化\n" +
                        "-3.其他性能处理"));

        changes5 = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes5.hardlight( 0xffff00 );
        changeInfos.add(changes5);

        changes5.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16),
                Messages.get(ChangesScene.class, "bugfixes"),
                "-1.修复了人族贴图Null的Bug\n" +
                        "-2.修复了部分贴图文字的Bug"));

        ChangeInfo changes4 = new ChangeInfo("CN-0.45自然之神的像素地牢", true,
                "");
        changes4.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes4);

        changes4 = new ChangeInfo( Messages.get( ChangesScene.class, "new"), false, null);
        changes4.hardlight( 0x00ff00 );
        changeInfos.add(changes4);

        changes4.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
                "-1.更多的翻译被追加了\n" +
                        "-2.图鉴得到了极大扩展"));

        changes4 = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes4.hardlight( 0xffff00 );
        changeInfos.add(changes4);

        changes4.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16),
                Messages.get(ChangesScene.class, "bugfixes"),
                "-1.修复了天狗层的Bug\n" +
                        "-2.其他性能优化"));

        ChangeInfo changes3 = new ChangeInfo("CN-0.42自然之神的像素地牢", true,
                "");
        changes3.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes3);

        changes3 = new ChangeInfo( Messages.get( ChangesScene.class, "new"), false, null);
        changes3.hardlight( 0x00ff00 );
        changeInfos.add(changes3);

        changes3.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
                "-1.血量和饥饿值现在都能显示数值了\n" +
                        "-2.按照群友要求，不再改游戏内容，一切还原初始。但专精之书开局不能直接获得是我最后的倔强\n" +
                        "-3.更多的翻译被追加了"));

        changes3 = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes3.hardlight( 0xffff00 );
        changeInfos.add(changes3);

        changes3.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16),
                Messages.get(ChangesScene.class, "bugfixes"),
                "-1.修复了气泡文本Bug\n" +
                        "2.新游戏按钮可能导致崩溃得到了修复。也许吧（）"));

        ChangeInfo changes2 = new ChangeInfo("CN-0.4自然之神的像素地牢", true,
                "");
        changes2.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes2);

        changes2 = new ChangeInfo( Messages.get( ChangesScene.class, "new"), false, null);
        changes2.hardlight( 0x00ff00 );
        changeInfos.add(changes2);

        changes2.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
                "-1.快捷栏现在最多4个\n" +
                        "-2.更多的汉化加入\n" +
                        "-3.所有英雄开局获得3瓶小型治疗药水和250金币\n" +
                        "-4.更多的贴图被矫正"));

        changes2 = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes2.hardlight( 0xffff00 );
        changeInfos.add(changes2);

        changes2.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "-1.修复了告示牌BUG\n" +
                        "2.越界BUG处理"));

        ChangeInfo changes = new ChangeInfo("CN-0.36自然之神的像素地牢", true,
                "1.修复作者在种族之书阅读长按进入测试模式的效果" +
                "(血量999，力量999)\n" +
                "2.商人贩卖逻辑和6层一致，十字架必定刷新\n" +
                "3.翻译信息优化\n" +
                "4.开局自带种子袋");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        ChangeInfo changes1 = new ChangeInfo("CN-0.3.X.自然之神的像素地牢", true,
                "-汉化了药水和卷轴，有一些新卷轴还没有汉化\n" +
                "-对开局界面滚动卡顿修复\n" +
                "-血量过低屏幕现在会闪烁红色作为警告\n" +
                "-添加了饥饿度的动态显示\n" +
                "-添加了Buff点击可查看详情界面\n" +
                "-优化了部分UI,对一些界面进行了优化\n" +
                "-更换了更多的原BitMapText依赖项\n" +
                "_v0.1.7-0.2自然之神的像素地牢_\n" +
                "-预添加了更多的贴图文本渲染转字体渲染\n" +
                "-战士的豺狼种族贴图人试修复\n" +
                "_v0.1.7-0.1自然之神的像素地牢_\n" +
                "-汉化了界面等东西，但总体说来并没有太多汉化\n" +
                "-迁移了多语言系统\n" +
                "-优化和修复了一些问题\n" +
                "-添加了崩溃日志收录报告\n" +
                "_已知特性BUG(属于地牢框架问题，无法修复）_\n" +
                "1.退出游戏的时候偶尔贴图混乱解决方法：（重启游戏）\n" +
                "2.不要随意切换语言。解决方法：（同上所述）\n" +
                "3.切换横竖屏的时候可能崩溃。解决方法：（同上所述）\n");
        changes1.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes1);

    }

}

