const express = require('express');
const { redirectRequest } = require('../middleware/redirectMiddleware');

const router = express.Router();

const getBaseRedirectConfig = (basePath, req = null) => {
    const path = basePath.replace(/:(\w+)/g, (match, paramName) => {
        return req && req.params[paramName] ? req.params[paramName] : match;
    });

    return {
        baseUrl: process.env.ISSUE_SERVICE_URL,
        path
    };
};

//Basic
router.get('/', redirectRequest(() => getBaseRedirectConfig('/api/statuses')));
router.get('/:id', redirectRequest(req => getBaseRedirectConfig('/api/statuses/:id', req), true));
router.post('/', redirectRequest(() => getBaseRedirectConfig('/api/statuses')));
router.put('/:id', redirectRequest(req => getBaseRedirectConfig('/api/statuses/:id', req), true));

module.exports = router;
