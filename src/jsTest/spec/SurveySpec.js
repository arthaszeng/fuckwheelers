describe("survey pop up", function () {

    survey = new Survey();
    surveyPopUp = new SurveyPopUp();

    it("should pop up if cookie does not exist", function () {
	$.cookie = jasmine.createSpy('cookie').and.returnValue(false);
        spyOn(surveyPopUp, 'show');

        survey.showSurvey(surveyPopUp);
        expect(surveyPopUp.show).toHaveBeenCalled();
    });

    it("should not pop up if cookie exists", function () {
	$.cookie = jasmine.createSpy('cookie').and.returnValue(true);
        spyOn(surveyPopUp, 'show');

        survey.showSurvey(surveyPopUp);
        expect(surveyPopUp.show).not.toHaveBeenCalled();
    });
});
