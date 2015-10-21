package scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PageScrapper {
    private String httpAddress;
    private Elements products;

    public static void main(String[] args) throws IOException {

        PageScrapper scrapper = null;
        try {
            scrapper = new PageScrapper();
            ProductContainer container = new ProductContainer(scrapper.getProducts());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Press ENTER key to end program.");
        System.in.read();
    }

    public PageScrapper(String httpAddress) throws IOException{
        this.httpAddress = httpAddress;
        getProductNodes();
    }

    public PageScrapper() throws IOException{
        httpAddress = "http://www.sainsburys.co.uk/webapp/wcs/stores/servlet/CategoryDisplay?msg=&langId=44&categoryId=185749&storeId=10151&krypto=Xg4WPVZZzYoZrxvQ%2FXytr4IwHqNAMvsgon56SnPex9lzis7CeY2nLxF8y4mLSG4ZNNqgxdIXCAaO%0AwpzD40N1GemNGsoKM%2BTBg7ug53G%2FUOMYfkpsnWNru%2FL6pSlmAvLlq9f%2B%2BSQUvqlah3BxkwCOlLGk%0Av9EMs%2FKbVzDUwUBYQdH3483dndIrJLQmdmYUeLEIVP5WQCb9oaK%2BTN7kY8Givh%2FOZd2HQRfXnQDl%0Aby1W9DYW%2FzJRZPYrKrAxpezbu1CUUhIyUiFMp2FzMNZJ8W9vUevxB0qWWNP1Nv1P8D37Rww%3D#langId=44&storeId=10151&catalogId=10137&categoryId=185749&parent_category_rn=12518&top_category=12518&pageSize=20&orderBy=FAVOURITES_FIRST&searchTerm=&beginIndex=0&hideFilters=true";
        getProductNodes();
    }

    public String getHttpAddress() {
        return httpAddress;
    }

    private void getProductNodes() throws IOException {
        Document doc;

        doc = Jsoup.connect(httpAddress).timeout(10 * 1000).get();
        Element element = doc.select("#productsContainer #productLister .productLister").first();
        products = element.children();
    }

    public Elements getProducts() {
        return products;
    }
}
