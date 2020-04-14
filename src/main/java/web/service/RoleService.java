package web.service;

import web.model.Role;

import java.util.Set;

public interface RoleService {
    void addRole(Role role);
    Role getRoleById(Long id);
    void deleteRole(Long id);
    Set getAuthorityById(Long id);
}
