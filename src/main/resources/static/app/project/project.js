"use strict";
/**
 * Represents a Project model class
 * @class
 */
var Project = (function () {
    function Project(id, name) {
        this.id = id;
        this.name = name;
    }
    /**
     * Parses anonymous object to an instance of Project class.
     * Called when parsing JSON string from http request in order
     * to map JS anonymous object to Project.
     * @param obj the anonymous project
     * @returns {Project} the parsed object
     */
    Project.parseInputObjectToProject = function (obj) {
        console.log(obj.name);
        return new Project(obj.id, obj.name);
    };
    /**
     * Creates an empty Project class instance.
     * Used for two-way-binding in forms.
     * @returns {Project} the empty Project
     */
    Project.createEmptyProject = function () {
        return new Project(null, null);
    };
    return Project;
}());
exports.Project = Project;
//# sourceMappingURL=project.js.map