package org.example.be_java_hisp_w26_g04.repository.users.imp;

import lombok.NoArgsConstructor;

import org.example.be_java_hisp_w26_g04.model.User;
import org.example.be_java_hisp_w26_g04.repository.users.IUsersRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@NoArgsConstructor
public class UsersRepositoryImp implements IUsersRepository {
    private Set<User> users;

    @Override
    public Set<User> findAll() {
        return users;
    }

    @Override
    public boolean save(User user) {
        return users.add(user);
    }

    @Override
    public Optional<User> findById(int id) {
        return users.stream().filter(u -> u.getUser_id() ==  id).findFirst();
    }
}
