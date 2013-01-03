package com.jayway.domain.guava;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import com.jayway.domain.Beer;
import com.jayway.domain.Receipt;
import com.jayway.domain.Type;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.FluentIterable.from;
import static com.jayway.domain.Type.LAGER;

public class GuavaExamples {

    public static final Function<Beer,Integer> BEER_PRICE = new Function<Beer, Integer>() {
        @Override
        public Integer apply(Beer beer) {
            return beer.getPrice();
        }
    };

    public static final Function<Beer,String> BEER_NAME = new Function<Beer, String>() {
        @Override
        public String apply(Beer beer) {
            return beer.getName();
        }
    };

    public static final Comparator<Beer> BEER_PRICE_COMPARATOR = new Comparator<Beer>() {
        @Override
        public int compare(Beer first, Beer second) {
            return first.getPrice() - second.getPrice();
        }
    };
    private List<Beer> beers;

    @Before
    public void setUp() {
        beers = new Receipt().getBeers();
    }

    @Test
    public void get_cheapest_beer() {
        int min = Ordering.from(BEER_PRICE_COMPARATOR).min(beers).getPrice();
        System.out.println(min);
    }

    @Test
    public void get_cheapest_beer2() {
        int min = Ints.min(Ints.toArray(from(beers).transform(BEER_PRICE).toImmutableList()));
        System.out.println(min);
    }

    @Test
    public void get_cheapest_beer3() {
        int min = from(beers).transform(BEER_PRICE).toSortedImmutableList(Ordering.natural()).get(0);
        System.out.println(min);
    }

    @Test
    public void get_most_expensive_beer() {
        int min = from(beers).transform(BEER_PRICE).toSortedImmutableList(Ordering.natural().reverse()).get(0);
        System.out.println(min);
    }

    @Test
    public void get_name_of_most_expensive_beer() {
        String name = Ordering.from(BEER_PRICE_COMPARATOR).max(beers).getName();
        System.out.println(name);
    }


    @Test
    public void get_name_of_lagers() {
        Iterable<String> names = from(beers).filter(type(LAGER)).transform(BEER_NAME).toImmutableSet();

        System.out.println(names);
    }

    private static Predicate<Beer> type(final Type type) {
        return new Predicate<Beer>() {
            @Override
            public boolean apply(Beer beer) {
                return beer.getType().equals(type);
            }
        };
    }

    @Test
    public void get_name_of_lagers_no_duplicates() {
        Iterable<String> names = from(beers).filter(type(LAGER)).transform(BEER_NAME).toImmutableSet();
        System.out.println(names);
    }

}
