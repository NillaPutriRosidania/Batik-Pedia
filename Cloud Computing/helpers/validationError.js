const validationError = (errorArray) => {
  console.log(errorArray);
  const errors = {};

  for (let error of errorArray) {
    errors[error.path] = error.msg;
  }

  return errors;
};

module.exports = { validationError };
