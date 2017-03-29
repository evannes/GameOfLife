package sample;

import java.util.ArrayList;
import java.util.List;

public class DynamicGrid extends Grid {
    private List<List<Boolean>> list;

    public DynamicGrid(int width, int height) {
        super(width, height);
        list = new ArrayList<List<Boolean>>(width);

        for (int x = 0; x < width; x++) {
            ArrayList<Boolean> column = new ArrayList<Boolean>(height);

            for (int y = 0; y < height; y++) {
                column.add(false);
            }

            list.add(column);
        }
    }

    @Override
    public int getWidth() {
        return list.size();
    }

    @Override
    public int getHeight() {
        return list.get(0).size();
    }

    @Override
    public void setValue(int x, int y, boolean value) {
        list.get(x).set(y, value);
    }

    @Override
    public boolean getValue(int x, int y) {
        return list.get(x).get(y);
    }

    @Override
    public IGrid getClone() {
        DynamicGrid clone = new DynamicGrid(getWidth(), getHeight());

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                clone.setValue(x,y, getValue(x, y));
            }
        }

        return clone;
    }

    public void resize(int width, int height) {
        List<List<Boolean>> tmpList = new ArrayList<List<Boolean>>(width);

        for (int x = 0; x < width; x++) {
            ArrayList<Boolean> column = new ArrayList<Boolean>(height);

            for (int y = 0; y < height; y++) {
                column.add(false);
            }

            tmpList.add(column);
        }

        for (int x = 0; x < tmpList.size() && x < list.size(); x++) {
            for (int y = 0; y < tmpList.get(0).size() && y < list.get(0).size(); y++) {
                tmpList.get(x).set(y, list.get(x).get(y));
            }
        }

        list = tmpList;
    }
}
