package be.bonamis.advent;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;


public class DayDataRetriever {

    public static void main(String[] args) throws Exception {
        //Scanner scanner = new Scanner(System.in);
        //.out.println("Enter year and month:");

        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int day = now.getDayOfMonth();
        NumberFormat formatter = new DecimalFormat("00");
        retrieveCodes(year, day, formatter);
        retrievePuzzleInput(year, day, formatter);
    }

    private static void retrievePuzzleInput(int year, int day, NumberFormat formatter) throws IOException {
        Path directories = Files.createDirectories(Paths.get("src/main/resources/" + year + "/" + formatter.format(day)));
        String puzzleInputUrl = dayUrl(year, day) + "/input";
        InputStream inputStream = downloadInput(puzzleInputUrl);
        Path path = Paths.get(directories.toString() + "/" + year + "_" + formatter.format(day) + "_input.txt");
        Files.copy(inputStream, path);
    }

    private static void retrieveCodes(int year, int day, NumberFormat formatter) throws IOException {
        Document document = getDocument(dayUrl(year, day));
        List<Element> elements = document.select("code");
        Path directories = Files.createDirectories(Paths.get("src/test/resources/" + year + "/" + formatter.format(day)));
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            Path path = Paths.get(directories.toString() + "/" + year + "_" + formatter.format(day) + "_" + formatter.format(i) + "_code.txt");
            byte[] strToBytes = element.html().getBytes();
            Files.write(path, strToBytes);
        }
    }

    private static InputStream downloadInput(String puzzleInputUrl) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(puzzleInputUrl).openConnection();
        con.addRequestProperty("Cookie", "session=53616c7465645f5fe62cabceae77be19ad4cd2cac6825cdb899e92b716babfa0e130422d2c46bd303268412195a476f21c05b5ec22e0feb38d7498b73b280eaf");
        return con.getInputStream();
    }

    static Document getDocument(String url) throws IOException {
        return Jsoup.connect(url)
                .timeout(10 * 1000)
                .method(Connection.Method.GET)
                .followRedirects(true)
                .execute()
                .parse();
    }

    private static String dayUrl(int year, int day) {
        return "https://adventofcode.com/" + year + "/day/" + day;
    }
}
