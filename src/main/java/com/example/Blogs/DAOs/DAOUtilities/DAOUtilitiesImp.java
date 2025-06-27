package com.example.Blogs.DAOs.DAOUtilities;

import java.util.List;
import java.util.stream.Stream;

public class DAOUtilitiesImp implements DAOUtilities {
    @Override
    public Object[] preparingParamForTheQuery(List<Long> ids) {
        return Stream.concat(
                Stream.concat(ids.stream(), ids.stream()),
                ids.stream()
        ).toArray();
    }
}
