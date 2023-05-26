package estu;

import java.text.DecimalFormat;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("###.##");

        Calculations c = new Calculations();
        Scanner sc = new Scanner(System.in);
        System.out.println(
                "Please enter the costs (unit cost, ordering cost, penalty cost) and interest rate with space between each number: ");
        double unitCost = sc.nextDouble();
        double orderingCost = sc.nextDouble();
        double penaltyCost = sc.nextDouble();
        double interestRate = sc.nextDouble();

        System.out.println("Please enter the lead time, lead time demand and lead time standard deviation: ");
        double leadTime = sc.nextDouble();
        double leadTimeDemand = sc.nextDouble();
        double leadTimeStandartDeviation = sc.nextDouble();

        double holdingCost = c.HoldingCost(unitCost, interestRate);
        double annualDemand = c.AnnualDemand(leadTime, leadTimeDemand);
        double q0 = c.UnitOrderedPerReplenishment(orderingCost, annualDemand, holdingCost);
        double fR = c.Frformula(q0, holdingCost, annualDemand, unitCost);
        double r0 = c.Rformula(leadTimeDemand, leadTimeStandartDeviation, fR, sc);
        double nR = c.Nformula(leadTimeStandartDeviation, sc);
        double rFirst;
        double rSecond = r0;
        int iteration = 0;

        do {
            double qN = c.UnitOrderedPerReplenishmentNew(orderingCost, annualDemand, holdingCost, unitCost, nR);
            double fRn = c.Frformula(qN, holdingCost, annualDemand, unitCost);
            double rN = c.Rformula(leadTimeDemand, leadTimeStandartDeviation, fRn, sc);
            nR = c.Nformula(leadTimeStandartDeviation, sc);
            rFirst = rSecond;
            rSecond = rN;
            q0 = qN;
            iteration++;

        } while (Math.abs(rSecond - rFirst) >= 1);

        System.out.println("Q = " + Math.round(q0));
        System.out.println("R = " + Math.round(rSecond));
        System.out.println(
                "Number of iterations the software run to obtain the optimal lot size and reorder point: " + iteration);

        double safetyStock = Math.round(Math.round(rSecond) - leadTimeDemand);
        System.out.println("The safety stock: " + safetyStock);

        double averageAnnualHoldingCost = holdingCost * (Math.round(q0) / 2 + Math.round(rSecond) - leadTimeDemand);

        double averageAnnualOrderingCost = (orderingCost * annualDemand) / Math.round(q0);

        double averageAnnualPenaltyCost = (unitCost * annualDemand * nR / Math.round(q0));

        System.out.println(
                "Average annual holding , setup and penalty costs are: " + df.format(averageAnnualHoldingCost) + ", "
                        + df.format(averageAnnualOrderingCost) + ", " + df.format(averageAnnualPenaltyCost));

        double averageTimeBetweenOrders = (Math.round(q0) / annualDemand) * 12;

        System.out.println(
                "Average time between the placement of orders: " + df.format(averageTimeBetweenOrders) + " months");

        sc.close();

    }

}
