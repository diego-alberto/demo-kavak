package com.kavak.demo_kavak.services;

import com.kavak.demo_kavak.dtos.ProfileDTO;
import com.kavak.demo_kavak.entities.Profile;
import com.kavak.main_core.mapper.IMainMapper;
import com.kavak.main_core.service.IMainService;
import com.kavak.main_core.service.MainService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService extends MainService<Profile, ProfileDTO, Long>
    implements IMainService<Profile, ProfileDTO, Long> {
    public ProfileService(JpaRepository<Profile, Long> repository, IMainMapper<Profile, ProfileDTO> mapper) {
        super(repository, mapper);
    }
}
