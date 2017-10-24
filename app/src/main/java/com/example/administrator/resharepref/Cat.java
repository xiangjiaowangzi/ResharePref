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

    @PrefPut(value = YEAR)
    boolean setYear(String year);

    @PrefGet(value = YEAR)
    boolean getYear();

}
