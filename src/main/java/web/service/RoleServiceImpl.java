package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.repository.RoleRepository;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleDao;

    @Transactional
    @Override
    public void addRole(Role role) {
        roleDao.save(role);
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleById(Long id) {
        return roleDao.getOne(id);
    }

    @Transactional
    @Override
    public void deleteRole(Long id) {
        roleDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Set getAuthorityById(Long id) {
        return roleDao.getAuthorityById(id);
    }
}
