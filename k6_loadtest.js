import http from 'k6/http';

import uuid from 'uuid.js'; //Download this open source

import exec from 'k6/execution';

import { sleep, check } from 'k6';

import { Counter } from 'k6/metrics';

import encoding from 'k6/encoding';

export const requests = new Counter('http_reqs');

export const options = {
    vus: 5, //increase this to give load
    iterations: 5 //increase this to give load
};

let parameters = null;

function _init() {

    const credentials = "user:password";
    const encodedCredentials = encoding.b64encode(credentials);

    parameters = {

        headers: {
            "Authorization": `Basic ${encodedCredentials}`,
            "Content-Type": "application/json"
        }
    };
};

_init();

export default function () {
    let requestId = uuid.v4();

    console.log(`id = ${requestId}`);

    let data = JSON.stringify(getPayload(requestId));

    let url = "http://localhost:9001/api/secured/webhooks";

    const res = http.post(url, data, parameters);

    const checkRes = check(res, {
        'Http request status 200': (r) => r.status >= 200 && r.status <= 299,
    });

};

function getPayload(requestId) {
    return {
        "async_response" : {
            "id" : requestId,
            "event": {
                "id" : requestId,
                "type" : "user profile changed"
            }
        }
    };
}