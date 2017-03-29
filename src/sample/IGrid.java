package sample;

public interface IGrid {
    int getWidth();
    int getHeight();
    void setValue(int x, int y, boolean value);
    boolean getValue(int x, int y);
    void toggleValue(int x, int y);
    void clear();
    IGrid getClone();
}
