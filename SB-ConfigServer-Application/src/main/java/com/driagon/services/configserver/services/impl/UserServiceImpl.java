package com.driagon.services.configserver.services.impl;

import com.driagon.services.configserver.dto.requests.UpdatePasswordRequest;
import com.driagon.services.configserver.dto.requests.UpdateUserRequest;
import com.driagon.services.configserver.dto.requests.UserRequest;
import com.driagon.services.configserver.dto.responses.UserResponse;
import com.driagon.services.configserver.mappers.UserMapper;
import com.driagon.services.configserver.repositories.IRoleRepository;
import com.driagon.services.configserver.repositories.IUserRepository;
import com.driagon.services.configserver.services.IUserService;
import com.driagon.services.error.exceptions.BusinessException;
import com.driagon.services.error.exceptions.NotFoundException;
import com.driagon.services.error.exceptions.ProcessException;
import com.driagon.services.logging.annotations.ExceptionLog;
import com.driagon.services.logging.annotations.Loggable;
import com.driagon.services.logging.annotations.Mask;
import com.driagon.services.logging.constants.Level;
import com.driagon.services.logging.utils.MaskedLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository repository;

    private final IRoleRepository roleRepository;

    private final UserMapper mapper;

    private final PasswordEncoder encryptor;

    private final MaskedLogger log = MaskedLogger.getLogger(this.getClass());

    @Override
    @Loggable(message = "Retrieving all users", level = Level.INFO, unexpectedExceptionLevel = Level.ERROR, exceptions = {
            @ExceptionLog(
                    value = NotFoundException.class,
                    message = "No users found in the system.",
                    exceptionLevel = Level.WARN
            ),
            @ExceptionLog(
                    value = ProcessException.class,
                    message = "An error occurred while accessing the database.",
                    printStackTrace = true,
                    exceptionLevel = Level.WARN
            )
    })
    @Transactional(readOnly = true)
    public Set<UserResponse> getAllUsers() {
        try {
            var users = repository.findAll()
                    .stream()
                    .map(mapper::mapUserEntityToUserResponse)
                    .collect(Collectors.toSet());

            if (users.isEmpty()) {
                throw new NotFoundException("No users found in the system.");
            }

            return users;
        } catch (DataAccessException e) {
            log.error("Database access error.", e);
            throw new ProcessException("An error occurred while accessing the database.");
        }
    }

    @Override
    @Loggable(message = "Retrieving user by Id", level = Level.INFO, unexpectedExceptionLevel = Level.ERROR, exceptions = {
            @ExceptionLog(
                    value = NotFoundException.class,
                    message = "User with ID {0} not found."
            ),
            @ExceptionLog(
                    value = ProcessException.class,
                    message = "An error occurred while accessing the database.",
                    printStackTrace = true
            )
    })
    @Transactional(readOnly = true)
    public UserResponse getUserById(@Mask Long id) {
        log.info("Retrieving user with ID {}", id);
        var user = this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found."));
        return this.mapper.mapUserEntityToUserResponse(user);
    }

    @Override
    @Loggable(message = "Creating user {0}", level = Level.INFO, unexpectedExceptionLevel = Level.ERROR, exceptions = {
            @ExceptionLog(
                    value = BusinessException.class,
                    message = "The email provided is already in use."
            ),
            @ExceptionLog(
                    value = DataAccessException.class,
                    message = "An exception occurred while accessing the database.",
                    printStackTrace = true
            )
    })
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        log.info("Creating user with email {}", userRequest);
        log.info("Creating user with email {}", userRequest.getEmail());
        this.repository.findByEmail(userRequest.getEmail())
                .ifPresent(user -> {
                    throw new BusinessException("This email is already in use.");
                });

        // Validate role existence
        var role = this.roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new NotFoundException("Role with ID " + userRequest.getRoleId() + " not found."));

        // User does not exist, proceed with creation
        var user = this.mapper.mapUserRequestToUserEntity(userRequest);
        log.info("Encrypting password for user {}", user.getEmail());
        user.setPassword(this.encryptor.encode(user.getPassword()));
        user.setRole(role);

        try {
            user = this.repository.save(user);
            log.info("User created successfully with ID {}", user.getId());
            return this.mapper.mapUserEntityToUserResponse(user);
        } catch (DataAccessException e) {
            throw new ProcessException("An error occurred while accessing the database.");
        }
    }

    @Override
    @Loggable(message = "Updating user with id {0}", level = Level.INFO, unexpectedExceptionLevel = Level.ERROR, exceptions = {
            @ExceptionLog(
                    value = NotFoundException.class,
                    message = "User with id {0} not found."
            ),
            @ExceptionLog(
                    value = ProcessException.class,
                    message = "An error occurred while accessing the database.",
                    printStackTrace = true
            )
    })
    @Transactional
    public UserResponse updateUser(@Mask Long id, UpdateUserRequest request) {
        log.info("Updating user with ID {} and request {}", id, request);
        try {
            var user = this.repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found."));

            this.repository.findByEmail(request.getEmail())
                    .filter(existingUser -> !existingUser.getId().equals(id))
                    .ifPresent(existingUser -> {
                        throw new BusinessException("Este email ya está en uso por otro usuario.");
                    });

            var role = this.roleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> new NotFoundException("Rol con ID " + request.getRoleId() + " no encontrado."));

            this.mapper.mapUpdateUserRequestToUserEntity(request, user);
            user.setRole(role);
            user = this.repository.save(user);
            return this.mapper.mapUserEntityToUserResponse(user);
        } catch (DataAccessException e) {
            throw new ProcessException("Ocurrió un error al acceder a la base de datos.");
        }
    }

    @Override
    @Loggable(message = "Deleting user with ID {0}", level = Level.INFO, unexpectedExceptionLevel = Level.ERROR, exceptions = {
            @ExceptionLog(
                    value = NotFoundException.class,
                    message = "User with ID {0} not found."
            ),
            @ExceptionLog(
                    value = ProcessException.class,
                    message = "An error occurred while accessing the database.",
                    printStackTrace = true
            )
    })
    @Transactional
    public void deleteUser(@Mask Long userId) {
        try {
            var user = this.repository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found."));

            this.repository.delete(user);
        } catch (DataAccessException e) {
            throw new ProcessException("An error occurred while accessing the database.");
        }
    }

    @Override
    @Loggable(message = "Updating password for user with ID {0}", level = Level.INFO, unexpectedExceptionLevel = Level.ERROR, exceptions = {
            @ExceptionLog(
                    value = NotFoundException.class,
                    message = "User with ID {0} not found."
            ),
            @ExceptionLog(
                    value = ProcessException.class,
                    message = "An error occurred while accessing the database.",
                    printStackTrace = true
            )
    })
    @Transactional
    public void updateUserPassword(Long userId, UpdatePasswordRequest request) {
        try {
            var user = this.repository.findById(userId).orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found."));

            String encodedPassword = this.encryptor.encode(request.getNewPassword());

            user.setPassword(encodedPassword);
            this.repository.save(user);
        } catch (DataAccessException e) {
            throw new ProcessException("An error occurred while accessing the database.");
        }
    }
}