(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('hipster', {
            parent: 'entity',
            url: '/hipster',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.hipster.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/hipster/hipsters.html',
                    controller: 'HipsterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('hipster');
                    $translatePartialLoader.addPart('band');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('hipster-detail', {
            parent: 'entity',
            url: '/hipster/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.hipster.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/hipster/hipster-detail.html',
                    controller: 'HipsterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('hipster');
                    $translatePartialLoader.addPart('band');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Hipster', function($stateParams, Hipster) {
                    return Hipster.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('hipster.new', {
            parent: 'hipster',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hipster/hipster-dialog.html',
                    controller: 'HipsterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                shirt: null,
                                age: null,
                                avatar: null,
                                avatarContentType: null,
                                birthday: null,
                                reallyHipster: false,
                                sampleField: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('hipster', null, { reload: true });
                }, function() {
                    $state.go('hipster');
                });
            }]
        })
        .state('hipster.edit', {
            parent: 'hipster',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hipster/hipster-dialog.html',
                    controller: 'HipsterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Hipster', function(Hipster) {
                            return Hipster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('hipster', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('hipster.delete', {
            parent: 'hipster',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hipster/hipster-delete-dialog.html',
                    controller: 'HipsterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Hipster', function(Hipster) {
                            return Hipster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('hipster', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
