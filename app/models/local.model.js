const sql = require("./db.js");

// Constructor
const Local = function(local) {
  this.name = local.name;
  this.location = local.location;
  this.description = local.description;
  this.ref = local.ref;
};

Local.create = (newLocal, result) => {
  sql.query("INSERT INTO locals SET ?", newLocal, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(err, null);
      return;
    }

    console.log("created local: ", { id: res.insertId, ...newLocal });
    result(null, { id: res.insertId, ...newLocal });
  });
};

Local.findById = (id, result) => {
  sql.query(`SELECT * FROM locals WHERE id = ${id}`, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(err, null);
      return;
    }

    if (res.length) {
      console.log("found local: ", res[0]);
      result(null, res[0]);
      return;
    }

    result({ kind: "not_found" }, null);
  });
};

Local.getAll = (result) => {
  sql.query("SELECT * FROM locals", (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    console.log("locals: ", res);
    result(null, res);
  });
};

Local.updateById = (id, local, result) => {
  sql.query(
    "UPDATE locals SET name = ?, location = ?, description = ?, ref = ? WHERE id = ?",
    [local.name, local.location, local.description, local.ref, id],
    (err, res) => {
      if (err) {
        console.log("error: ", err);
        result(null, err);
        return;
      }

      if (res.affectedRows == 0) {
        result({ kind: "not_found" }, null);
        return;
      }

      console.log("updated local: ", { id: id, ...local });
      result(null, { id: id, ...local });
    }
  );
};

Local.remove = (id, result) => {
  sql.query("DELETE FROM locals WHERE id = ?", id, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    if (res.affectedRows == 0) {
      result({ kind: "not_found" }, null);
      return;
    }

    console.log("deleted local with id: ", id);
    result(null, res);
  });
};

Local.removeAll = (result) => {
  sql.query("DELETE FROM locals", (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    console.log(`deleted ${res.affectedRows} locals`);
    result(null, res);
  });
};

module.exports = Local;
