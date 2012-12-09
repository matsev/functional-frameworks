package com.jayway.domain.fj.support;

import com.jayway.domain.Beer;
import com.jayway.domain.Type;
import fj.F;
import fj.F2;
import fj.Ordering;
import fj.data.List;
import fj.function.Integers;

import java.util.Map;

public class FunctionalBeer {
    public static final F<Beer,Integer> BEER_PRICE = new F<Beer, Integer>() {
        @Override
        public Integer f(Beer beer) {
            return beer.getPrice();
        }
    };

    public static final F<Beer,Type> BEER_TYPE = new F<Beer, Type>() {
        @Override
        public Type f(Beer beer) {
            return beer.getType();
        }
    };

    public static final F<Beer,Integer> BEER_SIZE = new F<Beer, Integer>() {
        @Override
        public Integer f(Beer beer) {
            return beer.getSize();
        }
    };

    public static final F<Beer,String> BEER_NAME = new F<Beer, String>() {
        @Override
        public String f(Beer beer) {
            return beer.getName();
        }
    };

    public static final F<Beer,F<Beer,Boolean>> BEER_TYPE_EQUAL = new F<Beer, F<Beer, Boolean>>() {
        @Override
        public F<Beer, Boolean> f(final Beer beer) {
            return type(beer.getType());
        }
    };

    public static final F<Beer,F<Beer,Boolean>> BEER_NAME_EQUAL = new F<Beer, F<Beer, Boolean>>() {
        @Override
        public F<Beer, Boolean> f(final Beer beer1) {
            return new F<Beer, Boolean>() {
                @Override
                public Boolean f(Beer beer2) {
                    return beer1.getName().equals(beer2.getName());
                }
            };
        }
    };

    public static final F<Beer,F<Beer,Boolean>> BEER_PRICE_EQUAL = new F<Beer, F<Beer, Boolean>>() {
        @Override
        public F<Beer, Boolean> f(final Beer beer1) {
            return new F<Beer, Boolean>() {
                @Override
                public Boolean f(Beer beer2) {
                    return beer1.getPrice() == beer2.getPrice();
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
                    return comparableToOrder(i);
                }
            };
        }
    };

    public static final F<Beer,F<Beer,Ordering>> BEER_NAME_ORDER = new F<Beer, F<Beer, Ordering>>() {
        @Override
        public F<Beer, Ordering> f(final Beer beer1) {
            return new F<Beer, Ordering>() {
                @Override
                public Ordering f(Beer beer2) {
                    final String beer1Name = beer1.getName();
                    final String beer2Name = beer2.getName();
                    final int i = beer1Name.compareTo(beer2Name);
                    return comparableToOrder(i);
                }
            };
        }
    };

    public static final F<Beer,F<Beer,Ordering>> BEER_PRICE_ORDER = new F<Beer, F<Beer, Ordering>>() {
        @Override
        public F<Beer, Ordering> f(final Beer beer1) {
            return new F<Beer, Ordering>() {
                @Override
                public Ordering f(Beer beer2) {
                    final Integer beer1Price = beer1.getPrice();
                    final Integer beer2Price = beer2.getPrice();
                    final int i = beer1Price.compareTo(beer2Price);
                    return comparableToOrder(i);
                }
            };
        }
    };


    public static final F2<Map<Type,Integer>, List<Beer>,Map<Type,Integer>> SUM_BEER_PRICE_BY_TYPE = new F2<Map<Type, Integer>, fj.data.List<Beer>, Map<Type, Integer>>() {
        @Override
        public Map<Type, Integer> f(Map<Type, Integer> prices, fj.data.List<Beer> beers) {
            final Integer sum = beers.map(BEER_PRICE).foldLeft1(Integers.add);
            final Type type = beers.head().getType();
            prices.put(type, sum);
            return prices;

        }
    };

    public static <T> F2<Map<T,Integer>, List<Beer>,Map<T,Integer>> sumBeerPriceBy(final F<Beer, T> f) {
        return new F2<Map<T, Integer>, fj.data.List<Beer>, Map<T, Integer>>() {
            @Override
            public Map<T, Integer> f(Map<T, Integer> prices, fj.data.List<Beer> beers) {
                final Integer sum = priceOf(beers);
                final Beer head = beers.head();
                final T result = f.f(head);
                prices.put(result, sum);
                return prices;

            }
        };
    }

    public static F<Beer, Boolean> type(final Type type) {
        return new F<Beer, Boolean>() {
            @Override
            public Boolean f(Beer beer) {
                return beer.getType() == type;
            }
        };
    }

    /**
     * Converts centiliters to liters
     */
    public static final F<Integer,Double> CL_TO_L = new F<Integer, Double>() {
            @Override
            public Double f(Integer sizeInCl) {
                return sizeInCl / 100.0d;
            }
        };

    private static Integer priceOf(List<Beer> beers) {
        return beers.map(BEER_PRICE).foldLeft1(Integers.add);
    }

    // Convert a result of a compare to a Ordering
    private static Ordering comparableToOrder(int comparable) {
        if(comparable == 0) {
            return Ordering.EQ;
        }
        return comparable < 0 ? Ordering.LT : Ordering.GT;
    }
}
