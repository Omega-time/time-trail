"use strict";
/**
 * Represents a File model class
 * @class
 */
var DocFile = (function () {
    function DocFile(name, size, type) {
        this.name = name;
        this.size = size;
        this.type = type;
    }
    /**
     * Parses anonymous object to an instance of File class.
     * Called when parsing JSON string from http request in order
     * to map JS anonymous object to File.
     * @param obj the anonymous project
     * @returns {File} the parsed object
     */
    DocFile.parseInputObjectToDocFile = function (obj) {
        return new DocFile(obj.name, obj.size, obj.type);
    };
    return DocFile;
}());
exports.DocFile = DocFile;
//# sourceMappingURL=docfile.js.map