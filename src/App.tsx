import './App.css';
import Header from './components/Header';
import {Navigate, Outlet} from 'react-router-dom';
import Authentication from './auth';


export default function App(props:{auth:Authentication}) {
  const auth = props.auth
  if(auth.getAuthentication()){
    return (
      <div className = "mainFrame">
        <Header/>
        <Outlet/>
      </div>
  );

  }else{
    return <Navigate 
              to={'/login'}
    />

  }
}

