"use strict";
var platform_browser_dynamic_1 = require('@angular/platform-browser-dynamic');
var core_1 = require('@angular/core');
var _1 = require('./app/');
var http_1 = require('@angular/http');
var app_routing_1 = require('./app/app.routing');
var forms_1 = require('@angular/forms');
if (_1.environment.production) {
    core_1.enableProdMode();
}
/**
 * Runs the angular 2 app by supplying
 * with the needed service providers
 * and declaring the start component
 */
platform_browser_dynamic_1.bootstrap(_1.AppComponent, [
    http_1.HTTP_PROVIDERS,
    app_routing_1.appRouterProviders,
    forms_1.disableDeprecatedForms(),
    forms_1.provideForms()
]).catch(function (error) { return console.error(error); });
//# sourceMappingURL=main.js.map