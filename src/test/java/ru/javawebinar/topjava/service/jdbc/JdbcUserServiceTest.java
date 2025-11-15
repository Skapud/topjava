package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    Environment env;

    @Before
    public void CheckProfile() {
        Assume.assumeTrue(env.acceptsProfiles(JDBC));
    }

    @Override
    @Test
    @Ignore
    public void createWithException() throws Exception {}
}