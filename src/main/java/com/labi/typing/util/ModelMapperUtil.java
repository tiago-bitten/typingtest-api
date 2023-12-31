package com.labi.typing.util;

import org.modelmapper.ModelMapper;

public class ModelMapperUtil {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <D, T> D map(T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }
}
