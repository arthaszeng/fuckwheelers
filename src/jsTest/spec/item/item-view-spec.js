describe("ItemView", function () {

    function setUpHTMLFixture() {
        jasmine.getFixtures().set(
            '<form id="add_item">' +
            '<input id="name" type="text" value="Name"/>' +
            '<input id="price" type="text" value="10.0"/>' +
            '<input id="type" type="text" value="Type"/>' +
            '<input id="description" type="text" value="Desc">' +
            '<input id="quantity" type="text" value="3">' +
            '<div id="err1"></div>' +
            '<div id="err2" style="display: none;"></div>' +
            '</form>'
        );
    }

    var itemView;

    beforeEach(function () {
        setUpHTMLFixture();

        var itemForm = $("#add_item");
        itemView = new ItemView(itemForm);
    });

    it("model should validate when created from view", function () {
        var model = itemView.getModel();

        expect(model.validate()).toBeTruthy();
    });

    it("should hide error", function () {
        itemView.hideErrors(['err1']);

        expect($('#err1').is(":hidden")).toBeTruthy();
    });

    it("should show error", function () {
        itemView.showErrors(['err2']);

        expect($('#err2').is(":visible")).toBeTruthy();
    });

    it("should hide errors before showing newer ones", function () {
        spyOn(itemView, "getModel").and.returnValue({
            validate: function () {
                return ["err1", "err2"];
            }
        });
        spyOn(itemView, "hideErrors");
        spyOn(itemView, "showErrors");

        expect(itemView.validate()).toBe(false);
        expect(itemView.showErrors.calls.argsFor(0)).toEqual([["err1", "err2"]]);
    });
});