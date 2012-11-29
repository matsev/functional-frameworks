package com.jayway.domain;

import static com.jayway.domain.Type.LAGER;

public class Heineken implements Beer {

    @Override
    public String getName() {
        return "Heineken";
    }

    @Override
    public Type getType() {
        return LAGER;
    }

    @Override
    public int getPrice() {
        return 6;
    }
}
