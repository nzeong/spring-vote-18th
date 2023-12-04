package com.gotcha.vote.global.converter;

import com.gotcha.vote.user.domain.PartName;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPartName implements Converter<String, PartName> {
    @Override
    public PartName convert(String source) {
        return PartName.valueOf(source.toUpperCase());
    }
}
