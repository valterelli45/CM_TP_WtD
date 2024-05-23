const sql = require("./db.js");

// Constructor
const LocalImg = function(localImg) {
  this.local_id = localImg.local_id;
  this.image_url = localImg.image_url;
};

LocalImg.create = (newLocalImg, result) => {
  sql.query("INSERT INTO locals_img SET ?", newLocalImg, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(err, null);
      return;
    }

    console.log("created local image: ", { id: res.insertId, ...newLocalImg });
    result(null, { id: res.insertId, ...newLocalImg });
  });
};

LocalImg.findById = (id, result) => {
  sql.query(`SELECT * FROM locals_img WHERE id = ${id}`, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(err, null);
      return;
    }

    if (res.length) {
      console.log("found local image: ", res[0]);
      result(null, res[0]);
      return;
    }

    result({ kind: "not_found" }, null);
  });
};

LocalImg.getAll = (result) => {
  sql.query("SELECT * FROM locals_img", (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    console.log("local images: ", res);
    result(null, res);
  });
};

LocalImg.updateById = (id, localImg, result) => {
  sql.query(
    "UPDATE locals_img SET local_id = ?, image_url = ? WHERE id = ?",
    [localImg.local_id, localImg.image_url, id],
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

      console.log("updated local image: ", { id: id, ...localImg });
      result(null, { id: id, ...localImg });
    }
  );
};

LocalImg.remove = (id, result) => {
  sql.query("DELETE FROM locals_img WHERE id = ?", id, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    if (res.affectedRows == 0) {
      result({ kind: "not_found" }, null);
      return;
    }

    console.log("deleted local image with id: ", id);
    result(null, res);
  });
};

LocalImg.removeAll = (result) => {
  sql.query("DELETE FROM locals_img", (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    console.log(`deleted ${res.affectedRows} local images`);
    result(null, res);
  });
};

module.exports = LocalImg;
