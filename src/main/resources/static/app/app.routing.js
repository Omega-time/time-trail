"use strict";
var router_1 = require('@angular/router');
var project_form_component_1 = require("./project/project-form.component");
var project_list_component_1 = require("./project/project-list.component");
var pagenotfound_component_1 = require("./pagenotfound.component");
/**
 * Defined routes for our Router.
 */
var routes = [
    { path: '', redirectTo: '/projects', pathMatch: 'full' },
    { path: 'projects', component: project_list_component_1.ProjectListComponent },
    { path: 'projects/create', component: project_form_component_1.ProjectFormComponent },
    { path: '**', component: pagenotfound_component_1.PageNotFoundComponent }
];
/**
 * Exports a service provider for our router
 */
exports.appRouterProviders = [
    router_1.provideRouter(routes)
];
//# sourceMappingURL=app.routing.js.map