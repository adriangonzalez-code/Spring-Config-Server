package com.driagon.services.configserver.services;

import com.driagon.services.configserver.dto.requests.UpdatePasswordRequest;
import com.driagon.services.configserver.dto.requests.UpdateUserRequest;
import com.driagon.services.configserver.dto.requests.UserRequest;
import com.driagon.services.configserver.dto.responses.UserResponse;

import java.util.Set;

public interface IUserService {

    /**
     * Retrieves all users in the system.
     *
     * @return a set of all users
     */
    Set<UserResponse> getAllUsers();


    /**
     * Retrieves a user by their Email.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified email
     */
    UserResponse getUserById(Long id);

    /**
     * Creates a new user with the provided details.
     *
     * @param userRequest the details of the user to create
     * @return the created user
     */
    UserResponse createUser(UserRequest userRequest);

    /**
     * Updates an existing user with the provided details.
     *
     * @param id the ID of the user to update
     * @param request the details to update the user with
     * @return the updated user
     */
    UserResponse updateUser(Long id, UpdateUserRequest request);

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     */
    void deleteUser(Long userId);

    /**
     * Updates the password of a user.
     *
     * @param userId the ID of the user whose password is to be updated
     * @param request the request containing the new password
     */
    void updateUserPassword(Long userId, UpdatePasswordRequest request);
}