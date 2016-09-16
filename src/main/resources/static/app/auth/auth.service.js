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
var core_1 = require('@angular/core');
require('rxjs/add/observable/of');
require('rxjs/add/operator/do');
require('rxjs/add/operator/delay');
var oauth_service_1 = require('angular2-oauth2/oauth-service');
var AuthService = (function () {
    function AuthService(oAuthService) {
        this.oAuthService = oAuthService;
        // Login-Url
        this.oAuthService.loginUrl = 'https://accounts.google.com/o/oauth2/v2/auth';
        // URL of the SPA to redirect the user to after login
        this.oAuthService.redirectUri = window.location.origin + '/';
        // The SPA's id. Register SPA with this id at the auth-server
        this.oAuthService.clientId = '943782333645-mtostmvv8v7vja3dj9nof48dii30st3k.apps.googleusercontent.com';
        // set the scope for the permissions the client should request
        this.oAuthService.scope = 'openid profile email';
        // set to true, to receive also an id_token via OpenId Connect (OIDC) in addition to the
        // OAuth2-based access_token
        this.oAuthService.oidc = true;
        // Use setStorage to use sessionStorage or another implementation of the TS-type Storage
        // instead of localStorage
        this.oAuthService.setStorage(sessionStorage);
        // To also enable single-sign-out set the url for your auth-server's logout-endpoint here
        this.oAuthService.logoutUrl = 'https://accounts.google.com/logout';
        // This method just tries to parse the token within the url when
        // the auth-server redirects the user back to the web-app
        // It dosn't initiate the login
        this.oAuthService.tryLogin({});
        this.isLoggedIn = this.oAuthService.getAccessToken();
    }
    AuthService.prototype.login = function () {
        this.oAuthService.initImplicitFlow();
    };
    AuthService.prototype.logout = function () {
        var _this = this;
        return new Promise(function (resolve) {
            _this.oAuthService.logOut();
            resolve();
        }).then(function () { _this.isLoggedIn = false; });
    };
    AuthService.prototype.getAccessToken = function () {
        return this.oAuthService.getAccessToken();
    };
    AuthService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [oauth_service_1.OAuthService])
    ], AuthService);
    return AuthService;
}());
exports.AuthService = AuthService;
//# sourceMappingURL=auth.service.js.map