const LocalImg = require("../models/local_img.model.js");

// Create and Save a new LocalImg
exports.create = (req, res) => {
  // Validate request
  if (!req.body) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
  }

  // Create a LocalImg
  const localImg = new LocalImg({
    local_id: req.body.local_id,
    image_url: req.body.image_url
  });

  // Save LocalImg in the database
  LocalImg.create(localImg, (err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the LocalImg."
      });
    else res.send(data);
  });
};

// Retrieve all LocalImgs from the database.
exports.findAll = (req, res) => {
  LocalImg.getAll((err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while retrieving local images."
      });
    else res.send(data);
  });
};

// Find a single LocalImg with a localImgId
exports.findOne = (req, res) => {
  LocalImg.findById(req.params.localImgId, (err, data) => {
    if (err) {
      if (err.kind === "not_found") {
        res.status(404).send({
          message: `Not found LocalImg with id ${req.params.localImgId}.`
        });
      } else {
        res.status(500).send({
          message: "Error retrieving LocalImg with id " + req.params.localImgId
        });
      }
    } else res.send(data);
  });
};

// Update a LocalImg identified by the localImgId in the request
exports.update = (req, res) => {
  // Validate Request
  if (!req.body) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
  }

  LocalImg.updateById(
    req.params.localImgId,
    new LocalImg(req.body),
    (err, data) => {
      if (err) {
        if (err.kind === "not_found") {
          res.status(404).send({
            message: `Not found LocalImg with id ${req.params.localImgId}.`
          });
        } else {
          res.status(500).send({
            message: "Error updating LocalImg with id " + req.params.localImgId
          });
        }
      } else res.send(data);
    }
  );
};

// Delete a LocalImg with the specified localImgId in the request
exports.delete = (req, res) => {
  LocalImg.remove(req.params.localImgId, (err, data) => {
    if (err) {
      if (err.kind === "not_found") {
        res.status(404).send({
          message: `Not found LocalImg with id ${req.params.localImgId}.`
        });
      } else {
        res.status(500).send({
          message: "Could not delete LocalImg with id " + req.params.localImgId
        });
      }
    } else res.send({ message: `LocalImg was deleted successfully!` });
  });
};

// Delete all LocalImgs from the database.
exports.deleteAll = (req, res) => {
  LocalImg.removeAll((err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while removing all local images."
      });
    else res.send({ message: `All LocalImgs were deleted successfully!` });
  });
};
