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
var oauth_service_1 = require('angular2-oauth2/oauth-service');
var LoginComponent = (function () {
    function LoginComponent(oAuthService) {
        this.oAuthService = oAuthService;
    }
    LoginComponent.prototype.login = function () {
        this.oAuthService.initImplicitFlow();
    };
    LoginComponent.prototype.logoff = function () {
        this.oAuthService.logOut();
    };
    Object.defineProperty(LoginComponent.prototype, "name", {
        get: function () {
            var claims = this.oAuthService.getIdentityClaims();
            if (!claims) {
                return null;
            }
            return claims.given_name;
        },
        enumerable: true,
        configurable: true
    });
    LoginComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            templateUrl: 'login.component.html'
        }), 
        __metadata('design:paramtypes', [oauth_service_1.OAuthService])
    ], LoginComponent);
    return LoginComponent;
}());
exports.LoginComponent = LoginComponent;
//# sourceMappingURL=login.component.js.map