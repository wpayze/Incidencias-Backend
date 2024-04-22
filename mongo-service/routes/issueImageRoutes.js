const express = require('express');
const router = express.Router();
const issueImageController = require('../controllers/issueImageController');

router.get('/', issueImageController.getIssueImages);
router.get('/:id', issueImageController.getIssueImageById);
router.post('/', issueImageController.createIssueImage);
router.put('/:id', issueImageController.updateIssueImage);
router.delete('/:id', issueImageController.deleteIssueImage);

module.exports = router;
