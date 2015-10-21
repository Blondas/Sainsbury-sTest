import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Product {
    private Document document;
    private final String tempHtmlFilename = "tempHtmlFile.html";

    private String title;
    private Money unitPrice;
    private int size;
    private String description;

    public Product(Element element) throws IOException {
        setDocument(element);
        setSize();
        setTitle();
        setUnitPrice();
        setDescription();
    }

    public void setDocument(Element element) throws IOException {
        String url = element.select(".product .productInner .productInfoWrapper .productInfo h3 a").attr("href");
        document = Jsoup.connect(url).timeout(10 * 1000).get();
    }

    private void setTitle() {
        title = document.select("#content .productContent .mainProductInfoWrapper .mainProductInfo .productSummary .productTitleDescriptionContainer h1").text();
    }

    private void setUnitPrice() {
        Element elem = document.select("#content .productContent .mainProductInfoWrapper .mainProductInfo .productSummary .pricePerUnit").first();
        elem.select("abbr").remove();
        Double price = Double.parseDouble(elem.text().substring(1));

        CurrencyUnit gbp = CurrencyUnit.of("GBP");
        unitPrice = Money.of(gbp, price);
    }

    private String getUnitPriceAmount() {
        return unitPrice.getAmount().toString();
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    private void setSize() throws IOException {
        saveTempHtmlFile();
        size = getTempHtmlFileSize();
        removeTemHtmlFile();
    }

    private void setDescription() {
        Element elem = document.select("#information .productText").first();
        elem.select("h3").remove();
        description = elem.text();
    }

    private void saveTempHtmlFile() throws FileNotFoundException {
        PrintWriter out = new PrintWriter(tempHtmlFilename);
        out.println(document.outerHtml());
        out.close();
    }

    private void removeTemHtmlFile() {
        File f = new File(tempHtmlFilename);
        f.delete();
    }

    private int getTempHtmlFileSize() {
        File f = new File(tempHtmlFilename);
        return (int) f.length();
    }

    private String getSizeHumanReadable() {
        int unit = 1024;

        if (size < unit) {
            return Integer.toString(size) + " B";
        } else {
            int exp = (int) (Math.log(size) / Math.log(unit));
            String pre = ("KMGTPE").charAt(exp - 1) + ("i");
            return String.format("%.1f %sB", size / Math.pow(unit, exp), pre);
        }
    }

    public String toJson() {
        JSONObject obj = new JSONObject();
        obj.put("title",title);
        obj.put("size",getSizeHumanReadable());
        obj.put("unit_price",getUnitPriceAmount());
        obj.put("description", description);
        return obj.toJSONString();
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("title",title);
        obj.put("size",getSizeHumanReadable());
        obj.put("unit_price",getUnitPriceAmount());
        obj.put("description", description);
        return obj;
    }
}
