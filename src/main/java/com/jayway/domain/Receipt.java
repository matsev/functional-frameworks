package com.jayway.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Receipt {


    public List<Beer> getBeers() {
        List<Beer>  beers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            beers.add(new Guinness());
        }
        for (int i = 0; i < 20; i++) {
            beers.add(new Heineken());
        }
        for (int i = 0; i < 20; i++) {
            beers.add(new Carlsberg());
        }
        for (int i = 0; i < 20; i++) {
            beers.add(new BishopsFinger());
        }
        Collections.shuffle(beers);
        return Collections.unmodifiableList(beers);
    }

}
