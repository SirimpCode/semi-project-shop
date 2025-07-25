package com.github.semiprojectshop.repository.sihu.user;

import com.github.semiprojectshop.config.module.converter.MyEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RolesEnum implements MyEnumInterface {
    ROLE_ADMIN("운영자"),
    ROLE_SUPER_USER("슈퍼유저"),
    ROLE_USER("유저");

    private final String value;
}
