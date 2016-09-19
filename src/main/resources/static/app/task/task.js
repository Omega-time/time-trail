"use strict";
/**
 * Represents a Task model class
 * @class
 */
var Task = (function () {
    function Task(id, name, duration, comment) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.comment = comment;
    }
    /**
     * Gets anonymous object's duration hours and duration minutes,
     * and sets the totalDuration for object of type Task.
     * @param obj the anonymous task object
     * @return {number} total duration
     */
    Task.prototype.setTotalDuration = function (obj) {
        this.duration = (Number(obj.hours) * 60) + Number(obj.minutes);
    };
    /**
     * Parses anonymous object to an instance of Task class.
     * Called when parsing JSON string from http request in order
     * to map JS anonymous object to Task.
     * @param obj the anonymous task
     * @returns {Task} the parsed object
     */
    Task.parseInputObjectToTask = function (obj) {
        return new Task(obj.id, obj.name, obj.duration, obj.comment);
    };
    /**
     * Creates an empty Task class instance.
     * Used for two-way-binding in forms.
     * @returns {Task} the empty Task
     */
    Task.createEmptyTask = function () {
        return new Task(null, null, null, null);
    };
    return Task;
}());
exports.Task = Task;
//# sourceMappingURL=task.js.map