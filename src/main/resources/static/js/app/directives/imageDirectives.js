(function() {
    'use strict';
    app.directive("fileread", [function () {
        return {
            scope: {
                fileread: "="
            },
            link: function (scope, element, attributes) {
                element.bind("change", function (changeEvent) {
                    var reader = new FileReader();
                    reader.onload = function (loadEvent) {
                        scope.$apply(function () {
                            scope.fileread = loadEvent.target.result;
                            $('#image')
                            .attr('src', loadEvent.target.result)
                            .width(250)
                            .height(250);
                        });
                    }
                    reader.readAsDataURL(changeEvent.target.files[0]);
                });
            }
        }
    }]);
})();

(function() {
    'use strict';
    app.directive("filechange", [function () {
        return {
            scope: {
                filechange: "="
            },
            link: function (scope, element, attributes) {
                element.bind("change", function (changeEvent) {
                    var reader = new FileReader();
                    reader.onload = function (loadEvent) {
                        scope.$apply(function () {
                            scope.fileread = loadEvent.target.result;
                            //attributes.$set("src", 'image_fetched_from_server.png');
                            $('#imageChange')
                            .attr('src', loadEvent.target.result)
                            .width(250)
                            .height(250);
                        });
                    }
                    reader.readAsDataURL(changeEvent.target.files[0]);
                });
            }
        }
    }]);
})();