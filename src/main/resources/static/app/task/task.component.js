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
var duration_pipe_1 = require("./duration.pipe");
/**
 * Represents a component which renders a single Task.
 * The task to be render is passed by a property binding
 * from the parent component {@link TaskListComponent} to
 * the Input field project.
 * @class
 */
var TaskComponent = (function () {
    function TaskComponent(taskService, myElement) {
        this.taskService = taskService;
        this.myElement = myElement;
        this.taskDeleted = new core_1.EventEmitter();
        this.elementRef = myElement;
    }
    /**
     * Initiates the deletion of a tasks. Displays the confirmation dialog for deletion.
     */
    TaskComponent.prototype.onClickDelete = function (event) {
        this.confirmDelete = true;
    };
    /**
     * Deletes the task after user confirmation.
     */
    TaskComponent.prototype.deleteTask = function (taskId) {
        var _this = this;
        this.taskService
            .deleteTaskById(taskId)
            .then(function (resp) {
            _this.taskDeleted.emit(true);
        })
            .catch(function (error) {
            console.error(error);
            _this.taskDeleted.emit(true);
        });
    };
    /**
     * Listens for click events in and outside of this component.
     * We use it to hide the deletion dialog of current task.
     */
    TaskComponent.prototype.handleClick = function (event) {
        var clickedComponent = event.target;
        var inside = false;
        do {
            if (clickedComponent === this.elementRef.nativeElement) {
                inside = true;
            }
            clickedComponent = clickedComponent.parentNode;
        } while (clickedComponent);
        if (!inside) {
            this.confirmDelete = false;
        }
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', task_1.Task)
    ], TaskComponent.prototype, "task", void 0);
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], TaskComponent.prototype, "taskDeleted", void 0);
    TaskComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'task',
            templateUrl: 'task.component.html',
            pipes: [duration_pipe_1.DurationPipe],
            host: {
                '(document:click)': 'handleClick($event)'
            }
        }), 
        __metadata('design:paramtypes', [task_service_1.TaskService, core_1.ElementRef])
    ], TaskComponent);
    return TaskComponent;
}());
exports.TaskComponent = TaskComponent;
//# sourceMappingURL=task.component.js.map