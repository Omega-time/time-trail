"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require("@angular/core");
var ng2_file_upload_1 = require('ng2-file-upload');
var http_1 = require("@angular/http");
var auth_service_1 = require("../auth/auth.service");
var MAX_FILE_SIZE = 3 * 1024 * 1024;
var DOCX_FILE_MIME_TYPE = 'application/vnd.openxmlformats-officedocument.wordprocessingml.document';
var FileUploadFormComponent = (function () {
    function FileUploadFormComponent(authService) {
        this.authService = authService;
        this.fileUploadURL = 'http://localhost:8080/api/project';
        this.newFileAdded = new core_1.EventEmitter();
    }
    FileUploadFormComponent.prototype.ngOnInit = function () {
        this.initFileUploader();
    };
    FileUploadFormComponent.prototype.initFileUploader = function () {
        var _this = this;
        this.fileUploadURL = this.fileUploadURL + '/' + this.projectId + '/files';
        this.uploader = new ng2_file_upload_1.FileUploader({ url: this.fileUploadURL, authToken: this.createAuthorizationHeader() });
        this.uploader.onBeforeUploadItem = function (fileItem) {
            fileItem.method = 'POST';
        };
        this.uploader.onWhenAddingFileFailed = function (item, filter, options) {
            _this.isFileSizeTooLarge = !_this.uploader._fileSizeFilter(item);
            // this.isFileTypeInvalid = !this.uploader._mimeTypeFilter(item);
        };
        this.uploader.onAfterAddingFile = function (fileItem) {
            _this.fileItem = fileItem;
            console.log(_this.projectId);
        };
        this.uploader.onSuccessItem = function (fileItem) {
            _this.fileItem = null;
            _this.newFileAdded.emit(true);
        };
    };
    FileUploadFormComponent.prototype.createAuthorizationHeader = function (headers) {
        var authHeaders = headers || new http_1.Headers();
        authHeaders.append('Authorization', 'Bearer ' + this.authService.getAccessToken());
        return authHeaders;
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Number)
    ], FileUploadFormComponent.prototype, "projectId", void 0);
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], FileUploadFormComponent.prototype, "newFileAdded", void 0);
    FileUploadFormComponent = __decorate([
        core_1.Component({
            selector: 'file-upload-form',
            templateUrl: 'app/file/file-upload-form.component.html',
            directives: [ng2_file_upload_1.FILE_UPLOAD_DIRECTIVES]
        }), 
        __metadata('design:paramtypes', [auth_service_1.AuthService])
    ], FileUploadFormComponent);
    return FileUploadFormComponent;
}());
exports.FileUploadFormComponent = FileUploadFormComponent;
//# sourceMappingURL=file-upload-form.component.js.map