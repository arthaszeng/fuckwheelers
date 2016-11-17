describe("ItemModel", function () {
    var validItemModelParameters;
    var extendWith = $.extend;

    beforeEach(function () {
        validItemModelParameters = {
            price: "1",
            name: "name",
            type: "type",
            description: "description",
            quantity: "1"
        };
    });


    it("validates when price is a number and name, type, description and quantity are present", function () {
        var itemModel = new ItemModel(validItemModelParameters);
        expect(itemModel.validate()).toEqual([]);
    });

    it("fails to validate when name field is missing", function () {
        var itemArgsWithMissingNameAndType = extendWith(validItemModelParameters, {
            name: ""
        });

        var itemModelWithoutNameAndType = new ItemModel(itemArgsWithMissingNameAndType);
        expect(itemModelWithoutNameAndType.validate()).toEqual([ItemErrorEnum.nameEmpty]);
    });

    it("fails to validate when price is not a number", function () {

        var itemArgsWithNonNumberPrice = extendWith(validItemModelParameters, {
            price : "aaa"
        });
        var itemModel = new ItemModel(itemArgsWithNonNumberPrice);

        expect(itemModel.validate()).toEqual([ItemErrorEnum.priceEmpty]);
    });

    it("fails to validate when price is empty", function() {
        var itemArgsWithEmptyPrice = extendWith(validItemModelParameters, {
            price : ""
        });

        var itemModel = new ItemModel(itemArgsWithEmptyPrice);
        expect(itemModel.validate()).toEqual([ItemErrorEnum.priceEmpty]);
    });

    it("fails to validate when price is greater than threshold", function() {
        var itemArgsWithPriceGreaterThanThreshold = extendWith(validItemModelParameters, {
            price : "100001"
        });

        var itemModel = new ItemModel(itemArgsWithPriceGreaterThanThreshold);
        expect(itemModel.validate()).toEqual([ItemErrorEnum.priceOutOfBounds]);
    });

    it("fails to validate when quantity is not a integer", function () {
        var itemArgsWithInvalidQuantity = extendWith(validItemModelParameters, {
            quantity: "blah"
        });

        var itemModel = new ItemModel(itemArgsWithInvalidQuantity);
        expect(itemModel.validate()).toEqual([ItemErrorEnum.quantityEmpty]);
    });

    it("fails to validate when multiple fields are missing", function () {
        var itemArgsWithMissingNameAndType = extendWith(validItemModelParameters, {
            name: "",
            price: ""
        });

        var itemModelWithoutNameAndType = new ItemModel(itemArgsWithMissingNameAndType);
        expect(itemModelWithoutNameAndType.validate()).toEqual([ItemErrorEnum.nameEmpty, ItemErrorEnum.priceEmpty]);
    });

});

