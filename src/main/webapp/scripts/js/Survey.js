function Survey() {
    this.showSurvey = function (popUpWindow) {
        if (!$.cookie("SurveyTaken")) {
            popUpWindow.show();
        }
    }
}

function SurveyPopUp() {
    this.show = function () {
        setTimeout(function () {
            window.open('survey', 'Survey', 'left=20,top=20,width=750,height=370');
        }, 2000);
    }
}

