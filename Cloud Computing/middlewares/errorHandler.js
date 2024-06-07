const errorHandler = (err, req, res, next) => {
  res
    .status(err.status || 500)
    .json({ status: "error", code: err.status || 500, message: err.message });
};

module.exports = errorHandler;
