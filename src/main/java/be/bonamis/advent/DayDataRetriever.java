package be.bonamis.advent;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class DayDataRetriever {

    public static void main(String[] args) throws Exception {
        String year = "2022";
        int day = 1;
        NumberFormat formatter = new DecimalFormat("00");
        Document document = new DayDataRetriever().getDocument(year, day);
        List<Element> elements = document.select("code");
        Path directories = Files.createDirectories(Paths.get("src/test/resources/"
                + year + "/" + formatter.format(day)));

        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);

            Path path = Paths.get(directories.toString() + "/" + year + "_"
                    + formatter.format(day) + "_" + formatter.format(i) + "_code.txt");
            byte[] strToBytes = element.html().getBytes();
            Files.write(path, strToBytes);
        }
    }

    Document getDocument(String year, int day) throws IOException {
        String url = "https://adventofcode.com/" + year + "/day/" + day;
        return Jsoup.connect(url)
                .timeout(10 * 1000)
                .method(Connection.Method.GET)
                .followRedirects(true)
                .execute()
                .parse();
    }
}
