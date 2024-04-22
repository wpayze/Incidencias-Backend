const mongoose = require('mongoose');

const issueImageSchema = new mongoose.Schema({
  issueId: { type: Number, required: true, index: true },
  imageType: { type: String, required: true, enum: ['jpg', 'png'] },
  description: { type: String },
  uploadedAt: { type: Date, default: Date.now }
});

const IssueImage = mongoose.model('IssueImage', issueImageSchema);

module.exports = IssueImage;
