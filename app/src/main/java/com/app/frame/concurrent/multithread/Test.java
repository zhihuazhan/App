package com.app.frame.concurrent.multithread;

import java.util.ArrayList;
import java.util.List;

/**
 * 示例
 */

public class Test {

    public static void test() {
        List<Action> a = new ArrayList<>();
        Action aaa = new Action() {
            @Override
            public void act() {
                //LeoLog.i("Concurrent", "aaa");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        aaa.setPriority(6);
        a.add(aaa);

        Action aba = new Action() {
            @Override
            public void act() {
                //LeoLog.i("Concurrent", "aba");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        aba.setPriority(2);
        a.add(aba);

        Action abb = new Action() {
            @Override
            public void act() {
                //LeoLog.i("Concurrent", "abb");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        abb.setPriority(2);
        a.add(abb);

        Action abc = new Action() {
            @Override
            public void act() {
                //LeoLog.i("Concurrent", "abc");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        abc.setPriority(2);
        a.add(abc);

        Action abd = new Action() {
            @Override
            public void act() {
                //LeoLog.i("Concurrent", "abd");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        abd.setPriority(2);
        a.add(abd);

        Action aca = new Action() {
            @Override
            public void act() {
                //LeoLog.i("Concurrent", "aca");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        aca.setPriority(3);
        a.add(aca);

        Action ada = new Action() {
            @Override
            public void act() {
                //LeoLog.i("Concurrent", "ada");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ada.setPriority(4);
        a.add(ada);

        Action adb = new Action() {
            @Override
            public void act() {
                //LeoLog.i("Concurrent", "adb");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        adb.setPriority(4);
        a.add(adb);

        Action aea = new Action() {
            @Override
            public void act() {
                //LeoLog.i("Concurrent", "aea");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        aea.setPriority(5);
        a.add(aea);

        Concurrent ca = new Concurrent(a);
        ca.start();
        //打印结果:
        //        05-04 16:51:34.008 13030-13228/com.leo.post I/Concurrent: aba
        //        05-04 16:51:34.208 13030-13228/com.leo.post I/Concurrent: abb
        //        05-04 16:51:34.408 13030-13228/com.leo.post I/Concurrent: abc
        //        05-04 16:51:34.609 13030-13228/com.leo.post I/Concurrent: abd
        //        05-04 16:51:34.809 13030-13228/com.leo.post I/Concurrent: aca
        //        05-04 16:51:35.009 13030-13228/com.leo.post I/Concurrent: ada
        //        05-04 16:51:35.210 13030-13228/com.leo.post I/Concurrent: adb
        //        05-04 16:51:35.410 13030-13228/com.leo.post I/Concurrent: aea
        //        05-04 16:51:35.610 13030-13228/com.leo.post I/Concurrent: aaa
    }
}
