package com.example.sep4android.Models;

public class ArchiveDao {

    private static ArchiveDao instance;

    private ArchiveDao()
    {
    }

    public static ArchiveDao getInstance()
    {
        if(instance==null)
        {
            instance = new ArchiveDao();
        }
        return instance;
    }

}
