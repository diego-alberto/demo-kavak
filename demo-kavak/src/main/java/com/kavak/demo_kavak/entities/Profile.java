package com.kavak.demo_kavak.entities;

import com.kavak.main_core.entity.MainEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

import java.util.Calendar;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Profile extends MainEntity<Long> {
    private String name;
    private String lastName;
    private Calendar birthday;
}
