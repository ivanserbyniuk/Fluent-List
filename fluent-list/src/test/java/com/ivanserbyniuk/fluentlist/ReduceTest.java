package com.ivanserbyniuk.fluentlist;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ReduceTest {
    List<Product> products = Arrays.asList(new Product("prod1", 3), new Product("prod2", 4),
            new Product("prod3", 10), new Product("prod4", 52), new Product("prod5", 2), new Product("prod6", 7));

    @Test
    public void minBy() {
        Product cheapestProduct = FluentList.from(products).minBy(Product::getPrice);
        Assert.assertEquals("prod5", cheapestProduct.getName());
    }

    @Test
    public void maxBy() {
        Product mostExpensiveProduct = FluentList.from(products).maxBy(Product::getPrice);
        Assert.assertEquals("prod4", mostExpensiveProduct.getName());
    }

    @Test
    public void sumBy() {
        int priceForAllProds = FluentList.from(products).sumBy(Product::getPrice);
        Assert.assertEquals(78, priceForAllProds);
    }

}
