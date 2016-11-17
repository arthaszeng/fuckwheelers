describe("RowSelector", function () {
    describe("clickHandler", function () {
        var target, oldUpdateItemsCheckbox ;
        beforeEach(function () {
            oldUpdateItemsCheckbox = UpdateItemsCheckbox;

            UpdateItemsCheckbox = function () {
                return target;
            }

            target = jasmine.createSpyObj('target', ['toggle', 'toggleAll', 'isRow', 'isToggleAll']);
        });

        afterEach(function () {
            UpdateItemsCheckbox = oldUpdateItemsCheckbox;
        });

        it("toggles a row if target is a row", function () {
	    target.isRow.and.returnValue(true);

            RowSelector.clickHandler({});

            expect(target.toggle).toHaveBeenCalled();
        });

        it("toggles all the rows if target is toggleAll", function () {
	    target.isToggleAll.and.returnValue(true);

            RowSelector.clickHandler({});

            expect(target.toggleAll).toHaveBeenCalled();
        });
    });
});
