package com.ruoyi;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Model;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ProcessRunTest {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    /**
     * 流程部署测试
     */
    @Test
    public void testDeployment(){

    }

    /**
     * 运行测试
     */
    @ParameterizedTest
    @ValueSource(strings = {"1","2","3"})
    public void testRun(String key){
        Model model = repositoryService.newModel();
        model.setCategory("学习");
        model.setName("单人审批请假单");
    }

}
