package com.kavak.demo_kavak.dtos;

import com.kavak.main_core.dto.MainDTO;
import lombok.Data;

import java.util.Calendar;

@Data
public class ProfileDTO extends MainDTO<Long> {
    private String name;
    private String lastName;
    private Calendar birthday;
}
