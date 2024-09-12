const express = require('express');
const router = express.Router();
const imageController = require('../controllers/imageController');
const upload = require('../config/multerConfig');

router.post('/upload', upload.single('image'), imageController.uploadImage);
router.post('/replaceImage/:imageName', upload.single('image'), imageController.replaceImage);
router.get('/:imageId', imageController.getImage);
router.delete('/:imageId', imageController.deleteImage);

module.exports = router;
