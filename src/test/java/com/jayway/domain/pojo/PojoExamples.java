package com.jayway.domain.pojo;

import com.jayway.domain.Beer;
import com.jayway.domain.Receipt;
import com.jayway.domain.Type;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static com.jayway.domain.Type.LAGER;

public class PojoExamples {

    private List<Beer> beers;

    @Before
    public void setUp() {
        beers = new Receipt().getBeers();
    }

    @Test
    public void get_cheapest_beer() {
        int min = Integer.MAX_VALUE;
        for (Beer beer : beers) {
            min = Math.min(min, beer.getPrice());
        }
        System.out.println(min);
    }

    @Test
    public void get_most_expensive_beer() {
        int max = Integer.MIN_VALUE;
        for (Beer beer : beers) {
            max = Math.max(max, beer.getPrice());
        }
        System.out.println(max);
    }

    @Test
    public void get_name_of_most_expensive_beer() {
        Iterator<Beer> beerIterator = beers.iterator();
        Beer mostExpensive = beerIterator.next();
        while (beerIterator.hasNext()) {
            Beer next = beerIterator.next();
            if (next.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = next;
            }
        }
        System.out.println(mostExpensive.getName());
    }


    @Test
    public void get_name_of_lagers() {
        Map<Type, Set<Beer>> types = new HashMap<>();
        for (Beer beer : beers) {
            Type type = beer.getType();
            Set<Beer> beers = types.get(type);
            if (beers == null) {
                beers = new HashSet<>();
                types.put(type,  beers);
            }
            beers.add(beer);
        }
        System.out.println(types.get(LAGER));
    }


    @Test
    public void total_sum() {
        int sum = 0;
        for (Beer beer : beers) {
            sum += beer.getPrice();
        }
        System.out.println(sum);
    }
}
