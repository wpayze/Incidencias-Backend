const express = require('express');
const router = express.Router();
const commentController = require('../controllers/commentController');

router.get('/', commentController.getComments);
router.get('/issue/:issueId', commentController.getCommentsByIssueId);
router.get('/user/:userId', commentController.getCommentsByUserId);
router.post('/', commentController.createComment);
router.put('/:id', commentController.updateComment);
router.delete('/:id', commentController.deleteComment);

module.exports = router;
