var ItemErrorEnum = {
    nameEmpty: "name_empty",
    priceEmpty: "price_empty",
    priceOutOfBounds: "price_out_of_bounds",
    typeEmpty: "type_empty",
    descriptionEmpty: "description_empty",
    quantityEmpty: "quantity_empty"
};

var AllItemErrors = Object.keys(ItemErrorEnum).map(function (k) {
    return ItemErrorEnum[k];
});

function ItemModel(parameters) {
    var args = parameters || {};
    var mandatoryFieldNames = ["name", "type", "description", "quantity"];

    var priceShouldBeANumber = function () {
        var FLOAT_PATTERN = /^[0-9.]+$/;
        return args.price.match(FLOAT_PATTERN);
    };

    var priceShouldBeLessThanThreshold = function () {
        return parseInt(args.price) <= 10000;
    };

    var priceShouldBeValid = function () {

        if (!priceShouldBeANumber()) {
            return [ItemErrorEnum.priceEmpty];
        }

        if (!priceShouldBeLessThanThreshold()) {
            return [ItemErrorEnum.priceOutOfBounds];
        }

        return [];
    };

    var mandatoryFieldsShouldBePresent = function () {

        var fieldEmptyValidation = function (fieldName) {
            return args[fieldName] ? [] : [fieldName + "_empty"];
        };

        return mandatoryFieldNames.reduce(function (currentErrors, currentFieldName) {
            return currentErrors.concat(fieldEmptyValidation(currentFieldName));
        }, []);
    };

    var quantityShouldBeInteger = function () {
        var INTEGER_PATTERN = /^[0-9]+$/;

        return args.quantity.match(INTEGER_PATTERN) ? [] : [ItemErrorEnum.quantityEmpty];
    };

    this.validate = function () {
        return mandatoryFieldsShouldBePresent()
            .concat(priceShouldBeValid())
            .concat(quantityShouldBeInteger());
    };
}