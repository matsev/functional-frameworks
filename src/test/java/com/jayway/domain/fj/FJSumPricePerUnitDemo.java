package com.jayway.domain.fj;

import com.jayway.domain.Beer;
import com.jayway.domain.Receipt;
import com.jayway.domain.Type;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jayway.domain.Type.*;
import static com.jayway.domain.fj.support.Functional.with;
import static com.jayway.domain.fj.support.FunctionalBeer.*;
import static fj.Equal.equal;
import static fj.Ord.ord;
import static fj.function.Integers.add;
import static org.fest.assertions.Assertions.assertThat;

public class FJSumPricePerUnitDemo {

    @Test public void
    calculate_price_of_beers_with_type_lager() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final int sum = with(beers).filter(type(LAGER)).map(BEER_PRICE).foldLeft1(add);

        // Then
        assertThat(sum).isEqualTo(620);
    }

    @Test public void
    calculate_price_of_beers_with_type_ale() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final int sum = with(beers).filter(type(ALE)).map(BEER_PRICE).foldLeft1(add);

        // Then
        assertThat(sum).isEqualTo(400);
    }

    @Test public void
    calculate_price_of_beers_with_type_stout() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final int sum = with(beers).filter(type(STOUT)).map(BEER_PRICE).foldLeft1(add);

        // Then
        assertThat(sum).isEqualTo(440);
    }

    // Note: group doesn't sort before grouping, VERY confusing:
    // https://groups.google.com/group/functionaljava/browse_thread/thread/e14222b6087bb894/758bce96a58eb44e?lnk=gst&q=group#758bce96a58eb44e
    @Test public void
    calculate_price_of_beers_per_type() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final Map<Type, Integer> prices = with(beers).sort(ord(BEER_TYPE_ORDER)).group(equal(BEER_TYPE_EQUAL)).foldLeft(SUM_BEER_PRICE_BY_TYPE, new HashMap<Type, Integer>());

        // Then
        assertThat(prices.get(LAGER)).isEqualTo(620);
        assertThat(prices.get(ALE)).isEqualTo(400);
        assertThat(prices.get(STOUT)).isEqualTo(440);

        // All prices
        assertThat(with(prices.values()).foldLeft1(add)).isEqualTo(1460);
    }

    @Test public void
    calculate_price_of_beers_per_type_generic_version() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final Map<Type, Integer> prices = with(beers).sort(ord(BEER_TYPE_ORDER)).group(equal(BEER_TYPE_EQUAL)).foldLeft(sumBeerPriceBy(BEER_TYPE), new HashMap<Type, Integer>());

        System.out.println(prices.keySet());
        // Then
        assertThat(prices.get(LAGER)).isEqualTo(620);
        assertThat(prices.get(ALE)).isEqualTo(400);
        assertThat(prices.get(STOUT)).isEqualTo(440);
    }

    @Test public void
    calculate_price_of_beers_per_name() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final Map<String, Integer> prices = with(beers).sort(ord(BEER_NAME_ORDER)).group(equal(BEER_NAME_EQUAL)).foldLeft(sumBeerPriceBy(BEER_NAME), new HashMap<String, Integer>());

        // Then
        assertThat(prices.get("Heineken")).isEqualTo(300);
        assertThat(prices.get("Bishops Finger")).isEqualTo(400);
        assertThat(prices.get("Carlsberg")).isEqualTo(320);
        assertThat(prices.get("Guiness")).isEqualTo(440);
    }

    // :)
    @Test public void
    calculate_price_of_beers_per_price() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final Map<Integer, Integer> prices = with(beers).sort(ord(BEER_PRICE_ORDER)).group(equal(BEER_PRICE_EQUAL)).foldLeft(sumBeerPriceBy(BEER_PRICE), new HashMap<Integer, Integer>());

        // Then
        assertThat(prices.get(16)).isEqualTo(320);
        assertThat(prices.get(20)).isEqualTo(400);
        assertThat(prices.get(22)).isEqualTo(440);
        assertThat(prices.get(15)).isEqualTo(300);
    }
}
