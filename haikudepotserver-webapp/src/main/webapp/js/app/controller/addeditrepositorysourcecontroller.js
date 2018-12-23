/*
 * Copyright 2015-2018, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */

angular.module('haikudepotserver').controller(
    'AddEditRepositorySourceController',
    [
        '$scope','$log','$routeParams',
        'jsonRpc','constants','breadcrumbs','breadcrumbFactory','userState','errorHandling',
        function(
            $scope,$log,$routeParams,
            jsonRpc,constants,breadcrumbs,breadcrumbFactory,userState,errorHandling) {

            $scope.workingRepositorySource = undefined;
            $scope.amEditing = !!$routeParams.repositorySourceCode;
            var amSaving = false;

            $scope.shouldSpin = function() {
                return !$scope.workingRepositorySource || amSaving;
            };

            $scope.deriveFormControlsContainerClasses = function(name) {
                return $scope.addEditRepositorySourceForm[name].$invalid ? ['form-control-group-error'] : [];
            };

            // the validity of this field may have been set to false because of the
            // attempt to save it on the server-side.  This function will be hit when
            // the code changes so that validation does not apply.

            $scope.codeChanged = function() {
                $scope.addEditRepositorySourceForm.code.$setValidity('unique',true);
            };

            $scope.forcedInternalBaseUrlChanged = function() {
                $scope.addEditRepositorySourceForm.forcedInternalBaseUrl.$setValidity('malformed', true);
                $scope.addEditRepositorySourceForm.forcedInternalBaseUrl.$setValidity('trailingslash', true);
            };

            function refreshBreadcrumbItems() {

                var repository = { code : $routeParams.repositoryCode };

                var b = [
                    breadcrumbFactory.createHome(),
                    breadcrumbFactory.createListRepositories(),
                    breadcrumbFactory.createViewRepository(repository)
                ];

                if($scope.amEditing) {
                    b.push(breadcrumbFactory.applyCurrentLocation(breadcrumbFactory.createEditRepositorySource($scope.workingRepositorySource)));
                }
                else {
                    b.push(breadcrumbFactory.applyCurrentLocation(breadcrumbFactory.createAddRepositorySource(repository)));
                }

                breadcrumbs.mergeCompleteStack(b);
            }

            function refreshRepositorySource() {
                if($routeParams.repositorySourceCode) {
                    jsonRpc.call(
                        constants.ENDPOINT_API_V1_REPOSITORY,
                        "getRepositorySource",
                        [{ code : $routeParams.repositorySourceCode }]
                    ).then(
                        function(result) {
                            $scope.workingRepositorySource = _.clone(result);
                            refreshBreadcrumbItems();
                            $log.info('fetched repository source; '+result.code);
                        },
                        function(err) {
                            errorHandling.handleJsonRpcError(err);
                        }
                    );
                }
                else {
                    $scope.workingRepositorySource = {
                        repositoryCode : $routeParams.repositoryCode
                    };
                    refreshBreadcrumbItems();
                }
            }

            function refreshData() {
                refreshRepositorySource();
            }

            refreshData();

            $scope.goSave = function() {

                if($scope.addEditRepositorySourceForm.$invalid) {
                    throw Error('expected the save of a repository source to only to be possible if the form is valid');
                }

                amSaving = true;

                if($scope.amEditing) {
                    jsonRpc.call(
                        constants.ENDPOINT_API_V1_REPOSITORY,
                        "updateRepositorySource",
                        [{
                            filter : [ 'FORCED_INTERNAL_BASE_URL' ],
                            forcedInternalBaseUrl : $scope.workingRepositorySource.forcedInternalBaseUrl,
                            code : $routeParams.repositorySourceCode
                        }]
                    ).then(
                        function() {
                            $log.info('did update repository source; '+$routeParams.repositorySourceCode);
                            breadcrumbs.popAndNavigate();
                        },
                        function(err) {

                            switch(err.code) {
                                case jsonRpc.errorCodes.VALIDATION:
                                    errorHandling.relayValidationFailuresIntoForm(
                                        err.data.validationfailures,
                                        $scope.addEditRepositorySourceForm);
                                    break;

                                default:
                                    errorHandling.handleJsonRpcError(err);
                                    break;
                            }

                            amSaving = false;
                        }
                    );
                }
                else {
                    jsonRpc.call(
                        constants.ENDPOINT_API_V1_REPOSITORY,
                        "createRepositorySource",
                        [{
                            repositoryCode : $scope.workingRepositorySource.repositoryCode,
                            code : $scope.workingRepositorySource.code
                        }]
                    ).then(
                        function() {
                            $log.info('did create repository source; '+$scope.workingRepositorySource.code);
                            breadcrumbs.pop();
                            breadcrumbs.pushAndNavigate(breadcrumbFactory.createViewRepositorySource($scope.workingRepositorySource));
                        },
                        function(err) {

                            switch(err.code) {
                                case jsonRpc.errorCodes.VALIDATION:
                                    errorHandling.relayValidationFailuresIntoForm(
                                        err.data.validationfailures,
                                        $scope.addEditRepositorySourceForm);
                                    break;

                                default:
                                    errorHandling.handleJsonRpcError(err);
                                    break;
                            }

                            amSaving = false;
                        }
                    );
                }

            }

        }
    ]
);