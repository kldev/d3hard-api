package com.kldev.d3.storage.dao;

import com.kldev.d3.controller.model.IOrderByRequest;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class QueryUtil {

    public static <T> String setupOrder(String querySQL, IOrderByRequest request, Class<T> clazz)
    {
        if (request.getOrderBy() == null || request.getOrderBy().isEmpty())
        {
            return querySQL;
        }

        Field[] fields = clazz.getDeclaredFields();

        Optional<String> field = Arrays.stream(fields)
                .filter(x->x.getName().equalsIgnoreCase(request.getOrderBy()))
                .map(Field::getName).findAny();

        if (field.isPresent())
        {
            querySQL += " ORDER BY " + field.get();
        }
        else {
            return querySQL;
        }

        if (request.getDirection().equalsIgnoreCase("asc"))
        {
            querySQL += " asc";
        }
        else{
            querySQL += " desc";
        }

        return querySQL;
    }
}
