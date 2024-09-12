const Comment = require('../models/Comment');

exports.getComments = async () => {
    return await Comment.find();
};

exports.getCommentsByUserId = async (userId) => {
    return await Comment.find({ userId: userId }).sort({ createdAt: -1 });
};

exports.getCommentsByIssueId = async (issueId) => {
    return await Comment.find({ issueId: issueId });
};

exports.getCommentById = async (id) => {
    return await Comment.findById(id);
};

exports.createComment = async (commentData) => {
    const comment = new Comment({
        issueId: commentData.issueId,
        userId: commentData.userId,
        content: commentData.content,
        createdAt: commentData.createdAt || new Date(),
        parentId: commentData.parentId
    });

    return await comment.save();
};

exports.updateComment = async (id, commentData) => {
    const comment = await Comment.findById(id);
    if (!comment) {
        throw new Error("Comment not found");
    }

    comment.issueId = commentData.issueId ?? comment.issueId;
    comment.userId = commentData.userId ?? comment.userId;
    comment.content = commentData.content ?? comment.content;
    comment.parentId = commentData.parentId ?? comment.parentId;

    return await comment.save();
};

exports.deleteComment = async (id) => {
    const comment = await Comment.findById(id);
    if (!comment) {
        throw new Error("Comment not found");
    }
    return await comment.remove();
};
