package com.sezioo.wechar_demo.activiti;

import com.sezioo.wechat_demo.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @ClassName GroupPorcessTest
 * @Description TODO
 * @Author qinpeng
 * @Date 2020/1/14 14:39
 * @Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= DemoApplication.class)
@WebAppConfiguration
@Slf4j
public class GroupPorcessTest {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Before
    public void init(){
        User user = identityService.newUser("Lising");
        user.setFirstName("Li");
        user.setLastName("sing");
        user.setEmail("Lising@cmiot");
        identityService.saveUser(user);

        Group group = identityService.newGroup("deptLeader");
        group.setName("部门领导");
        group.setType("assignment");
        identityService.saveGroup(group);

        identityService.createMembership("Lising","deptLeader");
    }

    @After
    public void afterInvokeTestMethod(){
        identityService.deleteMembership("Lising","deptLeader");
        identityService.deleteGroup("deptLeader");
        identityService.deleteUser("Lising");
    }

    @Test
    public void groupTaskTest(){
        runtimeService.startProcessInstanceByKey("groupProcessTest");
        Task task = taskService.createTaskQuery().taskCandidateUser("Lising").singleResult();
        taskService.claim(task.getId(), "Lising");
        taskService.complete(task.getId());
    }
}
