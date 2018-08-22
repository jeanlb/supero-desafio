/* 
 * Este script eh o responsavel pelo roteamento das urls/requisicoes no frontend.
 * Utilizando o plugin (JavaScript): https://www.npmjs.com/package/jq-router
*/

var routes = {},
defaultRoute = 'list';

routes['list'] = {
    url: '#/',
    templateUrl: 'views/list.html'
};

routes['add'] = {
    url: '#/add',
    templateUrl: 'views/add.html'
};

routes['edit'] = {
    url: '#/edit/:id', // parametro id sendo pego no edit_task.js
    templateUrl: 'views/edit.html'
};

routes['view'] = {
    url: '#/view/:id', // parametro id sendo pego no view_task.js
    templateUrl: 'views/view.html'
};

$.router
.setData(routes)
.setDefault(defaultRoute)
.onViewChange(function(e, viewRoute, route, params) {
	if (route.name != 'list') {
		$('#message').html('');
	}
});

$.when($.ready)
.then(function() {
    $.router.run('.view', 'list');
});