package com.jayway.domain.fj;

import com.jayway.domain.Beer;
import com.jayway.domain.Receipt;
import fj.control.parallel.Strategy;
import fj.function.Integers;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.jayway.domain.fj.support.Functional.with;
import static com.jayway.domain.fj.support.FunctionalBeer.BEER_SIZE;
import static org.fest.assertions.Assertions.assertThat;

public class FJParallelSumDemo {

    @Test
    public void
    calculate_number_of_centiliters_drunk_in_parallel() {
        // Given
        final ExecutorService executorService = Executors.newFixedThreadPool(4);
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final Strategy<Integer>  strategy = Strategy.executorStrategy(executorService);
        final int numberOfCentilitersDrunk = strategy.parMap(BEER_SIZE, with(beers))._1().foldLeft1(Integers.add);


        // Then
        assertThat(numberOfCentilitersDrunk).isEqualTo(2960);
        executorService.shutdownNow();
    }

    @Test
    public void
    calculate_number_of_centiliters_drunk_sequential() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final int numberOfCentilitersDrunk = with(beers).map(BEER_SIZE).foldLeft1(Integers.add);


        // Then
        assertThat(numberOfCentilitersDrunk).isEqualTo(2960);
    }
}
