const axios = require('axios');
const FormData = require('form-data');

const redirectUploadImageMiddleware = (getUrlConfig, needsParams = false) => {
    return async (req, res) => {
        const config = needsParams ? getUrlConfig(req) : getUrlConfig();
        const { baseUrl, path } = config;
        const url = `${baseUrl}${path}`;

        console.log({FILE: req.file});
        console.log({BODY: req.body});

        try {
            const form = new FormData();
            if (req.body.issueId) {
                form.append('issueId', req.body.issueId);
            }

            if (req.file) {
                form.append('image', req.file.buffer, {
                    filename: req.file.originalname,
                    contentType: req.file.mimetype,
                });
            } else {
                throw new Error("No file uploaded.");
            }

            const response = await axios.post(url, form, { headers: form.getHeaders() });

            if (response.status !== 200) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const contentType = response.headers['content-type'];
            if (contentType && contentType.includes('image')) {
                res.setHeader('Content-Type', contentType);
                res.end(response.data); 
            } else {
                res.send(response.data);
            }
        } catch (error) {
            console.error("HTTP request error:", error);
            res.status(500).send(error.message || 'An error occurred during the request.');
        }
    };
};

module.exports = { redirectUploadImageMiddleware };
