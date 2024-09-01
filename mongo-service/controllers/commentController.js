const Comment = require('../models/Comment');

exports.getComments = async (req, res) => {
    try {
        const comments = await Comment.find();
        res.status(200).json(comments);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

exports.getCommentsByUserId = async (req, res) => {
    try {
        const userId = req.params.userId;
        const comments = await Comment.find({ userId: userId }).sort({ createdAt: -1 });
        res.status(200).json(comments);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

exports.getCommentsByIssueId = async (req, res) => {
    try {
        const issueId = parseInt(req.params.issueId);
        const comments = await Comment.find({ issueId: issueId });
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
        const comment = await Comment.findById(req.params.id);
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
    const comment = new Comment({
        issueId: req.body.issueId,
        userId: req.body.userId,
        content: req.body.content,
        createdAt: req.body.createdAt || new Date(),
        parentId: req.body.parentId
    });

    try {
        const newComment = await comment.save();
        res.status(201).json(newComment);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
};

exports.updateComment = async (req, res) => {
    try {
        const comment = await Comment.findById(req.params.id);
        if (!comment) {
            return res.status(404).json({ message: "Comment not found" });
        }

        comment.issueId = req.body.issueId ?? comment.issueId;
        comment.userId = req.body.userId ?? comment.userId;
        comment.content = req.body.content ?? comment.content;
        comment.parentId = req.body.parentId ?? comment.parentId;

        const updatedComment = await comment.save();
        res.status(200).json(updatedComment);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
};

exports.deleteComment = async (req, res) => {
    try {
        const comment = await Comment.findById(req.params.id);
        if (!comment) {
            return res.status(404).json({ message: "Comment not found" });
        }
        await comment.remove();
        res.status(200).json({ message: "Comment deleted" });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};
