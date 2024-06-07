const {
  addDoc,
  collection,
  query,
  where,
  getDocs,
} = require("firebase/firestore");
const { db } = require("../config/firebase");
const bcrypt = require("bcrypt");

const createUser = async (nama, email, password) => {
  const user = {
    nama,
    email,
    password,
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  const saved = await addDoc(collection(db, "users"), user);
  return { id: saved.id, ...user };
};

const loginUser = async (email, password) => {
  const q = query(collection(db, "users"), where("email", "==", email));
  const snapshot = await getDocs(q);

  const user = snapshot.docs.map((user) => {
    return { id: user.id, ...user.data() };
  })[0];

  // check if user not registered
  if (!user || !(await bcrypt.compare(password, user.password))) {
    return null;
  }

  return user;
};

module.exports = {
  createUser,
  loginUser,
};
