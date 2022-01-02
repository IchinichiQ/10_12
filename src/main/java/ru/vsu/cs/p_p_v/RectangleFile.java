package ru.vsu.cs.p_p_v;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ru.vsu.cs.p_p_v.RectangleFiller.*;

public class RectangleFile {
    public static RectangleFiller rectangleFillerFromFile(String path) throws IOException {
        List<Rectangle> rectangles = new ArrayList<>();

        String fileContent = Files.readString(Path.of(path));

        return rectangleFillerFromString(fileContent);
    }

    public static RectangleFiller rectangleFillerFromString(String input) {
        List<Rectangle> rectangles = new ArrayList<>();

        Scanner fileScan = new Scanner(input);

        Scanner fieldSizeScan = new Scanner(fileScan.nextLine());
        int sizeX = fieldSizeScan.nextInt();
        int sizeY = fieldSizeScan.nextInt();

        while (fileScan.hasNextLine()) {
            Scanner rectangleScan = new Scanner(fileScan.nextLine());
            Point pointTopLeft = new Point (rectangleScan.nextInt(), rectangleScan.nextInt());
            Point pointBottomRight = new Point (rectangleScan.nextInt(), rectangleScan.nextInt());
            rectangles.add(new Rectangle(pointTopLeft, pointBottomRight));
        }

        return new RectangleFiller(sizeX, sizeY, rectangles);
    }

    public static void RectanglesToFile(String path, int sizeX, int sizeY, List<Rectangle> rectangles) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(sizeX + " " + sizeY + "\n");
        for (Rectangle rect : rectangles) {
            builder.append(rect.pointTopLeft.x + " " + rect.pointTopLeft.y + " ");
            builder.append(rect.pointBottomRight.x + " " + rect.pointBottomRight.y + "\n");
        }

        FileWriter writer = new FileWriter(path);
        writer.write(builder.toString());
        writer.flush();
    }
}
