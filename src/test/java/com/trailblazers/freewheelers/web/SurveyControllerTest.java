package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.model.NpsReport;
import com.trailblazers.freewheelers.model.SurveyComments;
import com.trailblazers.freewheelers.model.SurveyEntry;
import com.trailblazers.freewheelers.service.AccountService;
import com.trailblazers.freewheelers.service.SurveyService;
import com.trailblazers.freewheelers.web.forms.SurveyEntryForm;
import org.apache.http.auth.BasicUserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import java.security.Principal;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SurveyControllerTest {

    private static final String COMMENT = "comment";
    private static final int A_DAY_IN_SECONDS = 86400;
    private static final long ACCOUNT_ID = 12L;
    private static final int SURVEY_ENTRY_RATING = 10;
    private static final String USERNAME = "john";


    @Mock
    private SurveyService surveyService;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private SurveyController surveyController;

    private Principal principal = new BasicUserPrincipal(USERNAME);
    private Account userAccount = new Account();

    @Before
    public void setUp() {
        userAccount.setAccount_id(ACCOUNT_ID);
        given(accountService.getAccountByName(USERNAME)).willReturn(userAccount);
    }

    @Test
    public void shouldReturnFormViewName() {
        String viewName = surveyController.get(new MockHttpServletResponse());
        assertThat(viewName, is("survey/form"));
    }

    @Test
    public void shouldReturnSurveyReportViewName() {
        ModelAndView reportModelAndView = surveyController.getReport();
        assertThat(reportModelAndView.getViewName(), is("survey/report"));
    }

    @Test
    public void shouldSetCookie() {
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        surveyController.get(mockResponse);

        Cookie surveyCookie = mockResponse.getCookie("SurveyTaken");
        assertThat(surveyCookie.getValue(), is("true"));
        assertThat(surveyCookie.getMaxAge(), is(A_DAY_IN_SECONDS));
    }

    @Test
    public void shouldSubmitSurveyForUserIfThereIsNoBindingError() {
        BindingResult bindingResult = mock(BindingResult.class);
        given(bindingResult.hasErrors()).willReturn(false);
        ModelAndView modelAndView = surveyController.post(principal, new SurveyEntryForm(SURVEY_ENTRY_RATING, COMMENT),
                                                          bindingResult);

        assertThat(modelAndView.getViewName(), is("survey/confirmation"));
        verify(surveyService).submitSurvey(eq(ACCOUNT_ID), aSurveyEntry(SURVEY_ENTRY_RATING, COMMENT));
    }

    @Test
    public void shouldHaveMandatoryFieldErrorIfSurveyRatingIsInvalid() {
        SurveyEntryForm surveyEntryFormWithoutRating = new SurveyEntryForm(null, COMMENT);
        ModelAndView modelAndView = surveyController.post(principal, surveyEntryFormWithoutRating, getBindingResultWithError());
        ModelMap modelMap = (ModelMap) modelAndView.getModelMap().get("validation");
        Boolean mandatoryFieldMissing = (Boolean) modelMap.get("mandatoryFieldMissing");

        assertThat(mandatoryFieldMissing, is(true));
    }

    @Test
    public void shouldReturnToTheFormPageWhenSurveyEntryFormIsInvalid() {
        SurveyEntryForm invalidSurveyEntryForm = new SurveyEntryForm(null, COMMENT);

        ModelAndView modelAndView = surveyController.post(principal, invalidSurveyEntryForm, getBindingResultWithError());

        assertThat(modelAndView.getViewName(), is("survey/form"));
    }

    @Test
    public void shouldNotCallValidationServiceWhenThereAreFormErrors() {
        SurveyEntryForm invalidSurveyEntryForm = new SurveyEntryForm(null, COMMENT);

        surveyController.post(principal, invalidSurveyEntryForm, getBindingResultWithError());

        verify(surveyService, never()).submitSurvey(anyLong(), any(SurveyEntry.class));
    }

    @Test
    public void shouldAddANpsReportToTheReport() {
        NpsReport expectedNpsReport = new NpsReport(1, 2, 3, 6);
        given(surveyService.generateNpsReport()).willReturn(expectedNpsReport);

        ModelAndView reportModelAndView = surveyController.getReport();
        NpsReport actualNpsReport = (NpsReport) reportModelAndView.getModelMap().get("npsReport");

        assertThat(actualNpsReport, is(expectedNpsReport));
    }

    @Test
    public void shouldAddASurveyCommentsToTheReport() {
        SurveyComments expectedSurveyComments = new SurveyComments(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());
        given(surveyService.getSurveyComments()).willReturn(expectedSurveyComments);

        ModelAndView reportModelAndView = surveyController.getReport();
        SurveyComments actualSurveyComments = (SurveyComments) reportModelAndView.getModelMap().get("surveyComments");

        assertThat(actualSurveyComments, is(expectedSurveyComments));
    }

    private BindingResult getBindingResultWithError() {
        BindingResult bindingResult = mock(BindingResult.class);
        given(bindingResult.hasErrors()).willReturn(true);
        return bindingResult;
    }

    private SurveyEntry aSurveyEntry(final int rating, final String comment) {
        return argThat(new ArgumentMatcher<SurveyEntry>() {
            @Override
            public boolean matches(Object argument) {
                SurveyEntry surveyEntry = (SurveyEntry) argument;
                return comment.equals(surveyEntry.getComment()) &&
                        rating == surveyEntry.getRating();
            }
        });
    }
}
