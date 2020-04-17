package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.repository.UserRepository;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService<User> {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() { return userRepository.findAll(); }

    @Transactional
    @Override
    public void addUser(User user) { userRepository.save(user); }

    @Transactional
    @Override
    public void updateUser(User user) { userRepository.save(user); }

    @Transactional
    @Override
    public User getUserById(Long id) { return userRepository.getOne(id); }

    @Transactional
    @Override
    public void deleteUser(Long id) { userRepository.deleteById(id); }

    @Transactional
    @Override
    public User getUserByEmail(String email) { return userRepository.findByEmail(email); }
}
