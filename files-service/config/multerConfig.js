const multer = require('multer');
const path = require('path');

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'temp_uploads/')
    },
    filename: (req, file, cb) => {
        const tempName = Date.now() + path.extname(file.originalname);
        cb(null, tempName);
    }
});

const upload = multer({ storage: storage });

module.exports = upload;
