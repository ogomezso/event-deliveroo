import config from '../config/environment';

console.log(config);

var headers = new Headers();

var getOrders = {
    method: 'GET',
    headers: headers,
    mode: 'no-cors',
    cache: 'default'
};

fetch(config.apiPath + 'orders', getOrders)
    .then(function(response) {
        console.log(response);
        return response.blob();
    });
