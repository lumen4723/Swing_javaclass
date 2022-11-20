package Swing_javaclass;

import java.awt.*;

public class circle {
    int x;
    int y;
    int r;
    int speed;
    int type;
    Color color;

    public circle(int x, int speed, int type) {
        this.x = x;         // x시작좌표
        this.speed = speed; // 속도
        this.type = type;   // 타입 0 = 태고딱소, 1. 태고쿵소, 2. 태고딱대, 3. 태고쿵대
        r = type < 2 ? 80 : 140; // 지름
        y = type < 2 ? 240 : 210; // y좌표
        color = type % 2 == 1 ? Color.red : Color.blue; // 색깔
    }
}