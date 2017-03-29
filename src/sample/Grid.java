package sample;

import java.util.stream.IntStream;

public class Grid implements IGrid {
    private boolean[][] array;

    public Grid(int width, int height) {
        array = new boolean[width][height];
    }

    public int getWidth() {
        return array.length;
    }

    public int getHeight() {
        return array[0].length;
    }

    public void setValue(int x, int y, boolean value) {
        array[x][y] = value;
    }

    public boolean getValue(int x, int y) {
        return array[x][y];
    }

    public void toggleValue(int x, int y) {
        setValue(x, y, !getValue(x, y));
    }

    public void clear() {
        IntStream.range(0, getWidth()).forEach(x -> IntStream.range(0, getHeight()).forEach(y -> setValue(x, y, false)));
    }

    public IGrid getClone() {
        Grid clone = new Grid(getWidth(), getHeight());

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                clone.setValue(x,y, getValue(x, y));
            }
        }

        return clone;
    }

    @Override
    public String toString(){
        String result = "";

        for(int x = 0; x < getWidth(); x++) {
            for(int y = 0; y < getHeight(); y++) {
                result += getValue(x, y) ? "1" : "0";
            }
        }

        return result;
    }
}
