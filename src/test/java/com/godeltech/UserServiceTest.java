package com.godeltech;

import com.godeltech.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest extends AbstractCreationTest{
    @Test
    public void createUserTest() {
        final User firstUser = createNewUser();
        User entityFromBase = userService.getById(firstUser.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(firstUser.getUserName(), entityFromBase.getUserName());
        userService.delete(firstUser.getId());
    }
}
