package master.com.mEssentials;

public class TimeUtil {
    public static long parseTime(String input) {
        if (input == null || input.equalsIgnoreCase("perm") || input.equalsIgnoreCase("permanent")) return -1;

        try {
            char unit = input.charAt(input.length() - 1);
            long value = Long.parseLong(input.substring(0, input.length() - 1));
            return switch (unit) {
                case 's' -> System.currentTimeMillis() + (value * 1000);
                case 'm' -> System.currentTimeMillis() + (value * 60000);
                case 'h' -> System.currentTimeMillis() + (value * 3600000);
                case 'd' -> System.currentTimeMillis() + (value * 86400000);
                default -> -1;
            };
        } catch (Exception e) {
            return -1;
        }
    }
}