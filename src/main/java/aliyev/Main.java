package aliyev;

public class Main {
    public static void main(String[] args) {
        Delivery delivery = new Delivery(40, "большой", false, "высокая");
        DeliveryCostCalculator calculator = new DeliveryCostCalculator();
        System.out.println(calculator.calculateCost(delivery));
    }
}