package com.jayway.domain.fj;

import com.jayway.domain.Beer;
import com.jayway.domain.Receipt;
import fj.function.Doubles;
import org.junit.Test;

import java.util.List;

import static com.jayway.domain.fj.support.Functional.with;
import static com.jayway.domain.fj.support.FunctionalBeer.BEER_SIZE;
import static com.jayway.domain.fj.support.FunctionalBeer.CL_TO_L;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Delta.delta;

public class FJNumberOfLitersDemo {

    @Test public void
    calculate_number_of_liters_drunk() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final double numberOfLitersDrunk = with(beers).map(BEER_SIZE).map(CL_TO_L).foldLeft1(Doubles.add);

        // Then
        assertThat(numberOfLitersDrunk).isEqualTo(29.6d, delta(0.01d));
    }
}
