require('dotenv').config();
const NodeCache = require('node-cache');
const tokenCache = new NodeCache({ stdTTL: 300 });

const validateToken = async (req, res, next) => {
    const token = req.headers['authorization'];
    if (!token) {
        return res.status(403).send({ message: "Token is required" });
    }

    if (tokenCache.has(token)) {
        return next();
    }

    try {
        const response = await fetch(process.env.AUTH_SERVICE_URL, {
            method: 'GET',
            headers: { 'Authorization': token }
        });

        if (!response.ok) {
            throw new Error('Token validation failed');
        }

        tokenCache.set(token, true);
        next();
    } catch (error) {
        return res.status(401).send({ message: "Invalid Token" });
    }
};

module.exports = validateToken;
