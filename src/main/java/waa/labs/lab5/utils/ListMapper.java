package waa.labs.lab5.utils;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListMapper<S,T> {
    ModelMapper modelMapper;

    @Autowired
    public ListMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<T> mapList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(entity -> modelMapper.map(entity, targetClass))
                .collect(Collectors.toList());
    }
}
