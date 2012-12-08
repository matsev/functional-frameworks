package com.jayway.domain.fj;

import com.jayway.domain.Beer;
import com.jayway.domain.Receipt;
import com.jayway.domain.Type;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jayway.domain.Type.LAGER;
import static com.jayway.domain.fj.support.Functional.with;
import static com.jayway.domain.fj.support.FunctionalBeer.*;
import static fj.Equal.equal;
import static fj.function.Integers.add;
import static org.fest.assertions.Assertions.assertThat;

public class FJSumTypeDemo {

    @Test public void
    calculate_sum_of_beers_with_specific_type() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final int sum = with(beers).filter(type(LAGER)).map(BEER_PRICE).foldLeft1(add);

        // Then
        assertThat(sum).isEqualTo(620);
    }

    @Ignore("Group is NOT group by!!! VERY confusing: https://groups.google.com/group/functionaljava/browse_thread/thread/e14222b6087bb894/758bce96a58eb44e?lnk=gst&q=group#758bce96a58eb44e") @Test public void
    calculate_sum_of_beers_per_type() {
        // Given
        final Receipt receipt = new Receipt();
        List<Beer> beers = receipt.getBeers();

        // When
        final Map<Type, Integer> prices = with(beers).group(equal(BEER_TYPE_EQUAL)).foldLeft(SUM_PER_TYPE, new HashMap<Type, Integer>());

        // Then
        assertThat(prices.get(LAGER)).isEqualTo(620);
    }
}
