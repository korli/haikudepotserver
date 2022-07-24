/*
 * Copyright 2014-2022, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */

angular.module('haikudepotserver').controller(
    'ReportsController',
    [
        '$scope','$log','$timeout',
        'remoteProcedureCall','breadcrumbs','breadcrumbFactory','userState','constants',
        'errorHandling',
        function(
            $scope,$log,$timeout,
            remoteProcedureCall,breadcrumbs,breadcrumbFactory,userState,constants,
            errorHandling) {

            $scope.didReject = false;
            $scope.didRejectTimeout = undefined;

            breadcrumbs.mergeCompleteStack([
                breadcrumbFactory.createHome(),
                breadcrumbFactory.createReports()
            ]);

            /**
             * <p>A report may be enqueued (accepted), in which case a GUID will be supplied for the report
             * and the user should then be able to see the report in the "view job" page.  If the report is
             * rejected then a null / empty GUID will be supplied -- maybe because they were already running
             * the report?</p>
             */

            function navigateToViewJobOrNotifyRejection(guid) {

                function notifyRejection() {
                    $scope.didReject = true;

                    if ($scope.didRejectTimeout) {
                        $timeout.cancel($scope.didRejectTimeout);
                    }

                    $scope.didRejectTimeout = $timeout(function () {
                        $scope.didReject = false;
                        $scope.didRejectTimeout = undefined;
                    }, 3000);
                }

                if (!guid || !guid.length) {
                    notifyRejection();
                }
                else {
                    breadcrumbs.pushAndNavigate(breadcrumbFactory.createViewJob({ guid:guid }));
                }
            }

            /**
             * <p>A number of reports are 'basic' in that there are no parameters and the only differentiating
             * feature is the method that is invoked to start it; this function unifies the code to start one
             * of these ones.</p>
             */

            function goBasicPkgReport(methodName) {
                remoteProcedureCall.call(constants.ENDPOINT_API_V2_PKG_JOB, methodName).then(
                    function (data) {
                        navigateToViewJobOrNotifyRejection(data.guid);
                    },
                    function (err) {
                        errorHandling.handleRemoteProcedureCallError(err);
                    }
                );
            }

            $scope.goPkgVersionLocalizationCoverageExportSpreadsheet = function () {
                goBasicPkgReport('queue-pkg-version-localization-coverage-export-spreadsheet-job');
            };

            $scope.goPkgLocalizationCoverageExportSpreadsheet = function () {
                goBasicPkgReport('queue-pkg-localization-coverage-export-spreadsheet-job');
            };

            $scope.goPkgCategoryCoverageExportSpreadsheet = function () {
                goBasicPkgReport('queue-pkg-category-coverage-export-spreadsheet-job');
            };

            $scope.goPkgProminenceAndUserRatingSpreadsheetReport = function () {
                goBasicPkgReport('queue-pkg-prominence-and-user-rating-spreadsheet-job');
            };

            $scope.goPkgIconSpreadsheetReport = function () {
                goBasicPkgReport('queue-pkg-icon-spreadsheet-job');
            };

            $scope.goPkgScreenshotSpreadsheetReport = function () {
                goBasicPkgReport('queue-pkg-screenshot-spreadsheet-job');
            };

            $scope.goPkgIconExportArchive = function () {
                goBasicPkgReport('queue-pkg-icon-export-archive-job');
            };

            $scope.goPkgScreenshotExportArchive = function () {
                goBasicPkgReport('queue-pkg-screenshot-export-archive-job');
            };

            $scope.goAuthorizationRulesSpreadsheet= function () {
                remoteProcedureCall.call(constants.ENDPOINT_API_V2_AUTHORIZATION_JOB, 'queue-authorization-rules-spreadsheet').then(
                    function (data) {
                        navigateToViewJobOrNotifyRejection(data.guid);
                    },
                    function (err) {
                        errorHandling.handleRemoteProcedureCallError(err);
                    }
                );
            };

            $scope.goUserRatingSpreadsheetReportAll = function () {
                remoteProcedureCall.call(constants.ENDPOINT_API_V2_USERRATING_JOB, 'queue-user-rating-spreadsheet-job').then(
                    function (data) {
                        navigateToViewJobOrNotifyRejection(data.guid);
                    },
                    function (err) {
                        errorHandling.handleRemoteProcedureCallError(err);
                    }
                );
            };

        }
    ]
);
