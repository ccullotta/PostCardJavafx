package org.cfcdoom;

public class Calculations {
    private double printCost;
    private double totalCost;
    private int totalSheets;
    private double paperMult;
    private static final double costPerSheet = 0.14;
    double paperCost;
    double clickCharge;
    double clickMult;
    private static final int setup = 25;
    private int quantity;
    static double costRange[] = {0,100,250,500,1000,1500,2500,4000,7000};
    static double paperTable[] = {1.45,1.42,1.39,1.36,1.33,1.31,1.29,1.27,1.25};
    private double clickTotal;
    private double[] clickTable = {8,6,5,4,3.5,3.2,2.5,2,1.75};

    public Calculations(int quantity, int upOnSheet, double mailCost, org.cfcdoom.Color type){
        clickCharge = type.mult;
        totalSheets = quantity /upOnSheet;
        this.quantity = quantity;
        calcPrintCost();
        totalCost = mailCost + printCost;
    }

    public Calculations(int quantity, int upOnSheet, org.cfcdoom.Color type){
        this(quantity, upOnSheet, 0, type);
    }

    private void calcPrintCost() {
        calcPaperCost();
        calcClickTotal();
        printCost = paperCost + clickTotal + setup;
    }

    private void calcClickTotal() {
        for (int i = costRange.length-1; i >= 0; i--){
            if (quantity >costRange[i]){
                clickMult = clickTable[i];
                break;
            }
        }
        clickTotal = clickMult * clickCharge * totalSheets;
    }

    private void calcPaperCost() {
        for (int i = costRange.length-1; i >= 0; i--){
            if (quantity >costRange[i]){
                paperMult = paperTable[i];
                break;
            }
        }
        paperCost = paperMult * costPerSheet * totalSheets;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getPrintCost() {
        return printCost;
    }
}

