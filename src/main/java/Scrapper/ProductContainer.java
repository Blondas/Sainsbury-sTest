package scrapper;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;

public class ProductContainer {
    private LinkedList<Product> products  = new LinkedList<Product>();
    private Money total;

    public ProductContainer(Elements elements) {
        CurrencyUnit gbp = CurrencyUnit.of("GBP");
        total = Money.of(gbp, 0);

        for (Element element : elements) {
            try {
                Product product = new Product(element);
                addProduct(product);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(this.toJson());
    }

    private void addProduct(Product product) {
        products.add(product);
        total = total.plus(product.getUnitPrice());
    }

    private String getTotalPriceAmount() {
        return total.getAmount().toString();
    }

    public String toJson() {
        JSONObject obj = new JSONObject();

        JSONArray results = new JSONArray();
        for (Product product: products) {
            results.add(product.toJSONObject());
        }
        obj.put("results", results);

        obj.put("total", getTotalPriceAmount());

        return obj.toJSONString();
    }
}
