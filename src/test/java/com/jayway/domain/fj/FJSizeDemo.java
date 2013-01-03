package com.jayway.domain.fj;

import com.jayway.domain.Beer;
import com.jayway.domain.Receipt;
import org.junit.Test;

import java.util.List;

import static com.jayway.domain.Type.ALE;
import static com.jayway.domain.fj.support.Functional.with;
import static com.jayway.domain.fj.support.FunctionalBeer.type;
import static org.fest.assertions.Assertions.assertThat;

public class FJSizeDemo {

    @Test public void
    calculate_number_of_beers_of_type_ale() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final int numberOfAles = with(beers).filter(type(ALE)).length();

        // Then
        assertThat(numberOfAles).isEqualTo(20);
    }
}
