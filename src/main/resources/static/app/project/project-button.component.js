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
var project_1 = require("../project/project");
var project_component_1 = require("../project/project.component");
var router_1 = require("@angular/router");
/**
 * Represents a component which renders a single Project.
 * The project to be render is passed by a property binding
 * from the parent component {@link ProjectListComponent} to
 * the Input field project.
 * @class
 */
var ProjectButtonComponent = (function () {
    function ProjectButtonComponent(router) {
        this.router = router;
    }
    ProjectButtonComponent.prototype.ngOnInit = function () {
    };
    ProjectButtonComponent.prototype.goToProject = function () {
        this.router.navigate(['/project', this.project.id]);
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', project_1.Project)
    ], ProjectButtonComponent.prototype, "project", void 0);
    ProjectButtonComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'project-button',
            templateUrl: 'project-button.component.html',
            directives: [project_component_1.ProjectComponent]
        }), 
        __metadata('design:paramtypes', [router_1.Router])
    ], ProjectButtonComponent);
    return ProjectButtonComponent;
}());
exports.ProjectButtonComponent = ProjectButtonComponent;
//# sourceMappingURL=project-button.component.js.map