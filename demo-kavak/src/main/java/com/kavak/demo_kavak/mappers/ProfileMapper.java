package com.kavak.demo_kavak.mappers;

import com.kavak.demo_kavak.dtos.ProfileDTO;
import com.kavak.demo_kavak.entities.Profile;
import com.kavak.main_core.mapper.MainMapper;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper extends MainMapper<Profile, ProfileDTO> {
}
