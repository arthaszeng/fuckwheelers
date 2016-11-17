function ItemView(itemDOMForm){
    var itemDOMForm = $(itemDOMForm);

    this.getModel = function() {
        var itemModelParameters = {
            price : itemDOMForm.find("#price").val(),
            name : itemDOMForm.find("#name").val(),
            type : itemDOMForm.find("#type").val(),
            description : itemDOMForm.find("#description").val(),
            quantity : itemDOMForm.find("#quantity").val()
        };

        return new ItemModel(itemModelParameters);
    };

    this.hideErrors = function(errorNames) {
        errorNames.forEach(function(errorName) {
            $("#" + errorName).hide();
        });
    };

    this.showErrors = function(errorNames) {
        this.hideErrors(AllItemErrors);

        errorNames.forEach(function(errorName) {
            $("#" + errorName).show();
        });
    };

    var self = this;
    this.validate = function() {
        var errors = self.getModel().validate();
        self.showErrors(errors);

        return errors.length === 0;
    };

    itemDOMForm.submit(function(event) {
        if (!self.validate()) {
            event.preventDefault();
        }
    });
}

