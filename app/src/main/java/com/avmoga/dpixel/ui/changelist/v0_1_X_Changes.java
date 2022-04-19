package com.avmoga.dpixel.ui.changelist;

import com.avmoga.dpixel.Assets;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.items.Ankh;
import com.avmoga.dpixel.items.food.Blandfruit;
import com.avmoga.dpixel.scenes.ChangesScene;
import com.avmoga.dpixel.sprites.ItemSprite;
import com.avmoga.dpixel.sprites.ItemSpriteSheet;
import com.avmoga.dpixel.sprites.MolotovHuntsmanSprite;
import com.avmoga.dpixel.sprites.RatSprite;
import com.avmoga.dpixel.sprites.TorchHustmanSprites;
import com.avmoga.dpixel.ui.Icons;
import com.avmoga.dpixel.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class v0_1_X_Changes{

    public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
        add_v0_1_0_Changes(changeInfos);
    }

    public static void add_v0_1_0_Changes( ArrayList<ChangeInfo> changeInfos ){

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

        changes2.addButton( new ChangeButton(new Image(new MolotovHuntsmanSprite()), "血月爆破长老",
                "他们曾经是普普通通之人，但由于ZOT的影响，他们已经遁入疯狂，他们会使用炸弹把入侵者全部炸得渣都不剩！\n\n生成位置：17-18层\n\n概率：1~2%"));

        changes2.addButton( new ChangeButton(new Image(new TorchHustmanSprites()), "火把猎人",
                "这些猎人曾经是地表上的一员，由于地牢里面魔力的影响，他们渐渐的失去了理智，他们会将每一个入侵者杀死。\n\n生成位置：矿洞层之内\n\n概率：10~25%"));

        changes2 = new ChangeInfo( Messages.get( ChangesScene.class, "bugfixes"), false, null);
        changes2.hardlight( 0xffff00 );
        changeInfos.add(changes2);

        changes2.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "-1.修复了告示牌BUG" +
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
