const redirectRequest = (getUrlConfig, needsParams = false) => {
    return async (req, res) => {
        const config = needsParams ? getUrlConfig(req) : getUrlConfig();

        const { baseUrl, path } = config;
        const url = `${baseUrl}${path}`;

        try {
            const headers = { ...req.headers };
            delete headers['host'];
            delete headers['connection'];
            delete headers['content-length'];

            console.log(`Initiating request to ${url} with method ${req.method} and headers:`, headers);

            const response = await fetch(url, {
                method: req.method,
                body: ['GET', 'HEAD'].includes(req.method.toUpperCase()) ? null : JSON.stringify(req.body),
                headers: headers,
            });

            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

            const data = await response.json();
            res.send(data);
        } catch (error) {
            console.error(`Error during request to ${url}`);
            console.error(`Request method: ${req.method}`);
            console.error(`Request headers:`, req.headers);
            console.error(`Request body:`, req.body);
            console.error(`Error message: ${error.message}`);
            res.status(500).send(`Error during request to ${url}: ${error.message || 'An unknown error occurred.'}`);
        }
    };
};


module.exports = { redirectRequest };
