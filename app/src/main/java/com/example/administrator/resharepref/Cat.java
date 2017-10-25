package com.example.administrator.resharepref;

import com.example.library.annotations.PrefGet;
import com.example.library.annotations.PrefModel;
import com.example.library.annotations.PrefPut;

/**
 * Created by Lbin on 2017/10/24.
 */

@PrefModel(value = "cat")
public interface Cat {

    String YEAR = "year";
    String NAME = "name";

    @PrefPut(YEAR)
    boolean setYear(int year);

    @PrefGet(YEAR)
    int getYear();

    @PrefPut(NAME)
    boolean setName(String name);

    @PrefGet(NAME)
    String getName();


}
