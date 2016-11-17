var RowSelector = (function () {

    function clickHandler(event) {
        var target = new UpdateItemsCheckbox($(event.target));

        if (target.isRow()) {
            target.toggle();
        } else if (target.isToggleAll()) {
            target.toggleAll();
        }
    }

    return {
        clickHandler: clickHandler
    };

}());


$(function () {
    $("input[type=checkbox]").click(RowSelector.clickHandler);
});
