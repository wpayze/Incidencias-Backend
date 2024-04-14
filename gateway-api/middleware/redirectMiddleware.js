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

            const response = await fetch(url, {
                method: req.method,
                body: ['GET', 'HEAD'].includes(req.method.toUpperCase()) ? null : JSON.stringify(req.body),
                headers: headers,
            });

            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

            const data = await response.json();
            res.send(data);
        } catch (error) {
            console.error(error);
            res.status(500).send(error.message || 'An error occurred during the request.');
        }
    };
};


module.exports = { redirectRequest };
