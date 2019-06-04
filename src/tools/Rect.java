package tools;

import javafx.scene.image.Image;

public class Rect {
    private int x;
    private int y;

    private int w;
    private int h;

    public Rect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Rect(double x, double y, double w, double h) {
        this.x = (int)x;
        this.y = (int)y;
        this.w = (int)w;
        this.h = (int)h;
    }

    public Rect(Image img, int x, int y) {
        this.x = x;
        this.y = y;
        this.w = (int)img.getWidth();
        this.h = (int)img.getHeight();
    }

    public Rect (Image img, double x, double y) {
        this.x = (int)x;
        this.y = (int)y;
        this.w = (int)img.getWidth();
        this.h = (int)img.getHeight();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getTop() {
        return this.y;
    }

    public int getLeft() {
        return this.x;
    }

    public int getBottom() {
        return this.y + this.h;
    }

    public int getRight() {
        return this.x + this.h;
    }
}
