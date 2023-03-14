package test.switchexpression;

public class SwitchExpressionDemo {
    public static void main(String[] args) {
        String dayOfWeek = "Saturday";
        int dayNum = switch (dayOfWeek) {
            case "Monday" -> 1;
            case "Tuesday" -> 2;
            case "Wednesday" -> 3;
            case "Thursday" -> 4;
            case "Friday" -> 5;
            case "Saturday", "Sunday" -> {
                System.out.println("It's a weekend!");
                yield 6;
            }
            default -> throw new IllegalArgumentException("Invalid day of week: " + dayOfWeek);
        };

        System.out.println("Day number of " + dayOfWeek + " is: " + dayNum);
    }
}
