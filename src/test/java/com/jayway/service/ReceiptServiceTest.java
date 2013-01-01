package com.jayway.service;

import com.jayway.domain.Beer;
import com.jayway.domain.Receipt;
import com.jayway.domain.Type;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.jayway.domain.Type.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public abstract class ReceiptServiceTest {

    Receipt receipt;
    ReceiptService receiptService;

    @Before
    public void setUp() {
        receipt = new Receipt();
        receiptService = getReceiptService();
    }

    protected abstract ReceiptService getReceiptService();


    @Test
    public void verfiy_the_price_of_the_cheapest_beer() {
        // Given
        List<Beer> beers = receipt.getBeers();

        // When
        int priceOfCheapestBeer = receiptService.getPriceOfCheapestBeer(beers);

        // Then
        assertThat(priceOfCheapestBeer, is(15));
    }


    @Test
    public void verify_the_name_of_the_cheapest_beer() {
        // Given
        List<Beer> beers = receipt.getBeers();

        // When
        String name = receiptService.getNameOfCheapestBeer(beers);

        // Then
        assertThat(name, is("Heineken"));
    }


    @Test
    public void verify_the_name_of_the_most_expensive_beer() {
        // Given
        List<Beer> beers = receipt.getBeers();

        // When
        String name = receiptService.getNameOfMostExpensiveBeer(beers);

        // Then
        assertThat(name, is("Guiness"));
    }


    @Test
    public void verify_the_beer_volume() {
        // Given
        List<Beer> beers = receipt.getBeers();

        // When
        double nbrOfLitersConsumed = receiptService.getNbrOfLitersConsumed(beers);

        // Then
        assertEquals(29.6d, nbrOfLitersConsumed, 0.01d);
    }


    @Test
    public void verify_number_of_lagers() {
        // Given
        List<Beer> beers = receipt.getBeers();

        // When
        int nbrOfLagers = receiptService.getNbrOfBeersPerType(beers, LAGER);

        // Then
        assertThat(nbrOfLagers, is(40));
    }


    @Test
    public void verify_number_of_ales() {
        // Given
        List<Beer> beers = receipt.getBeers();

        // When
        int nbrOfAles = receiptService.getNbrOfBeersPerType(beers, ALE);

        // Then
        assertThat(nbrOfAles, is(20));
    }


    @Test
    public void verify_number_of_stout() {
        // Given
        List<Beer> beers = receipt.getBeers();

        // When
        int nbrOfStouts = receiptService.getNbrOfBeersPerType(beers, STOUT);

        // Then
        assertThat(nbrOfStouts, is(20));
    }


    @Test
    public void verify_cost_per_beer_type() {
        // Given
        List<Beer> beers = receipt.getBeers();

        // When
        Map<Type, Integer> costsPerBeerType = receiptService.getCostPerBeerType(beers);

        // Then
        assertThat(costsPerBeerType.get(LAGER), is(620));
        assertThat(costsPerBeerType.get(ALE), is(400));
        assertThat(costsPerBeerType.get(STOUT), is(440));
    }


    @Test
    public void verify_total_cost_of_all_beers() {
        // Given
        List<Beer> beers = receipt.getBeers();

        // When
        int totalCost = receiptService.getTotalCost(beers);

        // Then
        assertThat(totalCost, is(1460));
    }


    @Test
    public void verify_sort_beers_by_price() {
        // Given
        List<Beer> beers = receipt.getBeers();

        // When
        List<String> names = receiptService.getBeerNamesSortedAccordingToPrice(beers);

        // Then
        assertThat(names, contains( "Heineken", "Carlsberg", "Bishops Finger", "Guiness"));
    }
}
