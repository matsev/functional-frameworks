package com.jayway.domain.fj.support;

import fj.data.List;

import java.util.Collection;

public class Functional {

    public static <T> List<T> with(Collection<T> collection) {
        return (List<T>) List.list(collection.toArray());
    }


}
