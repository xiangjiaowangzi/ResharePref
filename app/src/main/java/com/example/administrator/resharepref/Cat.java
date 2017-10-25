package com.example.administrator.resharepref;

import com.example.library.annotations.PrefBody;
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

    @PrefPut()
    boolean setYear(@PrefBody(YEAR) int year);

    @PrefGet(YEAR)
    int getYear();

    @PrefPut()
    boolean setName(@PrefBody(NAME)String name);

    @PrefGet(NAME)
    String getName();

    @PrefPut()
    boolean setCat(@PrefBody(NAME)String name , @PrefBody(YEAR)int year);

}
