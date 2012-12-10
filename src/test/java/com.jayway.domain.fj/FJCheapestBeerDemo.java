package com.jayway.domain.fj;

import com.jayway.domain.Beer;
import com.jayway.domain.Receipt;
import fj.Ord;
import org.junit.Test;

import java.util.List;

import static com.jayway.domain.fj.support.Functional.with;
import static com.jayway.domain.fj.support.FunctionalBeer.BEER_PRICE_ORDER;
import static com.jayway.domain.fj.support.FunctionalBeer.BEER_PRICE;
import static fj.Ord.ord;
import static org.fest.assertions.Assertions.assertThat;


public class FJCheapestBeerDemo {

    @Test public void
    get_the_price_of_the_cheapest_beer() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final int cheapestPrice = with(beers).map(BEER_PRICE).minimum(Ord.intOrd);

        // Then
        assertThat(cheapestPrice).isEqualTo(15);
    }

    @Test public void
    get_the_name_of_the_cheapest_beer() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final String name = with(beers).minimum(ord(BEER_PRICE_ORDER)).getName();

        // Then
        assertThat(name).isEqualTo("Heineken");
    }

    @Test public void
    get_the_name_of_the_most_expensive_beer() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final String name = with(beers).maximum(ord(BEER_PRICE_ORDER)).getName();

        // Then
        assertThat(name).isEqualTo("Guiness");
    }
}
