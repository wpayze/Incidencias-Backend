const express = require('express');
const { redirectRequest } = require('../middleware/redirectMiddleware');

const router = express.Router();

const getBaseRedirectConfig = (path) => ({
    baseUrl: process.env.ISSUE_SERVICE_URL,
    path,
});

//Basic
router.get('/', redirectRequest(() => getBaseRedirectConfig('/api/categories')));

module.exports = router;
