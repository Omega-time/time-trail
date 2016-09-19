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
var http_1 = require("@angular/http");
var auth_service_1 = require("../auth/auth.service");
var docfile_1 = require("./docfile");
require('rxjs/Rx');
var FileService = (function () {
    function FileService(http, authService) {
        this.http = http;
        this.authService = authService;
        this.serviceUrl = 'http://localhost:8080/api/project';
    }
    FileService.prototype.getAllFileNamesByProjectId = function (projectId) {
        var getAllFilesUrl = this.serviceUrl + ("/" + projectId) + '/files';
        return this.http.get(getAllFilesUrl, {
            headers: this.createAuthorizationHeader()
        })
            .map(function (response) { return response.json(); })
            .map(function (files) { return files.map(function (docFile) { return docfile_1.DocFile.parseInputObjectToDocFile(docFile); }); })
            .toPromise();
    };
    FileService.prototype.deleteFileByNameAndProjectId = function (fileName, projectId) {
        var deleteFileUrl = this.serviceUrl + ("/" + projectId) + ("/" + fileName);
        return this.http.delete(deleteFileUrl, {
            headers: this.createAuthorizationHeader()
        })
            .map(function (response) { return response.json(); })
            .toPromise();
    };
    FileService.prototype.createAuthorizationHeader = function (headers) {
        var authHeaders = headers || new http_1.Headers();
        authHeaders.append('Authorization', 'Bearer ' + this.authService.getAccessToken());
        return authHeaders;
    };
    FileService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http, auth_service_1.AuthService])
    ], FileService);
    return FileService;
}());
exports.FileService = FileService;
//# sourceMappingURL=file.service.js.map