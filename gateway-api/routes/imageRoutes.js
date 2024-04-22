const express = require('express');
const { redirectUploadImageMiddleware } = require('../middleware/redirectStaticRequestMiddleware');
const multer = require('multer');
const { redirectDownloadImageMiddleware } = require('../middleware/redirectDownloadImageMiddleware');

const router = express.Router();
const storage = multer.memoryStorage();
const upload = multer({ storage: storage });

const getBaseRedirectConfig = (basePath, req = null) => {
    const path = basePath.replace(/:(\w+)/g, (match, paramName) => {
        return req && req.params[paramName] ? req.params[paramName] : match;
    });

    return {
        baseUrl: process.env.FILE_SERVICE_URL,
        path
    };
};

router.post('/upload', upload.single('image'), redirectUploadImageMiddleware(() => getBaseRedirectConfig('/images/upload')));
router.get('/:id', redirectDownloadImageMiddleware(req => getBaseRedirectConfig('/images/:id', req), true));
router.delete('/:id', redirectDownloadImageMiddleware(() => getBaseRedirectConfig('/api/issues')));

module.exports = router;
