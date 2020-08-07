package com.kkb.mybatis.test;

import jdk.internal.util.xml.impl.Input;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class TestMybatis {




    @Test
    public void testMyBatis() throws IOException {

        InputStream is = Resources.getResourceAsStream("");
        SqlSessionFactoryBuilder  builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = builder.build(is);
        SqlSession sqlSession = sessionFactory.openSession();


    }


}
