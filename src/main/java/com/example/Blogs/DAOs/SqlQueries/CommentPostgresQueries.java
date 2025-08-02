package com.example.Blogs.DAOs.SqlQueries;

import com.example.Blogs.DAOs.DAOUtilities.DAOUtilities;

public class CommentPostgresQueries extends CommentQueries {
    private DAOUtilities daoUtilities;

    public CommentPostgresQueries(DAOUtilities daoUtilities) {
        super();
        this.daoUtilities = daoUtilities;
    }
}
