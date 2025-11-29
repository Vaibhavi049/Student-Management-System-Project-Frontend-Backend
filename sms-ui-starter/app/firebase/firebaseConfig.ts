// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "REPLACE_WITH_YOUR_KEY",
  authDomain: "rcoem-sms-f3d44.firebaseapp.com",
  projectId: "rcoem-sms-f3d44",
  storageBucket: "rcoem-sms-f3d44.firebasestorage.app",
  messagingSenderId: "130973429833",
  appId: "1:130973429833:web:fcfe51a10d8b00e7fd4f98",
  measurementId: "G-L52WGR37PZ"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

const auth = getAuth(app);
// ✅ Export auth properly
export { auth, app };
