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
var http_1 = require("@angular/http");
var auth_service_1 = require("../auth/auth.service");
var task_1 = require("./task");
require('rxjs/Rx');
/**
 * Class which handles all HTTP requests to the
 * business layer. It stores and retrieves {@link Task}
 * objects. Uses map method to parse JSON strings to Task
 * instances.
 * @class
 */
var TaskService = (function () {
    function TaskService(http, authService) {
        this.http = http;
        this.authService = authService;
        this.tasksServiceUrl = 'http://localhost:8080/api/tasks';
        this.taskServiceUrl = 'http://localhost:8080/api/task';
    }
    TaskService.prototype.createAuthorizationHeader = function (headers) {
        var authHeaders = headers || new http_1.Headers();
        authHeaders.append('Authorization', 'Bearer ' + this.authService.getAccessToken());
        return authHeaders;
    };
    /**
     * Retrieves all tasks for a given project from the business layer.
     * @returns {Task<Task[]>} a promise which holds an array of Tasks objects
     */
    TaskService.prototype.getAllTasksByProjectId = function (projectId) {
        return this.http.get(this.tasksServiceUrl + ("/" + projectId), {
            headers: this.createAuthorizationHeader()
        })
            .map(function (response) { return response.json(); })
            .map(function (tasks) { return tasks.map(function (task) { return task_1.Task.parseInputObjectToTask(task); }); })
            .toPromise();
    };
    /**
     * Retrieves a single task object by a given id.
     * @param id the id by which it searches
     * @returns {Promise<Task>} a promise which holds a single task object
     */
    TaskService.prototype.getTaskById = function (id) {
        return this.http.get(this.taskServiceUrl + ("/" + id), {
            headers: this.createAuthorizationHeader()
        })
            .map(function (response) { return response.json(); })
            .map(function (task) { return task_1.Task.parseInputObjectToTask(task); })
            .toPromise();
    };
    /**
     * Deletes a single task object by a given id.
     * @param id the id by which it deletes the task
     * @returns {Promise<Object>} a promise which holds an anonymous object
     *                            which holds a response.
     */
    TaskService.prototype.deleteTaskById = function (id) {
        return this.http.delete(this.taskServiceUrl + ("/" + id), {
            headers: this.createAuthorizationHeader()
        })
            .map(function (response) { return response.json(); })
            .toPromise();
    };
    /**
     * Stores a task in the business layer.
     * @param task the task to be stored
     * @returns {Promise<Object>} a promise which holds an anonymous object
     *                            which holds the new task id
     */
    TaskService.prototype.saveTask = function (task, id) {
        return this.http.post(this.tasksServiceUrl + ("/" + id), task, {
            headers: this.createAuthorizationHeader()
        })
            .map(function (response) { return response.json(); })
            .toPromise();
    };
    TaskService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http, auth_service_1.AuthService])
    ], TaskService);
    return TaskService;
}());
exports.TaskService = TaskService;
//# sourceMappingURL=task.service.js.map