package com.dikann.webservice;

import com.dikann.webservice.dto.CategoryDto;
import com.dikann.webservice.utils.ApplicationConst;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryControllerTest extends AbstractTest {
    @Before
    public void init() {
        super.setUp();
    }

    @Test
    public void getCategory() throws Exception {
        String uri = ApplicationConst.baseUrl + "category/2";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject categoryDtoResponse = new JSONObject(content);
        assertTrue(categoryDtoResponse.has("id"));
    }


    @Test
    public void getAllCategories() throws Exception {
        String uri = ApplicationConst.baseUrl + "category";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        CategoryDto[] categoryDtos = super.mapFromJson(content, CategoryDto[].class);
        assertTrue(categoryDtos.length > 0);
    }

    @Test
    public void addCategory() throws Exception {
        String uri = ApplicationConst.baseUrl + "category";
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("test");
        categoryDto.setDesc("test description");
        categoryDto.setImageUrl("https://test-url.com/test.png");

        String inputJson = super.mapToJson(categoryDto);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject categoryDtoResponse = new JSONObject(content);
        assertTrue(categoryDtoResponse.has("id"));
    }

    @Test
    public void updateCategory() throws Exception {
        String uri = ApplicationConst.baseUrl + "category/3";
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("test-edited");
        categoryDto.setDesc("test description-edited");
        categoryDto.setImageUrl("https://test-url.com/test-edited.png");

        String inputJson = super.mapToJson(categoryDto);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject categoryDtoResponse = new JSONObject(content);
        assertTrue(categoryDtoResponse.has("id"));
    }

    @Test
    public void deleteCategory() throws Exception {
        String uri = ApplicationConst.baseUrl + "category/4";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject categoryDtoResponse = new JSONObject(content);
        assertTrue(categoryDtoResponse.getBoolean("success"));
    }
}
