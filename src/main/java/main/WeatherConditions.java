package main;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class WeatherConditions {
    public static String type;
    public static double rainfall;
    public static double windSpeed;
    public static String Season;
    public static boolean desertStorm;
    public static int numberOfHikers;
}