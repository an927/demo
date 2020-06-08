package com.fizzdev.demo;

import com.fizzdev.demo.dao.domain.User;
import com.fizzdev.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Testcontainers
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(initializers = DemoApplicationTests.Initializer.class)
class DemoApplicationTests {

    @Container
    public static GenericContainer rabbit = new GenericContainer("rabbitmq:3-management")
            .withExposedPorts(5672, 15672);

    @Autowired
    UserService userService;

    @Test
    void userServiceTest() throws InterruptedException {

        userService.saveUser(null);
        userService.saveUser(new User("A"));
        userService.saveUser(new User("B"));
        List<User> allUser = userService.fetchAllUsers();
        assertThat(allUser.size()).isEqualTo(2);

        userService.queueUser("A", "", "", "");
        TimeUnit.SECONDS.sleep(2);
        allUser = userService.fetchAllUsers();
        assertThat(allUser.size()).isEqualTo(2);

        userService.queueUser(null, "", "", "");
        TimeUnit.SECONDS.sleep(2);
        allUser = userService.fetchAllUsers();
        assertThat(allUser.size()).isEqualTo(2);

        userService.queueUser("C", "", "", "");
        TimeUnit.SECONDS.sleep(2);
        allUser = userService.fetchAllUsers();
        assertThat(allUser.size()).isEqualTo(3);
    }

    public static class Initializer implements
            ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.rabbitmq.host=" + rabbit.getContainerIpAddress(),
                    "spring.rabbitmq.port=" + rabbit.getMappedPort(5672)
            );
            values.applyTo(configurableApplicationContext);
        }
    }
}
