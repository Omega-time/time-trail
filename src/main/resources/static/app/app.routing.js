"use strict";
var router_1 = require('@angular/router');
var project_form_component_1 = require("./project/project-form.component");
var project_list_component_1 = require("./project/project-list.component");
var project_component_1 = require("./project/project.component");
var pagenotfound_component_1 = require("./pagenotfound.component");
var login_component_1 = require("./login/login.component");
var auth_guard_service_1 = require("./auth/auth-guard.service");
/**
 * Defined routes for our Router.
 */
var routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'home', component: login_component_1.LoginComponent, },
    { path: 'projects', component: project_list_component_1.ProjectListComponent, canActivate: [auth_guard_service_1.AuthGuard] },
    { path: 'projects/create', component: project_form_component_1.ProjectFormComponent, canActivate: [auth_guard_service_1.AuthGuard] },
    { path: 'project/:id', component: project_component_1.ProjectComponent, canActivate: [auth_guard_service_1.AuthGuard] },
    { path: '**', component: pagenotfound_component_1.PageNotFoundComponent }
];
/**
 * Exports a service provider for our router
 */
exports.appRouterProviders = [
    router_1.provideRouter(routes)
];
//# sourceMappingURL=app.routing.js.map