package com.avmoga.dpixel.scenes;

import com.avmoga.dpixel.Chrome;
import com.avmoga.dpixel.Messages.Messages;
import com.avmoga.dpixel.ShatteredPixelDungeon;
import com.avmoga.dpixel.ui.Archs;
import com.avmoga.dpixel.ui.ExitButton;
import com.avmoga.dpixel.ui.RenderedTextMultiline;
import com.avmoga.dpixel.ui.ScrollPane;
import com.avmoga.dpixel.ui.Window;
import com.watabou.noosa.Camera;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Component;

public class ChangesScene extends PixelScene {

    private static final String TXT_Update =
            "\n_v0.1.7-0.3自然之神的像素地牢_\n" +
                    "- 汉化了药水和卷轴，有一些新卷轴还没有汉化\n" +
                    "- 对开局界面滚动卡顿修复\n" +
                    "- 血量过低屏幕现在会闪烁红色作为警告\n" +
                    "- 添加了饥饿度的动态显示\n" +
                    "- 添加了Buff点击可查看详情界面\n" +
                    "- 优化了部分UI,对一些界面进行了优化\n" +
                    "- 更换了更多的原BitMapText依赖项\n" +
            "\n\n_v0.1.7-0.2自然之神的像素地牢_\n" +
                    "- 预添加了更多的贴图文本渲染转字体渲染\n" +
                    "- 战士的豺狼种族贴图人试修复" +
            "\n\n_v0.1.7-0.1自然之神的像素地牢_\n" +
                    "- 汉化了界面等东西，但总体说来并没有太多汉化\n" +
                    "- 迁移了多语言系统\n" +
                    "- 优化和修复了一些问题\n" +
                    "- 添加了崩溃日志收录报告\n\n" +
                    "_已知特性BUG(属于地牢框架问题，无法修复）_\n" +
                    "1.退出游戏的时候偶尔贴图混乱 解决方法：（重启游戏）\n" +
                    "2.不要随意切换语言。解决方法：（同上所述）\n" +
                    "3.切换横屏或者竖屏的时候可能崩溃。解决方法：（同上所述）\n\n" +
                    "_=======下方为群内不知名人士总结的经验=======_\n\n" +
                    "Gnoll（豺狼人）:\n" +
                    "  出生自带一张Scroll of regrowth(再生卷轴，全图能长草的地方都长草，有地图卷轴的效果，在远古关卡开有奇效)\n" +
                    "  很脆弱，饥饿受到更多的伤害\n" +
                    "专属神器：Wooden Effigies,制造一个蘑菇样的地雷踩到的敌人（包括自己）会受到伤害，可以无限种  " +
                    "（你可以在一回合内种满所有能种的位置，只要你够无聊）形状如同破碎里用草杖种出来的种子包\n" +
                    "   另外可以感应到所有踩在草地上的敌人（门上也可以）\n" +
                    "种族转职：1可以采草获得回血baff（很强）\n" +
                    "          2附近敌人越多伤害越高\n" +
                    "\n" +
                    "Human（人类）\n" +
                    "  能够使用其他所有种族的特殊道具的基本功能（只能用被动技能）\n" +
                    "  体力上限会比其他种族少2\n" +
                    "专属神器：通信器，花钱召唤出类似于分身的镜像，攻击一次后消失\n" +
                    "种族转职：1可以改造炸弹，效果有追踪，眩晕，恐惧...\n" +
                    "          2获得两倍的近战攻速\n" +
                    "\n" +
                    "Wraith（幽灵）\n" +
                    "  有着闪避加成\n" +
                    "  更难被冰冻或点燃\n" +
                    "  会被露水瓶的bless和净化伤害（一两点的样子）\n" +
                    "专属神器：Wraithmetal Amulet，可以消耗充能隐身，相当于磕了一瓶隐身药水\n" +
                    "种族转职：1.直接获得-3的财富戒指（最轻松得财富戒指的方法，尽管是-3的）\n" +
                    "          2.会拥有被击中敌人的视野\n" +
                    "  \n" +
                    "Dwarf（矮人）\n" +
                    "  会有对近战武器有力量加成（类似于女猎）\n" +
                    "  减少一点元素伤害，但会受到更高的邪能伤害\n" +
                    "专属神器：The Chains Of Ares...这个真不记得了\n" +
                    "种族转职：1可以使法杖的持续伤害和持续时间增加（但是这个地牢里有很多对毒免疫的怪物，so...）\n" +
                    "          2空手伤害更高\n" +
                    "\n" +
                    "另外角色好像还有一些隐藏属性\n" +
                    "女猎：自带神射戒指的效果，有很大的几率捡到射出去的远程武器\n" +
                    "法师：升级会加满血并且会超出血条上限（超出部分与当前等级有关，类似于life药水）\n" +
                    "盗贼：貌似每层都可以捡到炸弹，并且用过炸弹后会有新的炸弹刷出（改造过的炸弹并不会重新刷出）\n" +
                    "战士：？？？（玩的有点少，所以不清楚）";

    @Override
    public void create() {
        super.create();

        int w = Camera.main.width;
        int h = Camera.main.height;

        RenderedText title = PixelScene.renderText( Messages.get(this, "title"), 9 );
        title.hardlight(Window.TITLE_COLOR);
        title.x = (w - title.width()) / 2 ;
        title.y = 4;
        align(title);
        add(title);

        ExitButton btnExit = new ExitButton();
        btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
        add( btnExit );

        RenderedTextMultiline text = renderMultiline(TXT_Update, 6 );


        int pw = w - 6;
        int ph = h - 20;


        NinePatch panel = Chrome.get(Chrome.Type.WINDOW);
        panel.size( pw, ph );
        panel.x = (w - pw) / 2;
        panel.y = title.y + title.height() + 2;
        add( panel );

        ScrollPane list = new ScrollPane( new Component() );
        add( list );

        Component content = list.content();
        content.clear();

        text.maxWidth((int) panel.innerWidth());

        content.add(text);

        content.setSize( panel.innerWidth(), text.height() );

        list.setRect(
                panel.x + panel.marginLeft(),
                panel.y + panel.marginTop(),
                panel.innerWidth(),
                panel.innerHeight());
        list.scrollTo(0, 0);

        Archs archs = new Archs();
        archs.setSize( Camera.main.width, Camera.main.height );
        addToBack( archs );

        fadeIn();
    }

    @Override
    protected void onBackPressed() {
        ShatteredPixelDungeon.switchNoFade(TitleScene.class);
    }
}
