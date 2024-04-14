const express = require('express');
const { redirectRequest } = require('../middleware/redirectMiddleware');

const router = express.Router();

const getBaseRedirectConfig = (path) => ({
    baseUrl: process.env.AUTH_SERVICE_URL,
    path,
});

//Basic
router.post('/login', redirectRequest(() => getBaseRedirectConfig('/auth/login')));
router.post('/register', redirectRequest(() => getBaseRedirectConfig('/auth/register')));

//Users
router.get('/users', redirectRequest(() => getBaseRedirectConfig('/api/users')));

module.exports = router;
