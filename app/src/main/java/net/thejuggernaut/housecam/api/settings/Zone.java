package net.thejuggernaut.housecam.api.settings;

import java.io.Serializable;

public class Zone implements Serializable {
    int x1,x2,y1,y2;
    int threshold;
    int boxJump;
    int smallIgnore;
    int area;

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getBoxJump() {
        return boxJump;
    }

    public void setBoxJump(int boxJump) {
        this.boxJump = boxJump;
    }

    public int getSmallIgnore() {
        return smallIgnore;
    }

    public void setSmallIgnore(int smallIgnore) {
        this.smallIgnore = smallIgnore;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }
}
