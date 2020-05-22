package org.cfcdoom;

public enum Color {
    BWONESIDE(0.01, "B/W One Side"),
    BWTWOSIDE(0.02, "B/W Two Side"),
    COLORONESIDE(0.03, "Color One Side"),
    COLORTWOSIDE(0.06, "Color Two Side"),
    BWANDCOLOR(0.04, "B/W and Color");

    double mult;
    String name;
    private Color(double a, String name) {
        this.mult = a;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}