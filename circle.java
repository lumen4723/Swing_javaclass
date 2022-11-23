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
        color = type % 2 == 1 ? new Color(249, 72, 41) : new Color(104, 192, 193); // 색깔
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public int getSpeed() {
        return speed;
    }

    public int getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}