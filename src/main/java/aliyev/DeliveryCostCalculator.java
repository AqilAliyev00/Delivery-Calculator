package aliyev;

public class DeliveryCostCalculator {
    public int calculateCost(Delivery delivery) {
        if (delivery.fragile() && delivery.distance() > 30) {
            throw new IllegalArgumentException("Хрупкие грузы нельзя доставлять на расстояние более 30 км.");
        }

        int cost;
        if (delivery.distance() > 30) {
            cost = 300;
        } else if (delivery.distance() > 10) {
            cost = 200;
        } else if (delivery.distance() > 2) {
            cost = 100;
        } else {
            cost = 50;
        }

        if ("большой".equals(delivery.size())) {
            cost += 200;
        } else if ("маленький".equals(delivery.size())) {
            cost += 100;
        } else {
            throw new IllegalArgumentException("Некорректный размер груза. Укажите 'большой' или 'маленький'.");
        }

        if (delivery.fragile()) {
            cost += 300;
        }

        double multiplier = switch (delivery.load()) {
            case "очень высокая" -> 1.6;
            case "высокая" -> 1.4;
            case "повышенная" -> 1.2;
            default -> 1.0;
        };

        cost = (int) Math.ceil(cost * multiplier);
        return Math.max(cost, 400);
    }
}

