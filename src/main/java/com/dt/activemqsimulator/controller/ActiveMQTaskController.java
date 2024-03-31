package com.dt.activemqsimulator.controller;

import com.dt.activemqsimulator.dto.ActiveMQTaskInfoDto;
import com.dt.activemqsimulator.logic.ActiveMQTaskLogic;
import com.dt.activemqsimulator.logic.TaskCancellationLogic;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActiveMQTaskController {
    private final ActiveMQTaskLogic activeMQTaskLogic;
    private final TaskCancellationLogic taskCancellationLogic;

    @Autowired
    public ActiveMQTaskController(
            ActiveMQTaskLogic activeMQTaskLogic,
            TaskCancellationLogic taskCancellationLogic
    ) {
        this.activeMQTaskLogic = activeMQTaskLogic;
        this.taskCancellationLogic = taskCancellationLogic;
    }

    @ApiOperation(value = "특정 taskId에 해당 하는 task 취소", notes = "cancel task by taskId")
    @PostMapping("/cancel-task/{taskId}")
    public ResponseEntity<String> cancelTask(@PathVariable String taskId) {
        taskCancellationLogic.requestCancellation(taskId);

        return ResponseEntity.ok("작업 취소 요청됨: " + taskId);
    }

    // Rest api documentation add
    @ApiOperation(value = "작동 중인 모든 task 조회", notes = "Get running tasks")
    @GetMapping("/running-task")
    public ResponseEntity<List<ActiveMQTaskInfoDto>> getRunningTasks() {
        List<ActiveMQTaskInfoDto> taskInfoList = activeMQTaskLogic.makeTaskInfoList();

        return ResponseEntity.ok(taskInfoList);
    }
}