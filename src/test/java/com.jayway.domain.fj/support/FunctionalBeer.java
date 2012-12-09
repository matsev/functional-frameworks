package com.jayway.domain.fj.support;

import com.jayway.domain.Beer;
import com.jayway.domain.Type;
import fj.F;
import fj.F2;
import fj.Ordering;
import fj.data.List;

import java.util.Map;

import static fj.function.Integers.add;

public class FunctionalBeer {
    public static final F<Beer,Integer> BEER_PRICE = new F<Beer, Integer>() {
        @Override
        public Integer f(Beer beer) {
            return beer.getPrice();
        }
    };

    public static final F<Beer,F<Beer,Boolean>> BEER_TYPE_EQUAL = new F<Beer, F<Beer, Boolean>>() {
        @Override
        public F<Beer, Boolean> f(final Beer beer) {
            return type(beer.getType());
        }
    };

    public static final F<Beer,F<Beer,Ordering>> BEER_PRICE_ORDER = new F<Beer, F<Beer, Ordering>>() {
        @Override
        public F<Beer, Ordering> f(final Beer beer1) {
            return new F<Beer, Ordering>() {
                @Override
                public Ordering f(Beer beer2) {
                    final int beer1Price = beer1.getPrice();
                    final int beer2Price = beer2.getPrice();
                    return beer1Price <= beer2Price ? Ordering.LT : Ordering.GT;
                }
            };
        }
    };

    public static final F<Beer,F<Beer,Ordering>> BEER_TYPE_ORDER = new F<Beer, F<Beer, Ordering>>() {
        @Override
        public F<Beer, Ordering> f(final Beer beer1) {
            return new F<Beer, Ordering>() {
                @Override
                public Ordering f(Beer beer2) {
                    final Type beer1Type = beer1.getType();
                    final Type beer2Type = beer2.getType();
                    final int i = beer1Type.compareTo(beer2Type);
                    if(i == 0) {
                        return Ordering.EQ;
                    }
                    return i < 0 ? Ordering.LT : Ordering.GT;
                }
            };
        }
    };


    public static final F2<Map<Type,Integer>, List<Beer>,Map<Type,Integer>> SUM_PER_TYPE = new F2<Map<Type, Integer>, fj.data.List<Beer>, Map<Type, Integer>>() {
        @Override
        public Map<Type, Integer> f(Map<Type, Integer> prices, fj.data.List<Beer> beers) {
            final Integer sum = beers.map(BEER_PRICE).foldLeft1(add);
            final Type type = beers.head().getType();
            prices.put(type, sum);
            return prices;

        }
    };

    public static F<Beer, Boolean> type(final Type type) {
        return new F<Beer, Boolean>() {
            @Override
            public Boolean f(Beer beer) {
                return beer.getType() == type;
            }
        };
    }
}
