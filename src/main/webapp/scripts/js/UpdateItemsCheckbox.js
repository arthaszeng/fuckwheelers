function UpdateItemsCheckbox(checkbox) {

    this.isRow = function () {
        return checkbox.hasClass('rowSelector');
    };

    this.isToggleAll = function() {
        return checkbox.hasClass('toggleAll');
    };

    this.isChecked = function () {
        return checkbox.is(":checked");
    };

    this.rowInputFields = function () {
        var inputFields = checkbox.parents("tr").find(":input:not(.rowSelector)");

        inputFields.enable = function () { this.removeAttr("disabled"); };
        inputFields.disable = function () { this.attr("disabled", "disabled"); };

        return inputFields;
    };

    this.toggle = function () {
        this.isChecked() ? this.rowInputFields().enable() : this.rowInputFields().disable();
    };

    this.toggleAll = function () {
        $(":checkbox:not(.toggleAll)").click();
    };

}
