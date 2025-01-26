package com.kavak.main_core.mapper;

import com.kavak.main_core.dto.MainDTO;
import com.kavak.main_core.entity.MainEntity;

public interface IMainMapper<Entity extends MainEntity, DTO extends MainDTO> {
    public Entity toDomain(DTO dto);
    public DTO fromDomain(Entity entity);
    public Entity setDomainValues(Entity entity, DTO dto);
}
