const Comment = require('../models/Comment');
const commentService = require('../services/commentService');

exports.getComments = async (req, res) => {
    try {
        const comments = await commentService.getComments();
        res.status(200).json(comments);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

exports.getCommentsByUserId = async (req, res) => {
    try {
        const userId = req.params.userId;
        const comments = await commentService.getCommentsByUserId(userId);
        res.status(200).json(comments);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

exports.getCommentsByIssueId = async (req, res) => {
    try {
        const issueId = parseInt(req.params.issueId);
        const comments = await commentService.getCommentsByIssueId(issueId);
        if (comments.length) {
            res.status(200).json(comments);
        } else {
            res.status(404).json({ message: 'No comments found for this issue ID' });
        }
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

exports.getCommentById = async (req, res) => {
    try {
        const comment = await commentService.getCommentById(req.params.id);
        if (comment) {
            res.status(200).json(comment);
        } else {
            res.status(404).json({ message: 'Comment not found' });
        }
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

exports.createComment = async (req, res) => {
    try {
        const newComment = await commentService.createComment(req.body);
        res.status(201).json(newComment);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
};

exports.updateComment = async (req, res) => {
    try {
        const updatedComment = await commentService.updateComment(req.params.id, req.body);
        res.status(200).json(updatedComment);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
};

exports.deleteComment = async (req, res) => {
    try {
        await commentService.deleteComment(req.params.id);
        res.status(200).json({ message: "Comment deleted" });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};
