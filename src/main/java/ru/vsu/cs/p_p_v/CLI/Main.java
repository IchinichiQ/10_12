package ru.vsu.cs.p_p_v.CLI;

import ru.vsu.cs.p_p_v.*;
import ru.vsu.cs.p_p_v.RectangleFiller.Rectangle;

import java.io.File;
import java.util.List;

public class Main {

/*    Для набора прямоугольников, стороны которых параллельны OX и OY, заданных
    координатами 2-х диагональных вершин, найти все прямоугольники, которые не
    перекрываются никакими другими прямоугольниками (т.е. если вырезать
    прямоугольники нужного размера и раскладывать по координатам на листе бумаги, то
    нужные прямоугольники не буду накладываться на другие прямоугольники, но могут
    касаться сторонами).
*/

    static class InputArgs {
        public String inputFilePath = null;
        public String outputFilePath = null;
    }

    public static void main(String[] args) {
        try {
            InputArgs processedArgs = parseCmdArgs(args);

            RectangleFiller filler = RectangleFile.rectangleFillerFromFile(processedArgs.inputFilePath);

            List<Rectangle> newRectangles = filler.fillByRectangles();

            RectangleFile.RectanglesToFile(processedArgs.outputFilePath, filler.sizeX, filler.sizeY, newRectangles);

            System.out.printf("The result is saved in the file %s", processedArgs.outputFilePath);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static InputArgs parseCmdArgs(String[] args) throws Exception {
        if (args.length != 4)
            throw new Exception("Invalid number of arguments");

        InputArgs myInputArgs = new InputArgs();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-i", "--input-file" -> myInputArgs.inputFilePath = args[++i];
                case "-o", "--output-file" -> myInputArgs.outputFilePath = args[++i];
            }
        }

        if (myInputArgs.outputFilePath == null)
            throw new Exception("Invalid output file");

        if (myInputArgs.inputFilePath == null)
            throw new Exception("Invalid input file");

        File inputFile = new File(myInputArgs.inputFilePath);
        if (!inputFile.isFile() || !inputFile.exists())
            throw new Exception("Invalid input file");

        return myInputArgs;
    }
}
