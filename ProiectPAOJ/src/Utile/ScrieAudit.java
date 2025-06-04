package Utile;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScrieAudit {

    private static final String CSV_FILE_PATH = "C:\\Users\\stmil\\IdeaProjects\\ProiectPAOJ\\src\\Resurse\\log.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void scrieFisier(String mesaj, LocalDateTime timestamp) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) {
            String formattedTimestamp = formatter.format(timestamp);
            String csvLine = "\"" + mesaj.replace("\"", "\"\"") + "\"," + formattedTimestamp + "\n";
            writer.write(csvLine);
        } catch (IOException ioe) {
            System.out.println("Nu s-a putut scrie in fisier");
        }
    }
}
