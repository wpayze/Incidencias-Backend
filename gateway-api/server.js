const express = require('express');
const NodeCache = require('node-cache');
const validateToken = require('./middleware/validateTokenMiddleware');

const app = express();
const PORT = 3000;

const tokenCache = new NodeCache({ stdTTL: 300 });

app.get('/public', (req, res) => {
    res.send('This is a public route');
});

app.get('/private', validateToken, (req, res) => {
    res.send('This is a protected route');
});

app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
