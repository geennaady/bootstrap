package web.service;

import java.util.List;

public interface UserService<T> {
    List<T> listUsers();
    void addUser(T user);
    void updateUser(T user);
    T getUserById(Long id);
    void deleteUser(Long id);
    T getUserByName(String username);
}
