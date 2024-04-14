const express = require('express');

//const validateToken = require('./middleware/validateTokenMiddleware');
require('dotenv').config();

const authRoutes = require('./routes/authRoutes');
const issueRoutes = require('./routes/issueRoutes');
const categoryRoutes = require('./routes/categoryRoutes');
const statusRoutes = require('./routes/statusRoutes');

const app = express();
const PORT = 3000;

app.use(express.json());

app.use('/api/auth', authRoutes);
app.use('/api/issues', issueRoutes);
app.use('/api/categories', categoryRoutes);
app.use('/api/statuses', statusRoutes);

app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
