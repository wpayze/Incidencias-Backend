package com.incidenciasvlc.mysqlservice.service;

import com.incidenciasvlc.mysqlservice.model.Role;
import com.incidenciasvlc.mysqlservice.model.User;
import com.incidenciasvlc.mysqlservice.repository.RoleRepository;
import com.incidenciasvlc.mysqlservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el id: " + id));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());

        Role role = roleRepository.findById(userDetails.getRole().getId())
                .orElseThrow(
                        () -> new RuntimeException("Rol no encontrado con el id: " + userDetails.getRole().getId()));
        user.setRole(role);

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
