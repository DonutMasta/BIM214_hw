package estu;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Calculations {
    DecimalFormat df = new DecimalFormat("#.###");

    // public void Closescanner() {
    // sc.close();
    // }

    public double HoldingCost(double unitCost, double interestRate) {
        return unitCost * interestRate;
    }

    public double AnnualDemand(double leadTime, double leadTimeDemand) {
        return leadTimeDemand * (12 / leadTime);
    }

    public double UnitOrderedPerReplenishment(double setupCostPerOrder, double annualDemand, double holdingCost) {
        return Math.sqrt((2 * setupCostPerOrder * annualDemand) / holdingCost);
    }

    public double Frformula(double Q, double holdingCost, double annualDemand, double unitCost) {
        return (Q * holdingCost) / (unitCost * annualDemand);
    }

    public double Rformula(double leadTimeDemand, double leadTimeStandartDeviation, double Fr, Scanner sc) {

        System.out.println("Please enter the z value for " + df.format(Fr));
        double z = sc.nextDouble();
        return leadTimeDemand + (z * leadTimeStandartDeviation);

    }

    public double Nformula(double leadTimeStandartDeviation, Scanner sc)// N formula
    {

        System.out.println("Please enter the corresponding L(z) value: ");
        double lZ = sc.nextDouble();

        return leadTimeStandartDeviation * lZ;
    }

    public double UnitOrderedPerReplenishmentNew(double setupCostPerOrder, double annualDemand, double holdingCost,
            double unitCost, double nR) {
        return Math.sqrt((2 * annualDemand * (setupCostPerOrder + (unitCost * nR))) / holdingCost);
    }

    
}
