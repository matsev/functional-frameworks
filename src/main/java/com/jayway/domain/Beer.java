package com.jayway.domain;

public abstract class Beer {

    private final String name;
    private final Type type;
    private final int price;
    private final int sizeInCl;

    protected Beer(String name, Type type, int price, int sizeInCl) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.sizeInCl = sizeInCl;
    }

    public int getSize() {
        return sizeInCl;
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

        if (sizeInCl != beer.sizeInCl) return false;
        if (price != beer.price) return false;
        if (name != null ? !name.equals(beer.name) : beer.name != null) return false;
        if (type != beer.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + sizeInCl;
        return result;
    }
}
