package org.cfcdoom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paper implements Serializable {
    double cost;
    String name;
    List<CardSize> compatibleSizes;

    public  Paper(String name, double cost, List<CardSize> sizes){
        this.compatibleSizes = new ArrayList<>();
        compatibleSizes.addAll(sizes);
        this.cost = cost;
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public List<CardSize> getCompatibleSizes() {
        return compatibleSizes;
    }

    @Override
    public String toString() {
        String firstPart = name + " cost: " + cost;
        StringBuilder SecondPart = new StringBuilder();
        SecondPart.append("\t Compatible sizes: ");
        for (CardSize x : compatibleSizes){
            SecondPart.append(x +" ");
        }
        return firstPart+SecondPart;
    }
}
