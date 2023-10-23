import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'

//Configuring router
import{createBrowserRouter, RouterProvider} from 'react-router-dom';

//Routes
import Login from './routes/Login.tsx';
import Register from './routes/Register.tsx';
import Explore from './routes/Explore.tsx';
import CustomPackage from './routes/CustomPackage.tsx';
import Checkout from './routes/Checkout.tsx';
import Configuration from './routes/Configuration.tsx';
import MyPackages from './routes/MyPackages.tsx';
import PackageManagement from './routes/PackageManagement.tsx';
import Authentication from './auth.tsx';

const auth = new Authentication()

//Defining routes
const router = createBrowserRouter([
  {
    path:"/",
    element: <App auth={auth}/>,
    children:[
      {
        path:"/",
        element: <Explore/>,
      },
      {
        path:"/custompackage",
        element: <CustomPackage/>,
      },
      {
        path:"/checkout",
        element: <Checkout/>,
      },
      {
        path:"/configuration",
        element: <Configuration/>,
      },
      {
        path:"/mypackages",
        element: <MyPackages/>,
      },
      {
        path:"/packagemanagement",
        element: <PackageManagement/>,
      },


    ]
  },
  {
    path:"/login",
    element: <Login auth={auth}/>,
  },
  {
    path:"/register",
    element: <Register/>,
  },

])

ReactDOM.createRoot(document.getElementById('root')!).render(
    <RouterProvider router={router} />,
)
