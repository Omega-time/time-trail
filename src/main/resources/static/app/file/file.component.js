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
var file_service_1 = require("./file.service");
var docfile_1 = require("./docfile");
var FileComponent = (function () {
    function FileComponent(fileService, myElement) {
        this.fileService = fileService;
        this.myElement = myElement;
        this.fileDeleted = new core_1.EventEmitter();
        this.confirmDelete = false;
        this.elementRef = myElement;
    }
    FileComponent.prototype.ngOnInit = function () {
        this.fileUrl = "http://localhost:8080/api/project/" + this.projectId + "/" + this.docFile.name;
    };
    FileComponent.prototype.onClickDelete = function (event) {
        var _this = this;
        this.fileService
            .deleteFileByNameAndProjectId(this.docFile.name, this.projectId)
            .then(function (resp) { return (_this.fileDeleted.emit(true)); })
            .catch(function (error) { return console.log(error); });
        this.confirmDelete = false;
    };
    FileComponent.prototype.confirmDeletion = function (event) {
        this.confirmDelete = true;
    };
    FileComponent.prototype.handleClick = function (event) {
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
        __metadata('design:type', docfile_1.DocFile)
    ], FileComponent.prototype, "docFile", void 0);
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Number)
    ], FileComponent.prototype, "projectId", void 0);
    __decorate([
        core_1.Output(), 
        __metadata('design:type', Object)
    ], FileComponent.prototype, "fileDeleted", void 0);
    FileComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'tr.special',
            templateUrl: 'file.component.html',
            host: {
                '(document:click)': 'handleClick($event)'
            }
        }), 
        __metadata('design:paramtypes', [file_service_1.FileService, core_1.ElementRef])
    ], FileComponent);
    return FileComponent;
}());
exports.FileComponent = FileComponent;
//# sourceMappingURL=file.component.js.map