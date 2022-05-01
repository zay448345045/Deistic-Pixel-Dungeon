package com.avmoga.dpixel.ui.changelist;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.scenes.ChangesScene;
import com.avmoga.dpixel.scenes.PixelScene;
import com.avmoga.dpixel.sprites.GhostGnollSprite;
import com.avmoga.dpixel.sprites.HermitCrabSprite;
import com.avmoga.dpixel.sprites.ItemSprite;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.sprites.KingSprite;
import com.avmoga.dpixel.sprites.MrDestructo2dot0Sprite;
import com.avmoga.dpixel.sprites.MrDestructoSprite;
import com.avmoga.dpixel.sprites.RatKingSprite;
import com.avmoga.dpixel.sprites.ShadowYogSprite;
import com.avmoga.dpixel.sprites.SpiderSprite;
import com.avmoga.dpixel.ui.Icons;
import com.avmoga.dpixel.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class v0_1_X_Changes{

    public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
        add_v0_8_0_Changes(changeInfos);
        add_v0_7_0_Changes(changeInfos);
        add_v0_6_0_Changes(changeInfos);
        add_v0_5_0_Changes(changeInfos);
        add_v0_4_0_Changes(changeInfos);
        add_v0_3_0_Changes(changeInfos);
        add_v0_1_0_Changes(changeInfos);
    }

    public static void add_v0_8_0_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("CN-RC", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(0xffff00);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(new MrDestructoSprite(), ("新音乐"),
                "暗影古神层实装\n\n" +
                        "-Music-By-_Prohonor_"));

        changes.addButton( new ChangeButton(new RatKingSprite(), ("新副本"),
                "你居然抢我宝箱？\n\n" +
                        "-_我在英灵殿等着你_"));

        changes.addButton( new ChangeButton(Icons.get(Icons.LANG), "翻译校正",
                "_-_ 校正翻译问题！"));


        changes = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes.hardlight( 0xffff00 );
        changeInfos.add(changes);

        changes.addButton( new ChangeButton((new Image(Assets.SPINNER, 144, 0, 16, 16)), "Bug修复汇总",
                "_-_ 1.修复一些小的崩溃问题\n" +
                        "2.低血量警告声音"));
    }

    public static void add_v0_7_0_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("CN-RCV6", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(0xffff00);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(new MrDestructo2dot0Sprite(), ("新音乐"),
                "主界面音乐优先实装\n\n" +
                        "-Music-By-_Prohonor_"));

        changes.addButton( new ChangeButton(Icons.get(Icons.LANG), "翻译校正",
                "_-_ 校正翻译问题！"));

        changes = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes.hardlight( 0xffff00 );
        changeInfos.add(changes);

        changes.addButton( new ChangeButton((new Image(Assets.SPINNER, 144, 0, 16, 16)), "Bug修复汇总",
                "_-_ 1.修复小偷的崩溃问题"));
    }
    public static void add_v0_6_0_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("CN-RCV3-V5", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(0xffff00);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SANCHIKARAH, null), "暗影古神",
                "_-_ 逃避不能解决任何问题！直面我，英雄们！"));

        changes.addButton( new ChangeButton(Icons.get(Icons.LANG), "翻译校正",
                "_-_ 校正翻译问题！"));

        changes = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes.hardlight( 0xffff00 );
        changeInfos.add(changes);

        changes.addButton( new ChangeButton((new Image(Assets.SPINNER, 144, 0, 16, 16)), "Bug修复汇总",
                "_-_ 1.性能优化"));

    }

    public static void add_v0_5_0_Changes( ArrayList<ChangeInfo> changeInfos ){
        ChangeInfo changes = new ChangeInfo("CN-RCV2", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes= new ChangeInfo( Messages.get( ChangesScene.class, "changes"), false, null);
        changes.hardlight( 0xffff00 );
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.VIAL, null), "露珠瓶",
                "_-_ 修复祝福不使用扣除露珠的问题\n" +
                        "_-_ 优化露珠拾取逻辑"));

        changes.addButton( new ChangeButton(new SpiderSprite(), "宠物属性调整",
                "_-_ 现在追加召唤-移动-恢复-跟随"));

        changes = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes.hardlight( 0xffff00 );
        changeInfos.add(changes);

        changes.addButton( new ChangeButton((new Image(Assets.SPINNER, 144, 0, 16, 16)), "Bug修复汇总",
                "_-_ 1.修复露珠瓶部分特殊情况消失的问题\n" +
                        "_-_ 2.校正部分漏译"));
    }

    public static void add_v0_4_0_Changes( ArrayList<ChangeInfo> changeInfos ){

        ChangeInfo changes = new ChangeInfo("CN-RCV1", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes= new ChangeInfo( Messages.get( ChangesScene.class, "new"), false, null);
        changes.hardlight( 0x00ff00 );
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_BOX, null), "神器宝盒",
                "_-_ 添加神器宝盒翻译"));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.MUSHROOM, null), "伞菌蘑菇",
                "_-_ 添加伞菌蘑菇翻译"));

        changes = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes.hardlight( 0xffff00 );
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_TOOLKIT, null), "Bug修复汇总",
                "_-_ 1.移除高亮模式\n" +
                        "_-_ 2.校正部分漏译"));

    }

            public static void add_v0_3_0_Changes( ArrayList<ChangeInfo> changeInfos ){
        ChangeInfo changes = new ChangeInfo("CN-0.55自然之神的像素地牢", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes= new ChangeInfo( Messages.get( ChangesScene.class, "new"), false, null);
        changes.hardlight( 0x00ff00 );
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_RADIO, null), "人族专属神器-通讯中继器",
                "_-_ 修复了很多原版的问题\n" +
                        "_-_ 正式实装在游戏里面\n" +
                        "_-_ 汉化作者评估：T0神器"));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_SHIELD, null), "矮人专属神器-战神锁链",
                "_-_ 修复了很多原版的问题\n" +
                        "_-_ 正式实装在游戏里面\n" +
                        "_-_ 汉化作者评估：T3神器"));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_WAMULET, null), "幽灵专属神器-幽灵金属护身符",
                "_-_ 修复了很多原版的问题\n" +
                        "_-_ 正式实装在游戏里面\n" +
                        "_-_ 汉化作者评估：T0神器"));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_ROOT, null), "豺狼专属神器-木质雕像",
                "_-_ 修复了很多原版的问题\n" +
                        "_-_ 正式实装在游戏里面\n" +
                        "_-_ 汉化作者评估：T1神器"));

        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), "杂项改动",
                "_-_ 高亮模式回归！！！\n" +
                        "种族全新的选择界面！！！"));

        Image yog = new ShadowYogSprite();
        yog.scale.set(PixelScene.align(0.75f));
        changes.addButton(new ChangeButton(yog, ("暗影古神加强"),
                "为了迎接即将到来的五一劳动节正式版，专属楼层音乐也在紧锣密鼓的制作中……\n\n" +
                        "暗影古神和发芽原版有所不同，小心应对！"));

        changes = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes.hardlight( 0xffff00 );
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), ("修复bug"),
                "1.修复了卷轴烧毁崩溃的问题\n" +
                        "2.其他问题修复\n" +
                        "3.全部翻译！！！理论上！"));
    }


    public static void add_v0_1_0_Changes( ArrayList<ChangeInfo> changeInfos ){


        ChangeInfo changes9 = new ChangeInfo("CN-0.53自然之神的像素地牢", true,
                "");
        changes9.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes9);

        changes9= new ChangeInfo( Messages.get( ChangesScene.class, "new"), false, null);
        changes9.hardlight( 0x00ff00 );
        changeInfos.add(changes9);

        changes9.addButton(new ChangeButton(new GhostGnollSprite(), ("新内容"),
                "1.更多的翻译追加了，翻译总进度95%\n" +
                        "2.除矮人的神器以外，其他的神器均匀修复各种问题\n" +
                        "3.新Buff,爆炸。解决炸弹花的逻辑问题"));

        changes9 = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes9.hardlight( 0xffff00 );
        changeInfos.add(changes9);

        changes9.addButton(new ChangeButton(new KingSprite(), ("修复bug"),
                "1.修复矮人王某些时刻重新喊人血量变满的问题\n" +
                        "2.修复部分楼层崩溃的问题\n" +
                        "3.修复露珠调查员文案缺失问题和悲伤幽灵文本错误问题" +
                        "4.校正错误的露珠文本"));

        ChangeInfo changes8 = new ChangeInfo("CN-0.52自然之神的像素地牢", true,
                "");
        changes8.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes8);

        changes8= new ChangeInfo( Messages.get( ChangesScene.class, "new"), false, null);
        changes8.hardlight( 0x00ff00 );
        changeInfos.add(changes8);

        changes8.addButton(new ChangeButton(new HermitCrabSprite(), ("新内容"),
                "1.素材终于全部修复--感谢_洛小乐_的帮忙\n" +
                        "2.让_幽灵护身符_和_通讯中继器_彻底可用了\n" +
                        "3.更多的翻译追加了！_特别感谢仓鼠_"));

        changes8 = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes8.hardlight( 0xffff00 );
        changeInfos.add(changes8);

        changes8.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16),
                Messages.get(ChangesScene.class, "bugfixes"),
                "-1.修复了一系列Bug……\n" +
                        "-2.其他性能修复"));

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

