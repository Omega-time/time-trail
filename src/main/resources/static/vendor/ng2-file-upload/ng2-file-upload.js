"use strict";
function __export(m) {
    for (var p in m) if (!exports.hasOwnProperty(p)) exports[p] = m[p];
}
__export(require('./components/file-upload/file-select.directive'));
__export(require('./components/file-upload/file-drop.directive'));
__export(require('./components/file-upload/file-uploader.class'));
var file_select_directive_2 = require('./components/file-upload/file-select.directive');
var file_drop_directive_2 = require('./components/file-upload/file-drop.directive');
exports.FILE_UPLOAD_DIRECTIVES = [file_select_directive_2.FileSelectDirective, file_drop_directive_2.FileDropDirective];
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = {
    directives: [
        exports.FILE_UPLOAD_DIRECTIVES
    ]
};
