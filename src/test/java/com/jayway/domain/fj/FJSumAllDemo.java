package com.jayway.domain.fj;

import com.jayway.domain.Beer;
import com.jayway.domain.Receipt;
import fj.F;
import fj.F2;
import fj.control.parallel.Strategy;
import fj.function.Integers;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.jayway.domain.fj.support.Functional.with;
import static com.jayway.domain.fj.support.FunctionalBeer.BEER_PRICE;
import static org.fest.assertions.Assertions.assertThat;
import static org.funcito.Funcito.callsTo;
import static org.funcito.FuncitoFJ.fFor;

public class FJSumAllDemo {

    @Test public void
    calculate_sum_of_all_beers_on_the_receipt_using_fj_api_and_anonymous_inner_classes() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final int sum = fj.data.List.list(beers.toArray(new Beer[beers.size()])).map(new F<Beer, Integer>() {
            @Override
            public Integer f(Beer beer) {
                return beer.getPrice();
            }
        }).foldLeft1(Integers.add);

        // Then
        assertThat(sum).isEqualTo(1460);
    }

    @Test public void
    calculate_sum_of_all_beers_on_the_receipt_using_wrapper_api_and_shared_map_function() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final int sum = with(beers).map(BEER_PRICE).foldLeft1(Integers.add);

        // Then
        assertThat(sum).isEqualTo(1460);
    }

    @Test public void
    calculate_sum_of_all_beers_on_the_receipt_using_own_add_function() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final int sum = with(beers).map(BEER_PRICE).foldLeft(new F2<Integer, Integer, Integer>() {
            @Override
            public Integer f(Integer sum, Integer operand) {
                return sum + operand;
            }
        }, 0);

        // Then
        assertThat(sum).isEqualTo(1460);
    }

    @Test public void
    calculate_sum_of_all_beers_on_the_receipt_using_wrapper_api_with_and_funcito_function() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final F<Beer, Integer> getPrice = fFor(callsTo(Beer.class).getPrice());
        final int sum = with(beers).map(getPrice).foldLeft1(Integers.add);

        // Then
        assertThat(sum).isEqualTo(1460);
    }

    @Test
    public void
    calculate_sum_of_all_beers_parallel() {
        // Given
        final ExecutorService executorService = Executors.newFixedThreadPool(4);
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final Strategy<Integer> strategy = Strategy.executorStrategy(executorService);
        final int numberOfCentilitersDrunk = strategy.parMap(BEER_PRICE, with(beers))._1().foldLeft1(Integers.add);


        // Then
        assertThat(numberOfCentilitersDrunk).isEqualTo(1460);
        executorService.shutdownNow();
    }
}
