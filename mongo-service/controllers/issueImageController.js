const IssueImage = require('../models/IssueImage');

exports.getIssueImages = async (req, res) => {
    try {
        const issueImages = await IssueImage.find();
        res.status(200).json(issueImages);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

exports.getIssueImageById = async (req, res) => {
    try {
        const issueImage = await IssueImage.findById(req.params.id);
        if (issueImage) {
            res.status(200).json(issueImage);
        } else {
            res.status(404).json({ message: 'Issue Image not found' });
        }
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

exports.createIssueImage = async (req, res) => {
    console.log('Iniciando creación de IssueImage...');
    console.log('Datos recibidos del request:', req.body);

    const issueImage = new IssueImage({
        issueId: req.body.issueId,
        imageType: req.body.imageType,
        description: req.body.description,
        uploadedAt: req.body.uploadedAt || new Date(),
    });

    console.log('Objeto IssueImage a guardar:', issueImage);

    try {
        const newIssueImage = await issueImage.save();
        console.log('Imagen del issue creada con éxito:', newIssueImage);
        res.status(201).json(newIssueImage);
    } catch (error) {
        console.error('Error al crear la imagen del issue:', error.message);
        res.status(400).json({ message: error.message });
    }
};

exports.updateIssueImage = async (req, res) => {
    try {
        const issueImage = await IssueImage.findById(req.params.id);
        if (!issueImage) {
            return res.status(404).json({ message: "Issue Image not found" });
        }

        issueImage.issueId = req.body.issueId ?? issueImage.issueId;
        issueImage.imageUrl = req.body.imageUrl ?? issueImage.imageUrl;
        issueImage.imageType = req.body.imageType ?? issueImage.imageType;
        issueImage.description = req.body.description ?? issueImage.description;

        const updatedIssueImage = await issueImage.save();
        res.status(200).json(updatedIssueImage);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
};

exports.deleteIssueImage = async (req, res) => {
    try {
        const issueImage = await IssueImage.findById(req.params.id);
        if (!issueImage) {
            return res.status(404).json({ message: "Issue Image not found" });
        }
        await issueImage.remove();
        res.status(200).json({ message: "Issue Image deleted" });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};
