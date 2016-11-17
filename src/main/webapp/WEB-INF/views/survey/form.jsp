<%@include file="header.jsp" %>

<form:form id="survey" action="/payment/survey" method="post" modelAttribute="survey">

    <div class="survey-form">

        <div id="surveyRating">
            <form:label
                    path="rating">1. How likely is it that you would recommend Freewheelers to a friend or colleague?
                <span class="mandatory">*</span>

                <div><form:errors path="rating" class="text-error"/></div>
            </form:label>
            <table>
                <tr>
                    <td></td>
                    <td>0 - Not at all likely</td>
                    <td>1</td>
                    <td>2</td>
                    <td>3</td>
                    <td>4</td>
                    <td>5 - Neutral</td>
                    <td>6</td>
                    <td>7</td>
                    <td>8</td>
                    <td>9</td>
                    <td>10 - Extremely likely</td>
                </tr>
                <tr>
                    <td></td>
                    <td><form:radiobutton id="rating_0" name="rating" value="0" path="rating"/></td>
                    <td><form:radiobutton id="rating_1" name="rating" value="1" path="rating"/></td>
                    <td><form:radiobutton id="rating_2" name="rating" value="2" path="rating"/></td>
                    <td><form:radiobutton id="rating_3" name="rating" value="3" path="rating"/></td>
                    <td><form:radiobutton id="rating_4" name="rating" value="4" path="rating"/></td>
                    <td><form:radiobutton id="rating_5" name="rating" value="5" path="rating"/></td>
                    <td><form:radiobutton id="rating_6" name="rating" value="6" path="rating"/></td>
                    <td><form:radiobutton id="rating_7" name="rating" value="7" path="rating"/></td>
                    <td><form:radiobutton id="rating_8" name="rating" value="8" path="rating"/></td>
                    <td><form:radiobutton id="rating_9" name="rating" value="9" path="rating"/></td>
                    <td><form:radiobutton id="rating_10" name="rating" value="10" path="rating"/></td>
                </tr>
            </table>
        </div>

        <div>
            <form:label path="comment">2. How can we improve our offering?</form:label>
            <form:textarea id="surveyComment" name="comment" path="comment" placeholder="I would like to say..."/>
        </div>

    </div>

    <form:button class="button" id="surveySubmitButton" type="submit" value="Submit">Submit</form:button>
    <button type="button" class="button" id="closeButton">Close</button>

</form:form>

<%@ include file="../footer.jsp" %>