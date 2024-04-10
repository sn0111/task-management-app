package com.pesto.tech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pesto.tech.controller.TasksController;
import com.pesto.tech.data.dto.TaskDTO;
import com.pesto.tech.data.dto.UserDTO;
import com.pesto.tech.data.entity.TaskStatus;
import com.pesto.tech.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = TasksController.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
@DirtiesContext
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    private TaskDTO taskDTO;

    @BeforeEach
    public void setUp() {
        taskDTO = new TaskDTO();
        taskDTO.setTaskId(1L);
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Test Description");
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        taskDTO.setUser(userDTO);
        taskDTO.setStatus(TaskStatus.TODO.toString());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                "test",null,Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testUserTasks() throws Exception {
        when(taskService.getAllUserTasks(1L)).thenReturn(Collections.singletonList(taskDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks?userId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("Test Task"));

        verify(taskService, times(1)).getAllUserTasks(1L);
    }

    @Test
    public void testSaveTask() throws Exception {
        when(taskService.saveTask(any(TaskDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/add")
                        .content(objectMapper.writeValueAsString(taskDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Test Task"));

        verify(taskService, times(1)).saveTask(any(TaskDTO.class));
    }

    @Test
    public void testUpdateTask() throws Exception {
        when(taskService.updateTAsk(any(TaskDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/tasks/update")
                        .content(objectMapper.writeValueAsString(taskDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Test Task"));

        verify(taskService, times(1)).updateTAsk(any(TaskDTO.class));
    }

    @Test
    public void testDeleteTask() throws Exception {
        when(taskService.deleteTask(1L)).thenReturn("Task deleted");

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("Task deleted"));

        verify(taskService, times(1)).deleteTask(1L);
    }
}

