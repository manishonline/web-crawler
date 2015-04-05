package com.manishk.webcrawler.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

/**
 * Created by manish on 03/04/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class BaseJdbcTest {

    @Autowired
    ApplicationContext ctx;

    @Test
    public void test1(){
        Assert.assertNotNull(ctx);
        DataSource dataSource = (DataSource)ctx.getBean("dataSource");
        Assert.assertNotNull(dataSource);
    }
}
