package com.kavak.main_core.mapper;

import com.kavak.main_core.dto.MainDTO;
import com.kavak.main_core.entity.MainEntity;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;

public class MainMapper<Entity extends MainEntity, DTO extends MainDTO> implements IMainMapper<Entity, DTO> {

    private final ModelMapper modelMapper;

    public MainMapper() {
        modelMapper = new ModelMapper();
    }

    public Class<Entity> getDomainEntityClass() {
        return (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Class<DTO> getDTOClass() {
        return (Class<DTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Override
    @Transactional
    public Entity toDomain(DTO dto) {
        return null == dto ? null : modelMapper.map(dto, getDomainEntityClass());
    }

    @Override
    @Transactional
    public DTO fromDomain(Entity entity) {
        return null == entity ? null :  modelMapper.map(entity, getDTOClass());
    }

    @Override
    @Transactional
    public Entity setDomainValues(Entity entity, DTO dto) {
        dto.setId(entity.getId());
        modelMapper.map(dto, entity);
        return entity;
    }
}
