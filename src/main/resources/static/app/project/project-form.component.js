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
var project_1 = require("./project");
var project_service_1 = require("./project.service");
var router_1 = require("@angular/router");
/**
 * Represents a form which sends a new project to
 * the {@link ProjectService} for storage.
 * Uses dependency injection for the service providers.
 * @class
 */
var ProjectFormComponent = (function () {
    function ProjectFormComponent(projectService, router) {
        this.projectService = projectService;
        this.router = router;
        this.active = true;
        this.newProjectAdded = new core_1.EventEmitter();
    }
    /**
     * Implemented method from {@link OnInit} interface which
     * is called after the constructor of the class. Here
     * we instantiate the projectToBeCreated with an empty
     * Project object.
     */
    ProjectFormComponent.prototype.ngOnInit = function () {
        this.projectToBeCreated = project_1.Project.createEmptyProject();
    };
    /**
     * EventHandler method which is called when the Add new project
     * button is clicked. The method calls the service to store
     * the projectToBeCreated object.
     */
    ProjectFormComponent.prototype.addProject = function () {
        var _this = this;
        this.projectService.saveProject(this.projectToBeCreated)
            .then(function (newProjectId) {
            _this.router.navigateByUrl("/projects");
            _this.newProjectAdded.emit(true);
            _this.formReset();
        });
    };
    /**
     * Resets the Create project form by giving projectToBeCreated an empty Project object.
     */
    ProjectFormComponent.prototype.formReset = function () {
        var _this = this;
        this.projectToBeCreated = project_1.Project.createEmptyProject();
        this.active = false;
        setTimeout(function () { return _this.active = true; }, 0);
    };
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], ProjectFormComponent.prototype, "newProjectAdded", void 0);
    ProjectFormComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'project-form',
            templateUrl: 'project-form.component.html',
            providers: [project_service_1.ProjectService]
        }), 
        __metadata('design:paramtypes', [project_service_1.ProjectService, router_1.Router])
    ], ProjectFormComponent);
    return ProjectFormComponent;
}());
exports.ProjectFormComponent = ProjectFormComponent;
//# sourceMappingURL=project-form.component.js.map