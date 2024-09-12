const express = require('express');
const { redirectRequest } = require('../middleware/redirectMiddleware');

const router = express.Router();

const getBaseRedirectConfig = (basePath, req = null) => {
    const path = basePath.replace(/:(\w+)/g, (match, paramName) => {
        return req && req.params[paramName] ? req.params[paramName] : match;
    });

    return {
        baseUrl: process.env.AUTH_SERVICE_URL,
        path
    };
};

//Basic
router.post('/login', redirectRequest(() => getBaseRedirectConfig('/auth/login')));
router.post('/register', redirectRequest(() => getBaseRedirectConfig('/auth/register')));

//Users
router.get('/users', redirectRequest(() => getBaseRedirectConfig('/api/users')));
router.put('/users/:id', redirectRequest(req => getBaseRedirectConfig('/api/users/:id', req), true));

module.exports = router;
