package com.jayway.domain.lambdaj;

import ch.lambdaj.function.aggregate.Aggregator;
import com.jayway.domain.Beer;
import com.jayway.domain.Receipt;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.collection.LambdaCollections.with;
import static com.jayway.domain.Type.LAGER;
import static org.hamcrest.Matchers.is;

public class LambdajExamples {

    public static final Aggregator<Integer> SUM = new Aggregator<Integer>() {
        @Override
        public Integer aggregate(Iterator<? extends Integer> iterator) {
            int sum = 0;
            while (iterator.hasNext()) {
                sum += iterator.next();
            }
            return sum;
        }
    };
    private List<Beer> beers;

    @Before
    public void setUp() {
        beers = new Receipt().getBeers();
    }

    @Test
    public void get_cheapest_beer() {
        int min = with(beers).min(on(Beer.class).getPrice());
    }

    @Test
    public void get_most_expensive_beer() {
        int max = with(beers).max(on(Beer.class).getPrice());
    }

    @Test
    public void get_name_of_most_expensive_beer() {
        String name = with(beers).selectMax(on(Beer.class).getPrice()).getName();
        System.out.println(name);
    }


    @Test
    public void get_name_of_lagers() {
        List<String> names = with(beers).retain(having(on(Beer.class).getType(), is(LAGER))).extract(on(Beer.class).getName());
        System.out.println(names);
    }

    @Test
    public void get_name_of_lagers_no_duplicates() {
        Set<String> names = with(beers).retain(having(on(Beer.class).getType(), is(LAGER))).extract(on(Beer.class).getName()).distinct();
        System.out.println(names);
    }


    @Test
    public void total_sum_reduce() {
        int sum = with(beers).extract(on(Beer.class).getPrice()).aggregate(SUM);
        System.out.println(sum);
    }


    @Test
    public void total_sum() {
        int sum = with(beers).sum(on(Beer.class).getPrice());
        System.out.println(sum);
    }
}
