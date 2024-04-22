const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const commentRoutes = require('./routes/commentRoutes');
const issueImageRoutes = require('./routes/issueImageRoutes');

const app = express();
const PORT = 8083;

app.use(bodyParser.json());
app.use('/comments', commentRoutes);
app.use('/issue-images', issueImageRoutes);

mongoose.connect('mongodb://127.0.0.1:27017/mongo-service')
  .then(() => {
    app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
  })
  .catch(err => console.error(err));