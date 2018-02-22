package com.ivanserbyniuk.fluentlist;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ReduceTest {
    List<Product> products = Arrays.asList(new Product("prod1", 3), new Product("prod2", 4),
            new Product("prod3", 10), new Product("prod4", 52), new Product("prod5", 2), new Product("prod6", 7));

    @Test
    public void any() {
        boolean withPriceMoreThen50 = FluentList.from(products).any(it -> it.getPrice() > 50);
        assertTrue(withPriceMoreThen50);

        boolean withPriceMoreThen150 = FluentList.from(products).any(it -> it.getPrice() > 150);
        assertFalse(withPriceMoreThen150);
    }

    @Test
    public void all() {
        boolean allItemsCheaperThen100 = FluentList.from(products).all(it -> it.getPrice() < 100);
        assertTrue(allItemsCheaperThen100);

        boolean allItemsCheaperThen10 = FluentList.from(products).all(it -> it.getPrice() < 10);
        assertFalse(allItemsCheaperThen10);
    }

    @Test
    public void non() {
        boolean noPriceMoreThen100 = FluentList.from(products).non(it -> it.getPrice() > 100);
        assertTrue(noPriceMoreThen100);

        boolean noPirceLessThen100 = FluentList.from(products).non(it -> it.getPrice() < 100);
        assertFalse(noPirceLessThen100);
    }

    @Test
    public void minBy() {
        Product cheapestProduct = FluentList.from(products).minBy(Product::getPrice);
        assertEquals("prod5", cheapestProduct.getName());
    }

    @Test
    public void maxBy() {
        Product mostExpensiveProduct = FluentList.from(products).maxBy(Product::getPrice);
        assertEquals("prod4", mostExpensiveProduct.getName());
    }

    @Test
    public void sumBy() {
        int priceForAllProds = FluentList.from(products).sumBy(Product::getPrice);
        assertEquals(78, priceForAllProds);
    }

    @Test
    public void plusOneItem() {
        Product product = new Product("tv", 12);
        int countAfterPlus = FluentList.from(products).plus(product).count(it -> it.equals(product));
        assertEquals(1, countAfterPlus);

    }

    @Test
    public void plusList() {
        Product product = new Product("tv", 12);
        int countAfterPlusList = FluentList.from(products).plus(Arrays.asList(product, product, product, product))
                .count(it -> it.equals(product));
        assertEquals(4, countAfterPlusList);
    }

}
