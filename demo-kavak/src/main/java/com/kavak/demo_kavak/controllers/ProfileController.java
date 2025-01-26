package com.kavak.demo_kavak.controllers;

import com.kavak.demo_kavak.dtos.ProfileDTO;
import com.kavak.demo_kavak.entities.Profile;
import com.kavak.main_core.controllers.MainController;
import com.kavak.main_core.service.IMainService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/Profile")
public class ProfileController extends MainController<Profile, ProfileDTO, Long>
    implements IMainService<Profile, ProfileDTO, Long> {
    public ProfileController(IMainService<Profile, ProfileDTO, Long> mainService) {
        super(mainService);
    }
}
