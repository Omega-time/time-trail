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
var task_1 = require("./task");
var task_service_1 = require("./task.service");
/**
 * Represents a form which sends a new task to
 * the {@link TaskService} for storage.
 * Uses dependency injection for the service providers.
 * @class
 */
var TaskFormComponent = (function () {
    function TaskFormComponent(taskService) {
        this.taskService = taskService;
        this.duration = {
            hours: 0,
            minutes: 0
        };
        this.newTaskAdded = new core_1.EventEmitter();
        this.active = true;
    }
    /**
     * Implemented method from {@link OnInit} interface which
     * is called after the constructor of the class. Here
     * we instantiate the taskToBeCreated with an empty
     * Task object through the {@link formReset}.
     */
    TaskFormComponent.prototype.ngOnInit = function () {
        this.formReset();
    };
    /**
     * EventHandler method which is called when the Add new task
     * button is clicked. The method calls the service to store
     * the taskToBeCreated object.
     */
    TaskFormComponent.prototype.addTask = function () {
        var _this = this;
        this.taskToBeCreated.setTotalDuration(this.duration);
        this.taskService.saveTask(this.taskToBeCreated, this.projectId)
            .then(function (resp) {
            _this.newTaskAdded.emit(true);
            _this.formReset();
        })
            .catch(function (err) { return console.log(err); });
    };
    /**
     * Resets the Add Task form by giving taskToBeCreated an empty Task object.
     */
    TaskFormComponent.prototype.formReset = function () {
        var _this = this;
        this.taskToBeCreated = task_1.Task.createEmptyTask();
        this.active = false;
        setTimeout(function () { return _this.active = true; }, 0);
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Number)
    ], TaskFormComponent.prototype, "projectId", void 0);
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], TaskFormComponent.prototype, "newTaskAdded", void 0);
    TaskFormComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'task-form',
            templateUrl: 'task-form.component.html',
            providers: [task_service_1.TaskService],
        }), 
        __metadata('design:paramtypes', [task_service_1.TaskService])
    ], TaskFormComponent);
    return TaskFormComponent;
}());
exports.TaskFormComponent = TaskFormComponent;
//# sourceMappingURL=task-form.component.js.map