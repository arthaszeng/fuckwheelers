package com.trailblazers.freewheelers.persistence.persistence;

import com.trailblazers.freewheelers.mappers.SurveyMapper;
import com.trailblazers.freewheelers.model.NpsReport;
import com.trailblazers.freewheelers.model.SurveyEntry;
import com.trailblazers.freewheelers.persistence.persistence.MapperTestBase;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SurveyMapperTest extends MapperTestBase {
    private SurveyMapper surveyMapper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        surveyMapper = getSqlSession().getMapper(SurveyMapper.class);
        surveyMapper.deleteAll();
    }

    @Test
    public void shouldInsertAndGetAccount() throws Exception {
        SurveyEntry surveyEntry = new SurveyEntry(5, "ok ok");

        surveyMapper.insert(123L, surveyEntry);
        ResultSet resultSet = execute("select account_id, rating, comment from survey_entry where account_id = 123;");

        resultSet.next();
        assertThat(resultSet.getLong("account_id"), is(123L));
        assertThat(resultSet.getInt("rating"), is(5));
        assertThat(resultSet.getString("comment"), is("ok ok"));
    }

    @Test
    public void shouldGenerateReport() throws Exception {

        int promoters = 2;
        int detractors = 6;
        int passives = 2;

        double totalAmountOfSurveys = 10;
        Double promotersPercentage = (promoters / totalAmountOfSurveys) * 100;
        Double detractorsPercentage = (detractors / totalAmountOfSurveys) * 100;
        Double passivesPercentage = (passives / totalAmountOfSurveys) * 100;

        Double npsScore = promotersPercentage - detractorsPercentage;

        for (int i = 1; i <= 10; i++) {
            insertRecord(i, i);
        }

        NpsReport npsReport = surveyMapper.generateNpsReport();

        assertThat(npsReport.getPromotersPercentage(), is(promotersPercentage));
        assertThat(npsReport.getDetractorsPercentage(), is(detractorsPercentage));
        assertThat(npsReport.getPassivesPercentage(), is(passivesPercentage));
        assertThat(npsReport.getNpsScore(), is(npsScore));
    }

    @Test
    public void shouldGenerateEmptyReport() throws Exception {
        NpsReport npsReport = surveyMapper.generateNpsReport();

        assertThat(npsReport.getPromotersPercentage(), is(0.0));
        assertThat(npsReport.getDetractorsPercentage(), is(0.0));
        assertThat(npsReport.getPassivesPercentage(), is(0.0));
        assertThat(npsReport.getNpsScore(), is(0.0));
    }

    @Test
    public void shouldReturnPromoterComments() {
        surveyMapper.insert(1L, new SurveyEntry(10, "positive comment"));
        surveyMapper.insert(1L, new SurveyEntry(0, "negative comment"));
        surveyMapper.insert(1L, new SurveyEntry(8, "passive comment"));

        List<String> promoterComments = surveyMapper.getPromoterComments();
        assertThat(promoterComments.size(), is(1));
        assertThat(promoterComments.get(0), is("positive comment"));
    }

    @Test
    public void shouldReturnDetractorComments() {
        surveyMapper.insert(1L, new SurveyEntry(10, "positive comment"));
        surveyMapper.insert(1L, new SurveyEntry(0, "negative comment"));
        surveyMapper.insert(1L, new SurveyEntry(8, "passive comment"));

        List<String> detractorComments = surveyMapper.getDetractorComments();
        assertThat(detractorComments.size(), is(1));
        assertThat(detractorComments.get(0), is("negative comment"));
    }

    @Test
    public void shouldReturnPassiveComments() {
        surveyMapper.insert(1L, new SurveyEntry(10, "positive comment"));
        surveyMapper.insert(1L, new SurveyEntry(0, "negative comment"));
        surveyMapper.insert(1L, new SurveyEntry(8, "passive comment"));

        List<String> passiveComments = surveyMapper.getPassiveComments();
        assertThat(passiveComments.size(), is(1));
        assertThat(passiveComments.get(0), is("passive comment"));
    }

    @Test
    public void shouldNotReturnEmptyPromoterComments() {
        surveyMapper.insert(1L, new SurveyEntry(10, "comment"));
        surveyMapper.insert(1L, new SurveyEntry(10, ""));

        assertThat(surveyMapper.getPromoterComments().size(), is(1));
    }

    @Test
    public void shouldNotReturnEmptyDetractorComments() {
        surveyMapper.insert(1L, new SurveyEntry(0, "comment"));
        surveyMapper.insert(1L, new SurveyEntry(0, ""));

        assertThat(surveyMapper.getDetractorComments().size(), is(1));
    }

    @Test
    public void shouldNotReturnEmptyPassiveComments() {
        surveyMapper.insert(1L, new SurveyEntry(8, "comment"));
        surveyMapper.insert(1L, new SurveyEntry(8, ""));

        assertThat(surveyMapper.getPassiveComments().size(), is(1));
    }

    @Test
    public void shouldDeleteAllEntries() throws SQLException {
        insertRecord(123L, 10);

        surveyMapper.deleteAll();
        ResultSet resultSet = execute("select count(*) as entry_count from survey_entry;");
        resultSet.next();

        assertThat(resultSet.getInt("entry_count"), is(0));
    }

    private void insertRecord(long accountId, int rating) {
        surveyMapper.insert(accountId, new SurveyEntry(rating % 11, "some comment"));
    }
}
