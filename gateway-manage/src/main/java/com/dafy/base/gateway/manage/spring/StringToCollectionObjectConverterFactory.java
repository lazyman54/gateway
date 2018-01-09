package com.dafy.base.gateway.manage.spring;

import com.dafy.base.gateway.common.domain.ParamDefinition;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/11
 */
public final class StringToCollectionObjectConverterFactory implements ConverterFactory<String, List<?>> {

    @Override
    public <T extends List<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToCollectionObjectConverter();
    }


    private final class StringToCollectionObjectConverter<T extends List<?>> implements Converter<String, T> {

        @Override
        public T convert(String source) {

            return (T) new ArrayList<ParamDefinition>();
        }
    }

}
