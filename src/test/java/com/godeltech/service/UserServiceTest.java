package com.godeltech.service;

import com.godeltech.entity.User;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest extends AbstractCreationTest {
    @Test
    public void crudUserTest() {
        final User entity = createNewUser("User1");
        User entityFromBase = userService.getById(entity.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(entity.getUserName(), entityFromBase.getUserName());
        userService.delete(entity.getId());
    }

    @Test
    public void crudAdminTest() {
        final User entity = createNewAdmin("Admin1");
        User entityFromBase = userService.getById(entity.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(entity.getUserName(), entityFromBase.getUserName());
        userService.delete(entity.getId());
    }
}
