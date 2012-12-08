package com.jayway.domain;

public abstract class Beer {

    private final String name;
    private final Type type;
    private final int price;

    protected Beer(String name, Type type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beer beer = (Beer) o;

        if (price != beer.price) return false;
        if (!name.equals(beer.name)) return false;
        if (type != beer.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + price;
        return result;
    }
}
