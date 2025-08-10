package com.mortis.ainews.infra.service.impl.user;

import com.mortis.ainews.domain.model.UserDO;
import com.mortis.ainews.infra.persistence.converter.facade.ConverterFacade;
import com.mortis.ainews.infra.persistence.repository.interfaces.UserRepository;
import com.mortis.ainews.infra.service.exception.ErrorCode;
import com.mortis.ainews.infra.service.exception.ServiceException;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mortis.ainews.domain.service.user.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ConverterFacade converter;

    @Override
    public Optional<UserDO> findUserById(Long userId) {
        var user = userRepository.findByIdAndDeleted(userId, 0);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(converter.userConverter.toDO(user.get()));
    }

    @Override
    public Optional<UserDO> findUserByAc(String ac) {
        var user = userRepository.findByAcAndDeleted(ac, 0);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(converter.userConverter.toDO(user.get()));
    }

    @Override
    public UserDO createUser(UserDO userDO) {

        var user = userRepository.findByAcAndDeleted(userDO.getAc(), 0);

        if (!user.isEmpty() && user.get().getDeleted() == 0) {
            throw new ServiceException(ErrorCode.USER_ALREADY_EXISTS);
        }

        return converter.userConverter.toDO(userRepository.save(converter.userConverter.toPO(userDO)));
    }

    @Override
    public boolean updateUser(UserDO userDO) {
        var user = userRepository.findByIdAndDeleted(userDO.getId(), 0);

        if (user.isEmpty() || user.get().getDeleted() == 1) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }

        // 只更新非null字段
        var existingUser = user.get();
        if (userDO.getUserName() != null) {
            existingUser.setUserName(userDO.getUserName());
        }
        if (userDO.getAc() != null) {
            existingUser.setAc(userDO.getAc());
        }

        userRepository.save(existingUser);

        return true;
    }

    @Override
    public boolean deleteUser(Long userId) {
        var user = userRepository.findByIdAndDeleted(userId, 0);

        if (user.isEmpty() || user.get().getDeleted() == 1) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }

        user.get().setDeleted(1);
        userRepository.save(user.get());

        return true;
    }
}