import React, { useState,useRef,useEffect, useContext } from "react";
import { Transition } from "@headlessui/react";
import styles from './Burger.module.css'
import Dashboard from "../Pages/Dashboard";
import { Routes, Route, Link } from "react-router-dom";
import {userContext} from "../App"

function Burger(){
  const ref = useRef();

  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const {setCurrentUser, setUserToken} = useContext(userContext);
  const {currentUser, setUserCurrent} = useContext(userContext);
  useEffect(() => {
      const checkIfClickedOutside = (e) => {
    // If the menu is open and the clicked target is not within the menu,
    // then close the menu
    if (isMenuOpen && ref.current && !ref.current.contains(e.target)) {
      setIsMenuOpen(false);
    }
  };


  document.addEventListener("click", checkIfClickedOutside);

  return () => {
    // Cleanup the event listener
    document.removeEventListener("click", checkIfClickedOutside);
  };
}, [isMenuOpen]); 

  useEffect(() => {

  }, [currentUser]);
  

    const logout = () => {
        localStorage.removeItem("accessToken");
        setCurrentUser(null);
        setUserToken("");
    }


    return(
      <>
          <button
            onClick={()=>setIsMenuOpen((oldState)=>!oldState)}
            type="button"
            className="flex items-center transition rounded-lg group shrink-0"
          >
            {currentUser?.avatar?.length ? <img className="object-cover w-10 h-10 rounded-full" src={currentUser?.avatar}/> : <img className="h-10 w-10 object-cover rounded-full" src="https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png?20150327203541" alt="user"/>}
          
            

          <p className="hidden ml-2 text-xs text-left sm:block">
            <strong className="block font-medium">{currentUser?.firstName + " " + currentUser?.lastName}</strong>

            <span className="text-gray-500">{currentUser?.email}</span>
          </p>



         

          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="hidden w-5 h-5 ml-4 text-gray-500 transition sm:block group-hover:text-gray-700"
            viewBox="0 0 20 20"
            fill="currentColor"
          >
            <path
              fillRule="evenodd"
              d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
              clipRule="evenodd"
            />
          </svg>
        </button>


        
      <Transition 
          show={isMenuOpen}
          enter="transition ease-out duration-100 transform"
          enterFrom="opacity-0 scale-95"
          enterTo="opacity-100 scale-100"
          leave="transition ease-in duration-75 transform"
          leaveFrom="opacity-100 scale-100"
          leaveTo="opacity-0 scale-95"
        >
          {isMenuOpen && (
            <div className={["w-80 bg-gray-100 absolute -right-8 sm:-right-8 top-8",styles.burgbox].join(' ')} id="mobile-menu">
              <div ref={ref} className="pr-5 pl-5 pt-2 pb-3 text-center">
                <Link className="text-black hover:bg-white  block px-3 py-2 rounded-md text-base font-medium" to="/dashboard">Dashboard</Link>

                <Link className="text-black hover:bg-white  block px-3 py-2 rounded-md text-base font-medium" to={`/profile/${currentUser?.email}`}>Profile</Link>

                {(currentUser && (currentUser?.role.permission.name != "ROLE_EMPLOYEE")) && 
                <Link className="text-black hover:bg-white  block px-3 py-2 rounded-md text-base font-medium" to="/users">Users</Link>                
                }


                <button onClick={logout} className={["hover:bg-white hover:text-black shadow-xl rounded-md",styles.logoutbtn].join(' ')}>LOG OUT</button>
                
              </div>
            </div>
          )}
        </Transition>
      </>


     
        
    )


}
export default Burger;
