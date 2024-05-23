const Local = require("../models/local.model.js");

// Create and Save a new Local
exports.create = (req, res) => {
  // Validate request
  if (!req.body) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
  }

  // Create a Local
  const local = new Local({
    name: req.body.name,
    address: req.body.address
  });

  // Save Local in the database
  Local.create(local, (err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the Local."
      });
    else res.send(data);
  });
};

// Retrieve all Locals from the database.
exports.findAll = (req, res) => {
  Local.getAll((err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while retrieving locals."
      });
    else res.send(data);
  });
};

// Find a single Local with a localId
exports.findOne = (req, res) => {
  Local.findById(req.params.localId, (err, data) => {
    if (err) {
      if (err.kind === "not_found") {
        res.status(404).send({
          message: `Not found Local with id ${req.params.localId}.`
        });
      } else {
        res.status(500).send({
          message: "Error retrieving Local with id " + req.params.localId
        });
      }
    } else res.send(data);
  });
};

// Update a Local identified by the localId in the request
exports.update = (req, res) => {
  // Validate Request
  if (!req.body) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
  }

  Local.updateById(
    req.params.localId,
    new Local(req.body),
    (err, data) => {
      if (err) {
        if (err.kind === "not_found") {
          res.status(404).send({
            message: `Not found Local with id ${req.params.localId}.`
          });
        } else {
          res.status(500).send({
            message: "Error updating Local with id " + req.params.localId
          });
        }
      } else res.send(data);
    }
  );
};

// Delete a Local with the specified localId in the request
exports.delete = (req, res) => {
  Local.remove(req.params.localId, (err, data) => {
    if (err) {
      if (err.kind === "not_found") {
        res.status(404).send({
          message: `Not found Local with id ${req.params.localId}.`
        });
      } else {
        res.status(500).send({
          message: "Could not delete Local with id " + req.params.localId
        });
      }
    } else res.send({ message: `Local was deleted successfully!` });
  });
};

// Delete all Locals from the database.
exports.deleteAll = (req, res) => {
  Local.removeAll((err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while removing all locals."
      });
    else res.send({ message: `All Locals were deleted successfully!` });
  });
};
