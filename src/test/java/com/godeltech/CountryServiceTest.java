package com.godeltech;

import com.godeltech.entity.Country;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CountryServiceTest extends AbstractCreationTest{
    @Test
    public void crudTest() {
        final Country entity = createNewCountry();
        Country entityFromBase = countryService.getById(entity.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(entity.getCountryName(), entityFromBase.getCountryName());
        countryService.delete(entity.getId());
    }
}
