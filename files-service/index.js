const express = require('express');
const imageRoutes = require('./routes/imageRoutes');

const app = express();
const port = 8085;

require('dotenv').config();

app.use(express.json());
app.use('/images', imageRoutes);

app.listen(port, () => {
    console.log(`Servidor corriendo en http://localhost:${port}`);
});
