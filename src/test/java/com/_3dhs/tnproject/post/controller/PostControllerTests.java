package com._3dhs.tnproject.post.controller;

import com._3dhs.tnproject.TnProjectApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@ContextConfiguration(classes = {TnProjectApplication.class})
public class PostControllerTests {
    @Autowired
    private PostController postController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void blogLikeListPage_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/post/likelist?memberCode=12"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("post/likelist"))
                .andDo(MockMvcResultHandlers.print());

    }
}
