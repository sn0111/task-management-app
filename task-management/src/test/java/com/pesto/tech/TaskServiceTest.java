package com.pesto.tech;

import com.pesto.tech.dao.TaskDAO;
import com.pesto.tech.data.dto.TaskDTO;
import com.pesto.tech.data.entity.TaskEntity;
import com.pesto.tech.data.entity.TaskStatus;
import com.pesto.tech.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskDAO taskDAO;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskService taskService;

    private TaskDTO taskDTO;
    private TaskEntity taskEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        taskDTO = new TaskDTO();
        taskDTO.setTaskId(1L);
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Test Description");

        taskEntity = new TaskEntity();
        taskEntity.setTaskId(1L);
        taskEntity.setTitle("Test Task");
        taskEntity.setDescription("Test Description");
        taskEntity.setStatus(TaskStatus.TODO);
    }

    @Test
    public void testGetAllUserTasks() {
        when(taskDAO.findTasksByUserId(1L)).thenReturn(Collections.singletonList(taskEntity));
        when(modelMapper.map(any(TaskEntity.class), eq(TaskDTO.class))).thenReturn(taskDTO);

        assertEquals(1, taskService.getAllUserTasks(1L).size());
        assertEquals("Test Task", taskService.getAllUserTasks(1L).get(0).getTitle());

        verify(taskDAO, times(2)).findTasksByUserId(1L);
    }

    @Test
    public void testSaveTask() {
        when(modelMapper.map(any(TaskDTO.class), eq(TaskEntity.class))).thenReturn(taskEntity);
        when(taskDAO.saveTask(any(TaskEntity.class))).thenReturn(taskEntity);
        when(modelMapper.map(any(TaskEntity.class), eq(TaskDTO.class))).thenReturn(taskDTO);

        TaskDTO savedTask = taskService.saveTask(taskDTO);

        assertEquals("Test Task", savedTask.getTitle());

        verify(modelMapper, times(1)).map(any(TaskDTO.class), eq(TaskEntity.class));
        verify(taskDAO, times(1)).saveTask(any(TaskEntity.class));
        verify(modelMapper, times(1)).map(any(TaskEntity.class), eq(TaskDTO.class));
    }

    @Test
    public void testUpdateTask() {
        when(taskDAO.findTaskByTaskId(1L)).thenReturn(Optional.of(taskEntity));
        when(modelMapper.map(any(TaskDTO.class), eq(TaskEntity.class))).thenReturn(taskEntity);
        when(taskDAO.saveTask(any(TaskEntity.class))).thenReturn(taskEntity);
        when(modelMapper.map(any(TaskEntity.class), eq(TaskDTO.class))).thenReturn(taskDTO);

        TaskDTO updatedTask = taskService.updateTAsk(taskDTO);

        assertEquals("Test Task", updatedTask.getTitle());

        verify(taskDAO, times(1)).findTaskByTaskId(1L);
        verify(modelMapper, times(1)).map(any(TaskDTO.class), eq(TaskEntity.class));
        verify(taskDAO, times(1)).saveTask(any(TaskEntity.class));
        verify(modelMapper, times(1)).map(any(TaskEntity.class), eq(TaskDTO.class));
    }

    @Test
    public void testDeleteTask() {
        when(taskDAO.findTaskByTaskId(1L)).thenReturn(Optional.of(taskEntity));

        String result = taskService.deleteTask(1L);

        assertEquals("Task deleted", result);

        verify(taskDAO, times(1)).findTaskByTaskId(1L);
        verify(taskDAO, times(1)).deleteTask(taskEntity);
    }

    @Test
    public void testUpdateTaskElse() {
        when(taskDAO.findTaskByTaskId(1L)).thenReturn(Optional.empty());
        when(modelMapper.map(any(TaskDTO.class), eq(TaskEntity.class))).thenReturn(taskEntity);
        when(taskDAO.saveTask(any(TaskEntity.class))).thenReturn(taskEntity);
        when(modelMapper.map(any(TaskEntity.class), eq(TaskDTO.class))).thenReturn(taskDTO);

        TaskDTO updatedTask = taskService.updateTAsk(taskDTO);
        assertNull(updatedTask);
    }

    @Test
    public void testDeleteTaskElse() {
        when(taskDAO.findTaskByTaskId(1L)).thenReturn(Optional.empty());

        String result = taskService.deleteTask(1L);
        assertNull(result);
    }
}

