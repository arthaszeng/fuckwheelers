package com.trailblazers.freewheelers.persistence.persistence;

import com.trailblazers.freewheelers.mappers.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class MapperTestBase {
    private SqlSession sqlSession;

    @Before
    public void setUp() throws Exception {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
    }

    @After
    public void tearDown() throws Exception {
        sqlSession.rollback();
        sqlSession.close();
    }

    protected SqlSession getSqlSession() {
        return sqlSession;
    }

    protected ResultSet execute(String statement) throws SQLException {
        return sqlSession.getConnection().prepareStatement(statement).executeQuery();
    }
}
