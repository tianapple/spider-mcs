(function () {
    window.upotv = window.upotv ? window.upotv : {};
    upotv.biz = upotv.biz ? upotv.biz : {};
    upotv.biz.Dict = {};

    upotv.biz.Dict.getDict = function (typeName) {
        var dict = {};
        $.ajax({
            type: "POST",
            async: false,
            url: "dict/getDict",
            data: {userId: typeName},
            success: function (result) {
                dict = result;
            }
        });
        return dict;
    };

    upotv.biz.Dict.getValue = function (code, dictStore) {
        for (var k = 0; k < dictStore.length; k++) {
            if(dictStore[k]["codeId"] == code){

                return dictStore[k]["codeName"];
            }
        }
        return null;
    };
})();