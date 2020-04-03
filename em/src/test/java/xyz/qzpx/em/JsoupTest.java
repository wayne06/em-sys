package xyz.qzpx.em;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupTest {

    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect("http://wufazhuce.com/").get();
            Elements elements = document.getElementsByClass("fp-one-cita");
            Element element = elements.get(0);
            String result = element.text();
            System.out.println(result);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
