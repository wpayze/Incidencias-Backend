const path = require('path');
const fs = require('fs');
const amqp = require('amqplib/callback_api');

const uploadImage = async (req, res) => {
    try {
        const issueId = req.body.issueId;

        const postData = {
            issueId,
            imageType: path.extname(req.file.originalname).slice(1),
            uploadedAt: new Date().toISOString()
        };

        console.log('POST data:', postData);

        let data;
        try {
            const response = await fetch(process.env.MONGO_SERVICE_URL + '/issue-images', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(postData)
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Error al registrar la imagen en Mongo Service: ${errorText}`);
            }

            data = await response.json();
            console.log('Response from Mongo Service:', data);

        } catch (mongoError) {
            console.error('Error en la solicitud a Mongo Service:', mongoError.message);
            throw new Error('Fallo en la solicitud a Mongo Service');
        }

        const finalName = data._id + path.extname(req.file.originalname);
        const finalPath = path.join('uploads', finalName);
        const tempPath = req.file.path;

        fs.rename(tempPath, finalPath, (err) => {
            if (err) throw err;

            amqp.connect(process.env.RABBITMQ_URL, function (error0, connection) {
                if (error0) {
                    console.error('Error al conectar a RabbitMQ:', error0);
                    throw error0;
                }
                connection.createChannel(function (error1, channel) {
                    if (error1) {
                        console.error('Error al crear el canal en RabbitMQ:', error1);
                        throw error1;
                    }

                    const queue = 'imagenes';
                    const msg = JSON.stringify({ imageName: finalName });

                    channel.assertQueue(queue, {
                        durable: true
                    });

                    channel.sendToQueue(queue, Buffer.from(msg));

                    console.log(" [x] Sent %s", msg);
                });

                setTimeout(function () {
                    connection.close();
                }, 500);
            });

            res.status(200).send({
                message: "Imagen subida y registrada con éxito",
                filename: finalName,
                data: data
            });
        });

        try {
            const changeUrlResponse = await fetch(process.env.MYSQL_SERVICE_URL + `/api/issues/${issueId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ imageUrl: finalName })
            });

            if (!changeUrlResponse.ok) {
                const errorText = await changeUrlResponse.text();
                throw new Error(`Error al actualizar la URL en MySQL Service: ${errorText}`);
            }

        } catch (mysqlError) {
            console.error('Error en la solicitud a MySQL Service:', mysqlError.message);
            throw new Error('Fallo en la solicitud a MySQL Service');
        }

    } catch (error) {
        console.error('Error general:', error.message);

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

// const replaceImage = async (req, res) => {
//     try {
//         const imageName = req.params.imageName;
//         const tempPath = req.file.path;
//         const finalPath = path.join('uploads', imageName);

//         fs.rename(tempPath, finalPath, (err) => {
//             if (err) throw err;

//             res.status(200).send({
//                 message: "Imagen reemplazada con éxito",
//                 filename: imageName
//             });
//         });
//     } catch (error) {
//         res.status(500).send({
//             message: "Error al reemplazar la imagen",
//             error: error.message
//         });

//         if (req.file && req.file.path) {
//             fs.unlink(req.file.path, (err) => {
//                 if (err) console.log("Error al eliminar el archivo temporal:", err.message);
//             });
//         }
//     }
// };

const replaceImage = async (req, res) => {
    try {
        const imageName = req.params.imageName;
        const tempPath = req.file.path;

        // Generar el nombre del archivo comprimido agregando el sufijo '-compressed'
        const compressedName = path.parse(imageName).name + '-compressed' + path.extname(imageName);
        const finalPath = path.join('uploads', compressedName);

        fs.rename(tempPath, finalPath, (err) => {
            if (err) throw err;

            res.status(200).send({
                message: "Imagen comprimida y guardada con éxito",
                originalFilename: imageName,
                compressedFilename: compressedName
            });
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
    getImage,
    uploadImage,
    replaceImage,
    deleteImage
};
