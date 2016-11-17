function serializeObject(formData) {
    var data = {};
    $(formData).serializeArray().forEach(function (e) { data[e.name] = e.value; });
    return data;
}
