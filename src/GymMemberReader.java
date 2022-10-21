import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GymMemberReader {
    private String member;
    public static Scanner sc = new Scanner(System.in);

    public GymMemberReader() {
        Path p = Paths.get("/Users/marcushurtigh/Programmering/Projekt/Java/Uppgift2OOP/src/customers.txt");
        String s;
        printfindperson();
        String person = extractMember();
        try (Scanner scan = new Scanner(p)) {
            while (scan.hasNextLine()) {
                s = scan.nextLine();
                if (s.contains(person)) {
                    if (TimeCheck(scan.nextLine(), s)) {
                        return;
                    }
                }
            }
            System.out.println(person + " Är inte medlem");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMember() {
        if (this.member != null) {
            return this.member;
        }
        return null;
    }
    public String trainingMember() {
        return getMember() + " " + timeToday();
    }

    public boolean TimeCheck(String todayDate, String dateYearAgo) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy-MM-dd");
        LocalDate ldn = LocalDate.now();
        LocalDate ya = LocalDate.parse(todayDate, dtf);
        Period p = Period.between(ya, ldn);

        if (p.toTotalMonths() <= 12) {
            System.out.println(" Personen har betalat för sitt medlemsskap och tränar idag " + todayDate);
            this.member = dateYearAgo;
            return true;
        } else {
            System.out.println( "Personen har inte betalat för sitt medlemskap på " + p.toTotalMonths() + " månader");
            return true;
        }
    }

    public String timeToday() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(" yyyy-MM-dd ");
        return dtf.format(LocalDateTime.now());
    }

    public String extractMember() {
        return sc.nextLine();
    }

    public void printfindperson() {
        System.out.println("Vilken medlem vill ni söka efter?");
    }

    public void pTWriter() {
        if (getMember() != null) {
            Path file = Paths.get(getMember());
            String textLogFilePath = String.valueOf(file);
            if (!Files.exists(file)) {
                try {
                    Files.createFile(file);
                } catch (IOException e) {
                    System.out.println(" Det var inte möjligt att skapa filen");
                    e.printStackTrace();
                }
            }
            if (Files.exists(file)) {
                try {
                    if (trainingMember() != null) {
                        FileWriter writer = new FileWriter(textLogFilePath, true);
                        writer.write(trainingMember() + "\n");
                        writer.close();
                    }
                } catch (IOException e) {
                    System.out.println("Något gick fel");
                    e.printStackTrace();
                }
            }
        }
    }
}
