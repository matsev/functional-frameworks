package com.jayway.service;

import com.jayway.domain.Beer;
import com.jayway.domain.Type;

import java.util.List;
import java.util.Map;

public interface ReceiptService {

    int getPriceOfCheapestBeer(List<Beer> beers);

    String getNameOfCheapestBeer(List<Beer> beers);

    String getNameOfMostExpensiveBeer(List<Beer> beers);

    double getNbrOfLitersConsumed(List<Beer> beers);

    int getNbrOfBeersPerType(List<Beer> beers, Type type);

    Map<Type, Integer> getCostPerBeerType(List<Beer> beers);

    int getTotalCost(List<Beer> beers);

    List<String> getBeerNamesSortedAccordingToPrice(List<Beer> beers);
}
