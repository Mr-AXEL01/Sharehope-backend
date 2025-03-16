package net.axel.sharehope.service;

import net.axel.sharehope.security.domain.entity.AppUser;

public interface UserService {

    AppUser findUserEntity(String username);
}
