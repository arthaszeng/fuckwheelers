package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.mappers.MyBatisUtil;
import com.trailblazers.freewheelers.mappers.SurveyMapper;
import com.trailblazers.freewheelers.model.NpsReport;
import com.trailblazers.freewheelers.model.SurveyComments;
import com.trailblazers.freewheelers.model.SurveyEntry;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {

    private SqlSession sqlSession;
    private SurveyMapper surveyMapper;

    public SurveyService() {
        this(MyBatisUtil.getSqlSessionFactory().openSession());
    }

    public SurveyService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        this.surveyMapper = sqlSession.getMapper(SurveyMapper.class);
    }

    public void submitSurvey(Long accountId, SurveyEntry surveyEntry) {
        surveyMapper.insert(accountId, surveyEntry);
        sqlSession.commit();
    }

    public NpsReport generateNpsReport() {
        return surveyMapper.generateNpsReport();
    }

    public void deleteAll() {
        surveyMapper.deleteAll();
        sqlSession.commit();
    }

    public SurveyComments getSurveyComments() {
        List<String> promoterComments = surveyMapper.getPromoterComments();
        List<String> passiveComments = surveyMapper.getPassiveComments();
        List<String> detractorComments = surveyMapper.getDetractorComments();
        return new SurveyComments(promoterComments, detractorComments, passiveComments);
    }
}
