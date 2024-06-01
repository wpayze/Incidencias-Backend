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
router.get('/', redirectRequest(() => getBaseRedirectConfig('/api/issues')));
router.get('/:id', redirectRequest(req => getBaseRedirectConfig('/api/issues/:id', req), true));
router.get('/user/:id', redirectRequest(req => getBaseRedirectConfig('/api/issues/user/:id', req), true));
router.post('/', redirectRequest(() => getBaseRedirectConfig('/api/issues')));

module.exports = router;
