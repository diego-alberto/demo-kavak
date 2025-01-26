package com.kavak.main_core.service;

import com.kavak.main_core.dto.MainDTO;
import com.kavak.main_core.entity.MainEntity;
import com.kavak.main_core.mapper.IMainMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainService <Entity extends MainEntity, DTO extends MainDTO, Key extends Object>
        implements IMainService<Entity, DTO, Key>{
    protected final JpaRepository<Entity, Key> repository;

    protected final IMainMapper<Entity, DTO> mapper;

    public MainService(JpaRepository<Entity, Key> repository, IMainMapper<Entity, DTO> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PagingAndSortingRepository<Entity, Key> getRepository() {
        return repository;
    }

    @Override
    public IMainMapper<Entity, DTO> getMapper() {
        return mapper;
    }

    @Override
    public DTO create(DTO dto, IMainMapper<Entity, DTO> mapper) {
        DTO result = null;
        if (dto.getId() == null || dto.getId().toString().isEmpty()) {
            Entity domainEntity = mapper.toDomain(dto);
            Entity dbEntity = repository.save(domainEntity);
            result = mapper.fromDomain(dbEntity);
        }
        return result;
    }

    @Override
    public DTO update(Key id, DTO dto, IMainMapper<Entity, DTO> mapper) {
        DTO result = null;
        Optional<Entity> dbResult = repository.findById(id);
        if (dbResult.isPresent()) {
            Entity obj = dbResult.get();
            Entity updateEntity = repository.save(mapper.setDomainValues(obj, dto));
            result = mapper.fromDomain(updateEntity);
        }
        return result;
    }

    @Override
    public DTO get(Key id, IMainMapper<Entity, DTO> mapper) {
        DTO result = null;
        Entity dbResult = get(id);
        if (null != dbResult) {
            result = mapper.fromDomain(dbResult);
        }
        return result;
    }

    @Transactional
    public Entity get(Key id) {
        Entity result = null;
        Optional<Entity> dbResult = repository.findById(id);
        if (dbResult.isPresent()) {
            result = dbResult.get();
        }
        return result;
    }

    @Override
    public Page<DTO> getAll(Pageable pageable, IMainMapper<Entity, DTO> mapper) {
        Page<Entity> page = repository.findAll(pageable);
        List<DTO> content = new ArrayList<>();
        page.getContent().forEach((obj) -> {
            try {
                DTO dto = mapper.fromDomain(obj);
                content.add(dto);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });
        Page<DTO> result = new PageImpl(content, pageable, page.getTotalElements());
        return result;
    }

    @Override
    @Transactional
    public void delete(Key id) {
        delete(id, false);
    }

    @Transactional
    public void delete(Key id, boolean forceHardDelete) {
        Optional<Entity> dbResult = repository.findById(id);
        if (dbResult.isPresent()) {
            Entity obj = dbResult.get();
            if (!forceHardDelete) {
                obj.setEnabled(false);
                repository.save(obj);
            } else {
                repository.deleteById(id);
            }
        }
    }

    @Override
    public long getCount() {
        return repository.count();
    }
}
