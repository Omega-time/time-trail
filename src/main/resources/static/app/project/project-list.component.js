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
var project_service_1 = require("./project.service");
var project_button_component_1 = require("./project-button.component");
var project_form_component_1 = require("./project-form.component");
/**
 * Renders a list of projects provided from {@link ProjectService}.
 * Uses dependency injection to load the service.
 * @class
 */
var ProjectListComponent = (function () {
    function ProjectListComponent(projectService) {
        this.projectService = projectService;
        this.collapsed = true;
    }
    /**
     * Implemented method from {@link OnInit} interface which
     * is called after the constructor of the class. We use the
     * provided service to load all projects.
     */
    ProjectListComponent.prototype.ngOnInit = function () {
        this.getAllProjects();
    };
    /**
     * Gets all projects for the current user.
     */
    ProjectListComponent.prototype.getAllProjects = function () {
        var _this = this;
        this.projectService.getAllProjects()
            .then(function (projects) { return _this.projects = projects.reverse(); })
            .catch(function (err) { return console.error(err); });
    };
    ProjectListComponent.prototype.collapse = function () {
        this.collapsed = !this.collapsed;
    };
    ProjectListComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'project-list',
            templateUrl: 'project-list.component.html',
            directives: [project_button_component_1.ProjectButtonComponent, project_form_component_1.ProjectFormComponent],
            providers: [project_service_1.ProjectService]
        }), 
        __metadata('design:paramtypes', [project_service_1.ProjectService])
    ], ProjectListComponent);
    return ProjectListComponent;
}());
exports.ProjectListComponent = ProjectListComponent;
//# sourceMappingURL=project-list.component.js.map