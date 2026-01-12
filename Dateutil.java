import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Dateutil {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Dateutil() { }

    public static LocalDate parse(String ddMMyyyy) {
        return LocalDate.parse(ddMMyyyy, FMT);
    }
}

