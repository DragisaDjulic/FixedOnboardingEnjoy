import { Routes, Route, Link, useLocation } from "react-router-dom";
import { Navigate } from "react-router";
import RoutesCont from './RoutesCont';
import { getUsers, getCurrentUser } from '../src/service/userApi';
import {useState,useEffect} from 'react'
import React from 'react'
import { getOnboardings } from "./service/onboardingApi";
import axios from "axios";
import { getNotifications } from "./service/notificationsApi";

export const userContext = React.createContext();


function App() {
  const[users, setUsers] = useState([]);
  const[newNotifications, setNewNotifications] = useState([]);

  const [userToken, setUserToken] = useState(localStorage.getItem("accessToken") || "");
  const [currentUser, setCurrentUser] = useState(null);
  const [isLoading, setIsLoading] = useState(false);


  useEffect(() => {
    if(userToken){
      axios.defaults.headers.common = {
        "Authorization": `Bearer ${userToken}`,
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*"
      }
      getCurrentUser()
        .then(res => {
          setCurrentUser(res);
        })
        .catch(err => {
          if(err.response.status == 401) {
            localStorage.removeItem("accessToken");
            setCurrentUser(null);
            setUserToken("");
          }
        });
    }
  }, [userToken])



    useEffect(()=>{
      if(currentUser && (currentUser?.role.permission.name != "ROLE_EMPLOYEE"))
        getUsers().then(res => setUsers(res));
  },[currentUser])

  useEffect(()=> {
    getNotifications().then(res => setNewNotifications(res));
  },[currentUser])
  
  // console.log(users);

  const[onboardings, setOnboardings] = useState([]);

  const loc = useLocation();

  useEffect(()=>{
    if(userToken)
      getOnboardings().then(res => setOnboardings(res));
  },[userToken])

  // console.log(onboardings);

  // console.log(loc.pathname);

  const outRoutes = [/^\/$/, /\/forgotpassword/, /\/reset\//];

  let cond = outRoutes.reduce((a,b) => a || b.test(loc.pathname), false);
  
  // console.log(cond);

  return (
    <userContext.Provider  value={{users, setUsers, onboardings, setOnboardings, userToken, setUserToken, currentUser, setCurrentUser, newNotifications, setNewNotifications}}>
      <div className="App">
        <RoutesCont />
        {/* {(!userToken && (loc.pathname != "/" && loc.pathname != "/forgotpassword")) && <Navigate to={"/"} />} */}
        {(!userToken && !cond) && <Navigate to={"/"} />}
        {(userToken && (loc.pathname == "/")) && <Navigate to={"/Dashboard"}/>}
      </div>
    </userContext.Provider>

  );
}

export default App;