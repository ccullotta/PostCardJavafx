package org.cfcdoom;

import javafx.beans.property.SimpleBooleanProperty;

import java.util.HashMap;
import java.util.Map;

public enum CardSize {
    CARD3HALFBY2("3.5x2", 25, 20),
    CARD3BY5("3x5", 12, 10),
    CARD3HALFBY5HALF("3.5x5.5", 10, 10),
    CARD3d66BY8d5("3.66x8.5", 6, 6),
    CARD4BY6("4x6", 8, 8),
    CARD4BY9("4x9", 6, 6),
    CARD4d25BY6("4.25x6", 8, 8),
    CARD4d25BY11("4.25x11", 4, 4),
    CARD5BY7("5x7", 4, 4),
    CARD5BY8("5x8", 4, 4),
    CARD5d5BY8d5("5.5x8.5", 4, 4),
    CARD5d5BY11("5.5x11", 3, 3),
    CARD6BY9("6x9", 4, 4),
    CARD6BY11("6x11", 3, 3),
    CARD6d25BY9("6.25x9", 4, 4),
    CARD6d5BY9("6.5x9", 4, 2),
    CARD6d5BY11("6.5x11", 2, 2),
    CARD8d5BY11("8.5x11", 2, 2),
    CARD9BY12("9x12", 2, 2),
    CARD11BY17("11x17", 1, 1),
    CARD12BY18("12x18", 1, 1);


    int noBleed;
    int withBleed;
    String name;
    SimpleBooleanProperty possible;

    public static Map<String, CardSize> lookupTable = new HashMap<>();

    static {
        for (CardSize d : CardSize.values()) {
            lookupTable.put(d.name, d);
        }
    }

    private CardSize(String name, int noBleed, int withBleed){
        this.noBleed = noBleed;
        this.withBleed = withBleed;
        this.name = name;
        possible = new SimpleBooleanProperty(false);
    }

    @Override
    public String toString() {
        return name;
    }

    public void setPossible(boolean possible) {
        this.possible.set(possible);
    }

    public boolean isPossible() {
        return possible.get();
    }

    public static CardSize getByName(String name){
        return lookupTable.get(name);
    }
}
