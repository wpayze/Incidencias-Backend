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

router.post('/', redirectRequest(() => getBaseRedirectConfig('/api/comments')));
router.get('/user/:id', redirectRequest(req => getBaseRedirectConfig('/api/comments/user/:id', req), true));

module.exports = router;
