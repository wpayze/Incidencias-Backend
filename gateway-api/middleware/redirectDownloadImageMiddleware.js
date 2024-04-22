const { Readable } = require( "stream" );

const redirectDownloadImageMiddleware = (getUrlConfig, needsParams = false) => {
    return async (req, res) => {
        const config = needsParams ? getUrlConfig(req) : getUrlConfig();
        const { baseUrl, path } = config;
        const url = `${baseUrl}${path}`;

        try {
            const headers = { ...req.headers };
            delete headers['host'];
            delete headers['connection'];
            delete headers['content-length'];
            delete headers['content-type'];

            const response = await fetch(url, {
                method: req.method,
                body: ['GET', 'HEAD'].includes(req.method.toUpperCase()) ? null : req.rawBody, 
                headers: headers,
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const contentType = response.headers.get('content-type');
            res.setHeader('Content-Type', contentType);
            Readable.fromWeb(response.body).pipe(res);
        } catch (error) {
            console.error(error);
            res.status(500).send(error.message || 'An error occurred during the request.');
        }
    };
};

module.exports = { redirectDownloadImageMiddleware };
