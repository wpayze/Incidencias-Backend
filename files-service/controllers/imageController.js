const path = require('path');
const fs = require('fs');

const uploadImage = async (req, res) => {
    try {
        const issueId = req.body.issueId;

        const postData = {
            issueId,
            imageType: path.extname(req.file.originalname).slice(1),
            uploadedAt: new Date().toISOString()
        };

        const response = await fetch(process.env.MONGO_SERVICE_URL + '/issue-images', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(postData)
        });

        if (!response.ok) {
            throw new Error('No se pudo registrar la imagen en el servicio externo');
        }

        const data = await response.json();

        const finalName = data._id + path.extname(req.file.originalname);
        const finalPath = path.join('uploads', finalName);
        const tempPath = req.file.path;

        fs.rename(tempPath, finalPath, (err) => {
            if (err) throw err;

            res.status(200).send({
                message: "Imagen subida y registrada con éxito",
                filename: finalName,
                data: data
            });
        });

        const changeUrlResponse = await fetch(process.env.MYSQL_SERVICE_URL + `/api/issues/${issueId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({imageUrl: finalName})
        });

    } catch (error) {
        res.status(500).send({
            message: "Error al procesar la imagen",
            error: error.message
        });

        if (req.file && req.file.path) {
            fs.unlink(req.file.path, (err) => {
                if (err) console.log("Error al eliminar el archivo temporal:", err.message);
            });
        }
    }
};

const getImage = (req, res) => {
    const filePath = path.join(__dirname, '..', 'uploads', req.params.imageId);
    res.sendFile(filePath);
};

const deleteImage = (req, res) => {
    const filePath = path.join(__dirname, '..', 'uploads', req.params.imageId);
    if (fs.existsSync(filePath)) {
        fs.unlink(filePath, err => {
            if (err) {
                return res.status(500).send({ message: "Error al eliminar el archivo", error: err });
            }
            res.send({ message: "Imagen eliminada con éxito" });
        });
    } else {
        res.status(404).send('Imagen no encontrada');
    }
};

module.exports = {
    uploadImage,
    getImage,
    deleteImage
};
