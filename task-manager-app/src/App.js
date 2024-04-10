import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import "bootstrap/dist/js/bootstrap.bundle.min";
import { Routes, Route, Navigate } from 'react-router-dom';
import { Login } from './components/login';
import { Home } from './components/home';
import { Users } from './components/users';
import { useContext } from 'react';
import { AuthContext } from './authContext';

function App() {
  const { isAuthenticated } = useContext(AuthContext);
  return (
    // <Router>
        <Routes>
          {!isAuthenticated && <Route path='/*' element={<Navigate to="/task-manager-app/login"/>}/>}
          {isAuthenticated && <Route path='/*' element={<Navigate to="/task-manager-app/home"/>}/>}
          {!isAuthenticated && <Route path="/task-manager-app/login" element={<Login/>} />}
          {isAuthenticated && <Route path="/task-manager-app/home" element={<Home/>} />}
          {isAuthenticated && <Route path="/task-manager-app/users" element={<Users/>} />}
          {/* <Route path="/about" component={About} />
          <Route path="/tasks" component={Tasks} /> */}
        </Routes>
    // </Router>
  );
}

export default App;
