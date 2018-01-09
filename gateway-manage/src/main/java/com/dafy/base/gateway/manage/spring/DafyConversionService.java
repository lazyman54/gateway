package com.dafy.base.gateway.manage.spring;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.GenericConversionService;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/11
 */
public class DafyConversionService implements ConversionService {

    private final GenericConversionService conversionService;

    public DafyConversionService(GenericConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public void init() {
        conversionService.addConverterFactory(new StringToCollectionObjectConverterFactory());
    }

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {

        return conversionService.canConvert(sourceType, targetType);
    }

    @Override
    public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return conversionService.canConvert(sourceType, targetType);
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        return conversionService.convert(source, targetType);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return conversionService.convert(source, sourceType, targetType);
    }
}
