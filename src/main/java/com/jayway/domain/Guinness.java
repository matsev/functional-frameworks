package com.jayway.domain;

import static com.jayway.domain.Type.STOUT;

public class Guinness implements Beer {

    @Override
    public String getName() {
        return "Guiness";
    }

    @Override
    public Type getType() {
        return STOUT;
    }

    @Override
    public int getPrice() {
        return 8;
    }
}
