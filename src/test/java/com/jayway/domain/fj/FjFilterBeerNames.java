package com.jayway.domain.fj;

import com.jayway.domain.Beer;
import com.jayway.domain.Receipt;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static com.jayway.domain.Type.LAGER;
import static com.jayway.domain.fj.support.Functional.with;
import static com.jayway.domain.fj.support.FunctionalBeer.BEER_NAME;
import static com.jayway.domain.fj.support.FunctionalBeer.type;

public class FjFilterBeerNames {

    private Receipt receipt;
    private List<Beer> beers;

    @Before
    public void setUp() {
        receipt = new Receipt();
        beers = receipt.getBeers();
    }

    @Test
    public void get_names_of_all_lager() {

        Collection<String> names = with(beers).filter(type(LAGER)).map(BEER_NAME).nub().toCollection();

        System.out.println(names);
    }
}
