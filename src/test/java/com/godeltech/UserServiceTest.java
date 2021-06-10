package com.godeltech;

import com.godeltech.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest extends AbstractCreationTest{
    @Test
    public void crudTest() {
        final User entity = createNewUser();
        User entityFromBase = userService.getById(entity.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(entity.getUserName(), entityFromBase.getUserName());
        userService.delete(entity.getId());
    }
}
