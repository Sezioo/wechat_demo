package com.sezioo.wechar_demo.activiti;

import com.sezioo.wechat_demo.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @ClassName IdentityTest
 * @Description Activiti Identity Test
 * @Author qinpeng
 * @Date 2020/1/14 8:51
 * @Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= DemoApplication.class)
@WebAppConfiguration
@Slf4j
public class IdentityTest {
    @Autowired
    private IdentityService identityService;

    @Test
    public void addUserTest(){
        User user = identityService.newUser("Lising");
        user.setFirstName("Li");
        user.setLastName("sing");
        user.setEmail("Lising@cmiot.com");
        identityService.saveUser(user);
    }

    @Test
    public void queryUserTest(){
        User user = identityService.createUserQuery().userId("Lising").singleResult();
        log.info(user.getId());
    }

    @Test
    public void deleteUserTest() {
        identityService.deleteUser("Lising");
    }

    @Test
    public void addGroupTest(){
        Group group = identityService.newGroup("deptLeader");
        group.setName("部门领导");
        group.setType("assignment");
        identityService.saveGroup(group);
    }

    @Test
    public void queryGroupTest(){
        List<Group> groupList = identityService.createGroupQuery().groupId("deptLeader").list();
        groupList.forEach(group -> {
            log.info(group.getName());
        });
    }

    @Test
    public void deleteGroupTest(){
        identityService.deleteGroup("deptLeader");
        queryGroupTest();
    }

    @Test
    public void membershipTest(){
        addUserTest();
        addGroupTest();
        identityService.createMembership("Lising", "deptLeader");
        List<User> userList = identityService.createUserQuery().memberOfGroup("deptLeader").list();
        userList.forEach(user -> {
            log.info(user.getId());
        });
        List<Group> groupList = identityService.createGroupQuery().groupMember("Lising").list();
        groupList.forEach(group -> {
            log.info(group.getName());
        });
        identityService.deleteMembership("Lising", "deptLeader");
        deleteUserTest();
        deleteGroupTest();
    }

}
