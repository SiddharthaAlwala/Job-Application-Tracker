import { BrowserRouter as Router, Routes, Route, Navigate} from 'react-router-dom';
import JobApplication from './components/JobApplication';
import Login from './components/Login';
import ProtectedRoute from './services/ProtectedRoute';
import Home from './components/Home';


function App() {
  return (
    
    <Router>
      <Routes>
        {/* {REdirect root to login} */}
        <Route path = "/" element = {<Navigate to= "/login" replace/>}/>
        <Route path="/login" element = {<Login/>}/>
        {/* {Protected Routes} */}
        <Route
          path="/home"
          element={
            <ProtectedRoute>
              <Home/>
            </ProtectedRoute>
          }
          />
          <Route
          path="/create-application"
          element={
            <ProtectedRoute>
              <CreateApplication/>
            </ProtectedRoute>
          }
          />
          <Route
          path="/applications"
          element={
            <ProtectedRoute>
              <ApplicationsList/>
            </ProtectedRoute>
          }
          />
        
      </Routes>
    </Router>
    
  );
}

export default App;
