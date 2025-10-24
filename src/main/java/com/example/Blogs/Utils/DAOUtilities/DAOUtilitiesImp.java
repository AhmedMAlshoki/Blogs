package com.example.Blogs.Utils.DAOUtilities;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class DAOUtilitiesImp implements DAOUtilities {
    @Override
    public String preparingParamForTheQuery(List<Long> ids) {
        return String.join(",", Collections.nCopies(ids.size(), "?"));
    }
}
