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
var task_service_1 = require("./task.service");
var task_component_1 = require("./task.component");
var task_form_component_1 = require("./task-form.component");
var router_1 = require("@angular/router");
/**
 * Renders a list of tasks provided from {@link TaskService}.
 * Uses dependency injection to load the service.
 * @class
 */
var TaskListComponent = (function () {
    function TaskListComponent(route, router, taskService) {
        this.route = route;
        this.router = router;
        this.taskService = taskService;
    }
    /**
     * Implemented method from {@link OnInit} interface which
     * is called after the constructor of the class. We use the
     * provided service to load all tasks.
     */
    TaskListComponent.prototype.ngOnInit = function () {
        this.getProjectTasks();
    };
    /**
     * Gets all tasks for the current project.
     */
    TaskListComponent.prototype.getProjectTasks = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            var id = +params['id']; // (+) converts string 'id' to a number
            _this.projectId = id;
            _this.taskService.getAllTasksByProjectId(id)
                .then(function (tasks) { return _this.tasks = tasks; })
                .catch(function (err) { return console.error(err); });
        });
    };
    TaskListComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'task-list',
            templateUrl: 'task-list.component.html',
            providers: [task_service_1.TaskService],
            directives: [task_component_1.TaskComponent, task_form_component_1.TaskFormComponent]
        }), 
        __metadata('design:paramtypes', [router_1.ActivatedRoute, router_1.Router, task_service_1.TaskService])
    ], TaskListComponent);
    return TaskListComponent;
}());
exports.TaskListComponent = TaskListComponent;
//# sourceMappingURL=task-list.component.js.map