package softuni.exam.constants;

public enum Messages {
    ;

    public static final String INVALID_COUNTRY= "Invalid country%n";

    public static final String VALID_COUNTRY_FORMAT="Successfully imported country %s - %s%n";

    public static final String INVALID_CITY = "Invalid city%n";

    public static final String VALID_CITY_FORMAT = "Successfully imported city %s - %d%n";

    public static final String INVALID_FORECAST = "Invalid forecast";

    public static final String VALID_FORECAST_FORMAT = "Successfully imported forecast %s - %.2f%n";

    public static final String PRINT_FORMAT =
            "City: %s:%n" +
                    " -min temperature: %.2f%n" +
                    " --max temperature: %.2f%n" +
                    " ---sunrise: %s:%n" +
                    " ----sunset: %s:%n";
}
