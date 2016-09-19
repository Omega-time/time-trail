"use strict";
// SystemJS configuration file, see links for more information
// https://github.com/systemjs/systemjs
// https://github.com/systemjs/systemjs/blob/master/docs/config-api.md
/***********************************************************************************************
 * User Configuration.
 **********************************************************************************************/
/** Map relative paths to URLs. */
var map = {
    'ng2-file-upload': 'vendor/ng2-file-upload',
    'angular2-oauth2': 'vendor/angular2-oauth2',
    "base64-js": "vendor/angular2-oauth2/node_modules/base64-js/lib/b64.js",
    "buffer": "vendor/angular2-oauth2/node_modules/buffer/index.js",
    "convert-hex": "vendor/angular2-oauth2/node_modules/sha256/node_modules/convert-hex/convert-hex.js",
    "convert-string": "vendor/angular2-oauth2/node_modules/sha256/node_modules/convert-string/convert-string.js",
    "ieee754": "vendor/angular2-oauth2/node_modules/buffer/node_modules/ieee754/index.js",
    "isarray": "vendor/angular2-oauth2/node_modules/buffer/node_modules/isarray/index.js",
    "js-base64": "vendor/angular2-oauth2/node_modules/js-base64/base64.js",
    "sha256": "vendor/angular2-oauth2/node_modules/sha256/lib/sha256.js",
};
/** User packages configuration. */
var packages = {
    'ng2-file-upload': {
        format: 'cjs',
        defaultExtension: 'js',
        main: 'ng2-file-upload.js'
    },
    'angular2-oauth2': { defaultExtension: 'js' }
};
////////////////////////////////////////////////////////////////////////////////////////////////
/***********************************************************************************************
 * Everything underneath this line is managed by the CLI.
 **********************************************************************************************/
var barrels = [
    // Angular specific barrels.
    '@angular/core',
    '@angular/common',
    '@angular/compiler',
    '@angular/forms',
    '@angular/http',
    '@angular/router',
    '@angular/platform-browser',
    '@angular/platform-browser-dynamic',
    // Thirdparty barrels.
    'rxjs',
    // App specific barrels.
    'app',
    'app/shared',
];
var cliSystemConfigPackages = {};
barrels.forEach(function (barrelName) {
    cliSystemConfigPackages[barrelName] = { main: 'index' };
});
// Apply the CLI SystemJS configuration.
System.config({
    map: {
        '@angular': 'vendor/@angular',
        'rxjs': 'vendor/rxjs',
        'main': 'main.js'
    },
    packages: cliSystemConfigPackages
});
// Apply the user's configuration.
System.config({ map: map, packages: packages });
//# sourceMappingURL=system-config.js.map