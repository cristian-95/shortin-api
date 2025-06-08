package com.cristian.shortin_api.web.mapper;

import com.cristian.shortin_api.domain.model.Url;
import com.cristian.shortin_api.web.dto.UrlUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UrlMapper {

    UrlMapper INSTANCE = Mappers.getMapper(UrlMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateFromDTO(UrlUpdateDTO dto, @MappingTarget Url entity);
}
