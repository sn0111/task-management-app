import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import "bootstrap/dist/js/bootstrap.bundle.min";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Login } from './components/login';
import { Home } from './components/home';
import { Users } from './components/users';

function App() {
  return (
    <Router>
        <Routes>
          <Route path="/" element={<Login/>} />
          <Route path="/home" element={<Home/>} />
          <Route path="/users" element={<Users/>} />
          {/* <Route path="/about" component={About} />
          <Route path="/tasks" component={Tasks} /> */}
        </Routes>
    </Router>
  );
}

export default App;
