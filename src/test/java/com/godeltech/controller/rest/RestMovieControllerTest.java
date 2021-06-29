package com.godeltech.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godeltech.dto.MovieRequest;
import com.godeltech.entity.Country;
import com.godeltech.entity.Genre;
import com.godeltech.entity.User;
import com.godeltech.service.AbstractCreationTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AutoConfigureMockMvc
class RestMovieControllerTest extends AbstractCreationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void createNewMovieTest() throws Exception {
        final String userName = "Admin1";
        User admin = createNewAdmin(userName);
        String token = getToken(authenticate(userName));
        addMovieWithToken(token);
    }

    private void addMovieWithToken(String accessToken) throws Exception {
        final String content = getJsonMovieRequest(getMovieRequest());
        mockMvc.perform(post("/api/v1/movies")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String getJsonMovieRequest(MovieRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(request);
    }

    private MovieRequest getMovieRequest() {
        Country country = createNewCountry("Country1");
        Set<Genre> genres = Stream.of(createNewGenre("New Genre1"),
                createNewGenre("New Genre2")).collect(Collectors.toSet());
        MovieRequest request = new MovieRequest();
        request.setTitle("Title");
        request.setDirector("Director");
        request.setDescription("Bla-bla-bla");
        request.setReleaseYear(2000);
        request.setCountryId(country.getId());
        request.setGenreIds(genres.stream().map(Genre::getId).collect(Collectors.toSet()));
        return request;
    }

    private MvcResult authenticate(String userName) throws Exception {
        final String content = String.format("{\"login\": \"%s\", \"password\": \"%s\"}", userName, userName);
        return mockMvc.perform(post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private String getToken(MvcResult result) throws UnsupportedEncodingException, JSONException {
        String jsonString = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getString("token");
    }


}