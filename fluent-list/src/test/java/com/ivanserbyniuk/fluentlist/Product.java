package com.ivanserbyniuk.fluentlist;

/**
 * Created by ivan on 2/21/18.
 */

public class Product {
    private String name;
    private int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }

        Product product = (Product) o;

        if (getPrice() != product.getPrice()) {
            return false;
        }
        return getName() != null ? getName().equals(product.getName()) : product.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + getPrice();
        return result;
    }
}
