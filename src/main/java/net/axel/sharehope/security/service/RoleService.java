package net.axel.sharehope.security.service;

import net.axel.sharehope.security.domain.entity.AppRole;

public interface RoleService {

    AppRole findByRole(String role);
}
