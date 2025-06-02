package audit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class AuditService {
    private static final String FILE = "audit.csv";
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final AuditService INSTANCE = new AuditService();

    private AuditService() {
        File f = new File(FILE);
        if (!f.exists()) {
            try (PrintWriter out = new PrintWriter(new FileWriter(f, false))) {
                out.println("nume_actiune,timestamp");
            } catch (IOException ignored) {}
        }
    }

    public synchronized void log(String action) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE, true))) {
            out.printf("%s,%s%n", action, LocalDateTime.now().format(FMT));
        } catch (IOException e) {
            System.err.println("Eroare audit: " + e.getMessage());
        }
    }
}
