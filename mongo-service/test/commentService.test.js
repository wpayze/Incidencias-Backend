const Comment = require('../models/Comment');
const commentService = require('../services/commentService');

jest.mock('../models/Comment');

describe('Comment Service', () => {
    afterEach(() => {
        jest.clearAllMocks();
    });

    test('getComments debería devolver todos los comentarios', async () => {
        const mockComments = [
            { _id: '1', content: 'Comentario 1', userId: 'user1', issueId: 'issue1' },
            { _id: '2', content: 'Comentario 2', userId: 'user2', issueId: 'issue2' },
        ];
        Comment.find.mockResolvedValue(mockComments);

        const result = await commentService.getComments();

        expect(result).toEqual(mockComments);
        expect(Comment.find).toHaveBeenCalledTimes(1);
    });

    test('getCommentsByUserId debería devolver comentarios filtrados por userId', async () => {
        const mockComments = [{ _id: '1', content: 'Comentario 1', userId: 'user1', issueId: 'issue1' }];

        const sortMock = jest.fn().mockResolvedValue(mockComments);
        const findMock = jest.fn().mockReturnValue({ sort: sortMock });
        Comment.find.mockImplementation(findMock);

        const result = await commentService.getCommentsByUserId('user1');

        expect(result).toEqual(mockComments);
        expect(Comment.find).toHaveBeenCalledWith({ userId: 'user1' });
        expect(sortMock).toHaveBeenCalledWith({ createdAt: -1 });
    });

    test('createComment debería crear y devolver un nuevo comentario', async () => {
        const commentData = { content: 'Nuevo comentario', userId: 'user1', issueId: 'issue1' };
        const savedComment = { ...commentData, _id: '1' };

        Comment.prototype.save = jest.fn().mockResolvedValue(savedComment);

        const result = await commentService.createComment(commentData);

        expect(result).toEqual(savedComment);
        expect(Comment.prototype.save).toHaveBeenCalledTimes(1);
    });

    test('updateComment debería actualizar un comentario existente', async () => {
        const existingComment = {
            _id: '1',
            content: 'Comentario existente',
            userId: 'user1',
            issueId: 'issue1',
            save: jest.fn().mockResolvedValue(true)
        };

        const updatedData = { content: 'Comentario actualizado' };
        const updatedComment = { ...existingComment, ...updatedData };

        Comment.findById.mockResolvedValue(existingComment);
        existingComment.save.mockResolvedValue(updatedComment);

        const result = await commentService.updateComment('1', updatedData);

        expect(result.content).toBe('Comentario actualizado');
        expect(Comment.findById).toHaveBeenCalledWith('1');
        expect(existingComment.save).toHaveBeenCalledTimes(1);
    });


    test('deleteComment debería eliminar un comentario existente', async () => {
        const existingComment = {
            _id: '1',
            content: 'Comentario existente',
            userId: 'user1',
            issueId: 'issue1',
            remove: jest.fn().mockResolvedValue(true)
        };

        Comment.findById.mockResolvedValue(existingComment);
        await commentService.deleteComment('1');

        expect(Comment.findById).toHaveBeenCalledWith('1');
        expect(existingComment.remove).toHaveBeenCalledTimes(1);
    });

});
