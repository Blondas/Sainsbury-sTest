import org.jsoup.nodes.Element;

import java.io.IOException;

public class testRun {
    public static void main(String[] args) {

        PageScrapper scrapper = null;
        try {
            scrapper = new PageScrapper();
            ProductContainer container = new ProductContainer(scrapper.getProducts());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
