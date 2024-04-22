const mongoose = require('mongoose');

const commentSchema = new mongoose.Schema({
  issueId: { type: Number, required: true, index: true },
  userId: { type: Number, required: true, index: true },
  content: { type: String, required: true },
  createdAt: { type: Date, default: Date.now },
  parentId: { type: mongoose.Schema.Types.ObjectId, index: true }
});

const Comment = mongoose.model('Comment', commentSchema);

module.exports = Comment;
