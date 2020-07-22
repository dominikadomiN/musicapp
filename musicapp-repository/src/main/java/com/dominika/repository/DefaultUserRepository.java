package com.dominika.repository;

import com.dominika.commons.UserRepository;
import com.dominika.commons.model.User;
import com.dominika.repository.entity.UserEntity;
import com.dominika.repository.jpa.UserJpaRepository;
import com.dominika.repository.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultUserRepository implements UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserRepository.class);
    private final UserJpaRepository userJpaRepository;

    @Autowired
    public DefaultUserRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public long addUser(User newUser) {
        UserEntity userEntity = UserMapper.mapToUserEntity(newUser);
        UserEntity savedUser = userJpaRepository.save(userEntity);
        LOGGER.info("UserEntity {} added", userEntity);
        return savedUser.getId();
    }

    @Override
    public void deleteUser(long userId) {
        userJpaRepository.deleteById(userId);
    }
}
