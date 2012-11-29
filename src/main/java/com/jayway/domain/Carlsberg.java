package com.jayway.domain;

import static com.jayway.domain.Type.LAGER;

public class Carlsberg implements Beer {

    @Override
    public String getName() {
        return "Carlsberg";
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
