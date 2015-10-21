package scrapper;

import scrapper.PageScrapper;
import scrapper.ProductContainer;

import java.io.IOException;

public class TestRun {
    public static void main(String[] args) throws IOException {

        PageScrapper scrapper = null;
        try {
            scrapper = new PageScrapper();
            ProductContainer container = new ProductContainer(scrapper.getProducts());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Press any key to end program.");
        System.in.read();
    }
}
