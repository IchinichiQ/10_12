package ru.vsu.cs.p_p_v;

import java.util.ArrayList;
import java.util.List;

public class RectangleFiller {
    public static class Rectangle {
        public Point pointTopLeft;
        public Point pointBottomRight;

        public Rectangle(Point pointTopLeft, Point pointBottomRight){
            this.pointTopLeft = pointTopLeft;
            this.pointBottomRight = pointBottomRight;
        }
    }

    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public List<Rectangle> rectangles = new ArrayList<>();
    public int sizeX;
    public int sizeY;

    public RectangleFiller(int sizeX, int sizeY, List<Rectangle> rectangles) {
        this.rectangles = rectangles;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public List<Rectangle> fillByRectangles() {
        List<Rectangle> newRectangles = new ArrayList<>();

        for (int x = 0; x < sizeX; x++) {
            for (int y = 1; y <= sizeY; y++) {
                boolean isEmpty = true;

                Rectangle cell = new Rectangle(new Point(x, y), new Point(x + 1, y - 1));

                for (Rectangle rect : rectangles) {
                    if (isCellInsideRectangle(rect, cell)) {
                        isEmpty = false;
                        break;
                    }
                }

                if (isEmpty)
                    newRectangles.add(cell);
            }
        }

        return newRectangles;
    }

    private boolean isCellInsideRectangle(Rectangle rect, Rectangle cell) {
        return cell.pointTopLeft.x >= rect.pointTopLeft.x && cell.pointTopLeft.x < rect.pointBottomRight.x && cell.pointTopLeft.y <= rect.pointTopLeft.y && cell.pointBottomRight.y >= rect.pointBottomRight.y;
    }
}
