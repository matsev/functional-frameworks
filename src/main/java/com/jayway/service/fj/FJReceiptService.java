package com.jayway.service.fj;

import com.jayway.domain.Beer;
import com.jayway.domain.Type;
import com.jayway.service.ReceiptService;
import fj.*;
import fj.function.Doubles;
import fj.function.Integers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fj.Equal.equal;
import static fj.Ord.ord;
import static fj.data.List.list;

public class FjReceiptService implements ReceiptService {

    @Override
    public int getPriceOfCheapestBeer(List<Beer> beers) {
        Beer[] beerArray = beers.toArray(new Beer[beers.size()]);

        int minPrice = fj.data.List.list(beerArray).map(BEER_PRICE).minimum(Ord.intOrd);

        return minPrice;
    }

    @Override
    public String getNameOfCheapestBeer(List<Beer> beers) {
        Beer[] beerArray = beers.toArray(new Beer[beers.size()]);

        String name = list(beerArray).minimum(ord(BEER_PRICE_ORDER)).getName();

        return name;
    }

    @Override
    public String getNameOfMostExpensiveBeer(List<Beer> beers) {
        Beer[] beerArray = beers.toArray(new Beer[beers.size()]);

        String name = list(beerArray).maximum(ord(BEER_PRICE_ORDER)).getName();

        return name;
    }

    @Override
    public double getNbrOfLitersConsumed(List<Beer> beers) {
        Beer[] beerArray = beers.toArray(new Beer[beers.size()]);

        double liters = list(beerArray).map(BEER_SIZE).map(CL_TO_L).foldLeft1(Doubles.add);

        return liters;
    }

    @Override
    public int getNbrOfBeersPerType(List<Beer> beers, Type type) {
        Beer[] beerArray = beers.toArray(new Beer[beers.size()]);

        int nbrOfBeers = list(beerArray).filter(isType(type)).length();

        return nbrOfBeers;
    }

    @Override
    public Map<Type, Integer> getCostPerBeerType(List<Beer> beers) {
        Beer[] beerArray = beers.toArray(new Beer[beers.size()]);

        Map<Type, Integer> costPerType = list(beerArray).sort(ord(BEER_TYPE_ORDER)).group(equal(BEER_TYPE_EQUAL)).foldLeft(SUM_BEER_PRICE_BY_TYPE, new HashMap<Type, Integer>());

        return costPerType;
    }

    @Override
    public int getTotalCost(List<Beer> beers) {
        Beer[] beerArray = beers.toArray(new Beer[beers.size()]);

        int cost = list(beerArray).map(BEER_PRICE).foldLeft1(Integers.add);

        return cost;
    }

    @Override
    public List<String> getBeerNamesSortedAccordingToPrice(List<Beer> beers) {
        Beer[] beerArray = beers.toArray(new Beer[beers.size()]);

        fj.data.List<String> names = list(beerArray).nub().sort(ord(BEER_PRICE_ORDER)).map(BEER_NAME);

        return new ArrayList<>(names.toCollection());
    }

    /**
     * Gets the price of a beer.
     */
    public static final F<Beer,Integer> BEER_PRICE = new F<Beer, Integer>() {
        @Override
        public Integer f(Beer beer) {
            return beer.getPrice();
        }
    };

    /**
     * Orders the beers according to price.
     */
    public static final F<Beer, F<Beer, Ordering>> BEER_PRICE_ORDER = new F<Beer, F<Beer, Ordering>>() {
        @Override
        public F<Beer, Ordering> f(final Beer beer1) {
            return new F<Beer, Ordering>() {
                @Override
                public Ordering f(Beer beer2) {
                    Integer beer1Price = beer1.getPrice();
                    Integer beer2Price = beer2.getPrice();

                    if (beer1Price > beer2Price) {
                        return Ordering.GT;
                    } else if (beer1Price < beer2Price) {
                        return Ordering.LT;
                    } else {
                        return Ordering.EQ;
                    }
                }
            };
        }
    };

    /**
     * Orders the beers according to type.
     */
    public static final F<Beer, F<Beer,Ordering>> BEER_TYPE_ORDER = new F<Beer, F<Beer, Ordering>>() {
        @Override
        public F<Beer, Ordering> f(final Beer beer1) {
            return new F<Beer, Ordering>() {
                @Override
                public Ordering f(Beer beer2) {
                    Type beer1Type = beer1.getType();
                    Type beer2Type = beer2.getType();

                    int val = beer1Type.compareTo(beer2Type);
                    if (val > 0) {
                        return Ordering.GT;
                    } else if (val < 0) {
                        return Ordering.LT;
                    } else {
                        return Ordering.EQ;
                    }
                }
            };
        }
    };

    /**
     * Converts centiliters to liters.
     */
    public static final F<Integer, Double> CL_TO_L = new F<Integer, Double>() {
        @Override
        public Double f(Integer sizeInCl) {
            return sizeInCl / 100.0d;
        }
    };

    /**
     * Checks if the beer is of the specified type.
     */
    public static F<Beer, Boolean> isType(final Type type) {
        return new F<Beer, Boolean>() {
            @Override
            public Boolean f(Beer beer) {
                return beer.getType().equals(type);
            }
        };
    }

    /**
     * Checks if the beer types are equal.
     */
    public static final F<Beer, F<Beer, Boolean>> BEER_TYPE_EQUAL = new F<Beer, F<Beer, Boolean>>() {
        @Override
        public F<Beer, Boolean> f(final Beer beer) {
            return isType(beer.getType());
        }
    };

    /**
     * Gets the size of a beer.
     */
    public static final F<Beer,Integer> BEER_SIZE = new F<Beer, Integer>() {
        @Override
        public Integer f(Beer beer) {
            return beer.getSize();
        }
    };

    /**
     * Gets the name of a beer.
     */
    public static final F<Beer,String> BEER_NAME = new F<Beer, String>() {
        @Override
        public String f(Beer beer) {
            return beer.getName();
        }
    };

    public static final F2<Map<Type, Integer>, fj.data.List<Beer>, Map<Type, Integer>> SUM_BEER_PRICE_BY_TYPE = new F2<Map<Type, Integer>, fj.data.List<Beer>, Map<Type, Integer>>() {
        @Override
        public Map<Type, Integer> f(Map<Type, Integer> prices, fj.data.List<Beer> beers) {
            Integer sum = beers.map(BEER_PRICE).foldLeft1(Integers.add);
            Type type = beers.head().getType();
            prices.put(type, sum);
            return prices;
        }
    };
}
