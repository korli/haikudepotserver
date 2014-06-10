/*
 * Copyright 2013-2014, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */

angular.module('haikudepotserver')
    .constant('constants', {

        NATURALLANGUAGECODE_ENGLISH : 'en',

        DELAY_SPINNER : 1000, // millis

        // used for search constraint on the 'home' page.

        RECENT_DAYS : 90,

        ARCHITECTURE_CODE_DEFAULT : 'x86',

        ENDPOINT_API_V1_REPOSITORY : '/api/v1/repository',
        ENDPOINT_API_V1_PKG : '/api/v1/pkg',
        ENDPOINT_API_V1_CAPTCHA : '/api/v1/captcha',
        ENDPOINT_API_V1_MISCELLANEOUS : '/api/v1/miscellaneous',
        ENDPOINT_API_V1_USER : '/api/v1/user',
        ENDPOINT_API_V1_USERRATING : '/api/v1/userrating',

        MEDIATYPE_PNG : 'image/png',
        MEDIATYPE_HAIKUVECTORICONFILE : 'application/x-vnd.haiku-icon',

        SVG_RIGHT_ARROW : '<svg height=\"12\" width=\"12\"><path fill=\"black\" d=\"M0 4.5 L0 7.5 L4 7.5 L4 12 L12 6 L4 0 L4 4.5\"/></svg>',
        SVG_LEFT_ARROW : '<svg height=\"12\" width=\"12\"><path fill=\"black\" d=\"M12 4.5 L12 7.5 L8 7.5 L8 12 L0 6 L8 0 L8 4.5\"/></svg>'
    })

    // this constant is an object of mix-ins that can be used to extend a directive so that it
    // has access to a cache of handy functions that can be re-used.

    .constant('standardDirectiveMixins', {

        /**
         * <p>This function is able to adjust the visibility of the element by adding or removing a class.  It
         * will hide or show the element based on the results of a permissions check.  Either a single
         * permissions can be supplied to check or the an array of permission codes.  If the target or the
         * permissions are empty then the element will not be shown.</p>
         *
         * <p>Note that a null value for the targetIdentifier and the targetType means to evaluate the permissions
         * against the currently authenticated principal.</p>
         *
         * @param permissionCode is either an array or a single permission represented as a string.
         * @param targetType is the type of the target object against which the permission check is undertaken; eg "REPOSITORY"
         * @param targetIdentifier is the identifier for the target; eg "erik" for a user
         */

        showOrHideElementAfterCheckPermission : function(
            userState,
            element,
            permissionCode,
            targetType,
            targetIdentifier) {

            if(null==targetType && targetIdentifier) {
                throw 'if the target type is null (check on principal) then the target identifier is also expected to be null';
            }

            if(!permissionCode||(!targetIdentifier&&null!=targetType)) {
                element.addClass('app-hide');
            }
            else {
                var targetAndPermissions = [];

                if(angular.isArray(permissionCode)) {
                    _.each(permissionCode, function(item) {
                        targetAndPermissions.push({
                            targetType: targetType,
                            targetIdentifier : targetIdentifier,
                            permissionCode : '' + item
                        });
                    });
                }
                else {
                    targetAndPermissions.push({
                        targetType: targetType,
                        targetIdentifier : targetIdentifier,
                        permissionCode : '' + permissionCode
                    });
                }

                userState.areAuthorized(targetAndPermissions).then(function(flag) {
                    if(flag) {
                        element.removeClass('app-hide');
                    }
                    else {
                        element.addClass('app-hide');
                    }
                });
            }

        },

        /**
         * <p>This function will return true if the element supplied appears to be inside
         * a form element.</p>
         */

        isChildOfForm: function (e) {

            function isChildOfFormInternal(e2) {
                var tagName = e2.prop('tagName').toLowerCase();

                switch (tagName) {

                    case 'form':
                        return true;

                    case 'html':
                    case 'body':
                        break;

                    default:
                        var p2 = e2.parent();
                        if (p2 && p2.length) {
                            return isChildOfFormInternal(p2);
                        }
                        break;
                }

                return false;
            }

            return isChildOfFormInternal(e);
        },

        /**
         * <p>This function will return a string that represents the version number in a string form.</p>
         */

        pkgVersionElementsToString: function(pkgVersion) {
            var parts = [ pkgVersion.major ];

            if (pkgVersion.minor) {
                parts.push(pkgVersion.minor);
            }

            if (pkgVersion.micro) {
                parts.push(pkgVersion.micro);
            }

            if (pkgVersion.preRelease) {
                parts.push(pkgVersion.preRelease);
            }

            if (pkgVersion.revision) {
                parts.push('' + pkgVersion.revision);
            }

            return parts.join('.');
        }
    });