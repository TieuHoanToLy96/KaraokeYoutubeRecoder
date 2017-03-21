package model;

/**
 * Created by TieuHoan on 17/03/2017.
 */

public class ChinhAm {
    private int max;
    private int process;
    private int upper;
    private int lower;

    public ChinhAm() {
    }

    public ChinhAm(int max, int upper, int process, int lower) {
        this.max = max;
        this.upper = upper;
        this.process = process;
        this.lower = lower;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getLower() {
        return lower;
    }

    public void setLower(int lower) {
        this.lower = lower;
    }
}

