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
var core_1 = require('@angular/core');
/**
 * DurationPipe will be used to transform duration of Task object.
 * This pipe will help us to display the duration of Task in
 * format H:M. (hours and minutes)
 */
var DurationPipe = (function () {
    function DurationPipe() {
    }
    /**
     * This method will transform duration of Task object without modifying it.
     * To display duration in the hours field in tasks lits, we need to set the
     * parameter isHoursField to true, and respectively to false if we want to
     * display the minutes of the task in the (@see {@link task.component.html})
     *
     * @param {number} total duration of the Task object in minutes
     * @param {boolean} defines if the field which will display the
     *                  duration is hours or minutes.
     */
    DurationPipe.prototype.transform = function (totalDuration, isHoursField) {
        if (isHoursField) {
            return Math.floor(totalDuration / 60);
        }
        return totalDuration % 60;
    };
    DurationPipe = __decorate([
        core_1.Pipe({
            name: 'durationPipe'
        }), 
        __metadata('design:paramtypes', [])
    ], DurationPipe);
    return DurationPipe;
}());
exports.DurationPipe = DurationPipe;
//# sourceMappingURL=duration.pipe.js.map