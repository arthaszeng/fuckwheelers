package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.mappers.SurveyMapper;
import com.trailblazers.freewheelers.model.NpsReport;
import com.trailblazers.freewheelers.model.SurveyComments;
import com.trailblazers.freewheelers.model.SurveyEntry;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SurveyServiceTest {

    SurveyService surveyService;

    @Mock
    SqlSession sqlSession;
    @Mock
    SurveyMapper surveyMapper;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(sqlSession.getMapper(SurveyMapper.class)).thenReturn(surveyMapper);
        surveyService = new SurveyService(sqlSession);
    }

    @Test
    public void shouldSaveSurveyResults() throws Exception {
        final SurveyEntry surveyEntry = new SurveyEntry(10, "no comment");

        surveyService.submitSurvey(12L, new SurveyEntry(10, "no comment"));

        verify(surveyMapper).insert(eq(12L), argThat(new SurveyEntryMatcher(surveyEntry)));
        verify(sqlSession).commit();
    }

    @Test
    public void shouldFetchNpsReport() throws Exception {

        NpsReport expectedNpsReport = new NpsReport(1, 2, 3, 6);
        when(surveyMapper.generateNpsReport()).thenReturn(expectedNpsReport);

        NpsReport actualNpsReport = surveyService.generateNpsReport();

        verify(surveyMapper).generateNpsReport();
        assertEquals(actualNpsReport, expectedNpsReport);
    }

    @Test
    public void shouldFetchSurveyComments() {
        ArrayList<String> promoterComments = new ArrayList<String>(Arrays.asList("Positive Comment"));
        ArrayList<String> detractorComments = new ArrayList<String>(Arrays.asList("Negative Comment"));
        ArrayList<String> passiveComments = new ArrayList<String>(Arrays.asList("Passive Comment"));

        when(surveyMapper.getPromoterComments()).thenReturn(promoterComments);
        when(surveyMapper.getDetractorComments()).thenReturn(detractorComments);
        when(surveyMapper.getPassiveComments()).thenReturn(passiveComments);

        SurveyComments actualSurveyComments = surveyService.getSurveyComments();
        verify(surveyMapper).getPromoterComments();
        verify(surveyMapper).getDetractorComments();
        verify(surveyMapper).getPassiveComments();
        assertEquals(actualSurveyComments.getPromoterComments(), promoterComments);
        assertEquals(actualSurveyComments.getDetractorComments(), detractorComments);
        assertEquals(actualSurveyComments.getPassiveComments(), passiveComments);

    }

    @Test
    public void shouldDeleteAllSurveyEntries() throws Exception {
        surveyService.deleteAll();

        verify(surveyMapper).deleteAll();
        verify(sqlSession).commit();
    }

    private class SurveyEntryMatcher extends ArgumentMatcher<SurveyEntry> {
        private SurveyEntry surveyEntry;

        public SurveyEntryMatcher(SurveyEntry surveyEntry) {

            this.surveyEntry = surveyEntry;
        }

        @Override
        public boolean matches(Object o) {
            SurveyEntry entry = (SurveyEntry) o;
            return surveyEntry.getComment().equals(entry.getComment()) &&
                    surveyEntry.getRating() == entry.getRating();
        }
    }

}
