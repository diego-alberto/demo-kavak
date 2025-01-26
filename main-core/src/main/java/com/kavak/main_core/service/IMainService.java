package com.kavak.main_core.service;

import com.kavak.main_core.dto.MainDTO;
import com.kavak.main_core.entity.MainEntity;
import com.kavak.main_core.mapper.IMainMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IMainService<Entity extends MainEntity, DTO extends MainDTO, Key extends Object> {
    public PagingAndSortingRepository<Entity, Key> getRepository();
    public IMainMapper<Entity, DTO> getMapper();
    public DTO create(DTO dto, IMainMapper<Entity, DTO> mapper);
    public DTO update(Key id, DTO dto, IMainMapper<Entity, DTO> mapper);
    public DTO get(Key id, IMainMapper<Entity, DTO> mapper);
    public Page<DTO> getAll(Pageable pageable, IMainMapper<Entity, DTO> mapper);
    void delete(Key id);
    public long getCount();
}
