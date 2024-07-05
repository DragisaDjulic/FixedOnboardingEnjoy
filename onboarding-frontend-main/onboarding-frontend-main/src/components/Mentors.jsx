import React from 'react'
import {Avatar, AvatarGroup} from '@mui/material'
import { Transition } from "@headlessui/react";
import ModAvatar from '../components/ModAvatar'
import { useState,useEffect, useContext, useRef } from 'react'
import styles from "../components/Task.module.css"
import {userContext} from "../App"


function Mentors({moderators, ment, mentPermission}) {
    const {currentUser} = useContext(userContext);
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const ref = useRef();
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
  return (
    <>
        <button
            onClick={()=>setIsMenuOpen((oldState)=>!oldState)}
            className='flex justify-center items-center
                        sm-w-full'>
            
            <AvatarGroup max={4} >
            {((currentUser && (currentUser?.role.permission.name == "ROLE_ADMIN")) || mentPermission == true) && 
                moderators?.map(e =>{
                    let firstname = e?.firstName?.charAt(0);
                    let lastname = e?.lastName?.charAt(0);
                    return(

                        <Avatar key={e.id} src={e.avatar}>{firstname + "" + lastname}</Avatar>

                        // <Avatar alt={`${e.firstName} ${e.lastName}`} src="/static/images/avatar/3.jpg" />
                    )
            })}  
            {(currentUser && (currentUser?.role.permission.name != "ROLE_ADMIN") && mentPermission == false) && 
                ment?.map(e =>{
                    let firstname = e?.firstName?.charAt(0);
                    let lastname = e?.lastName?.charAt(0);
                    return(
                        <Avatar key={e.id}  src={e.avatar}>{firstname + "" + lastname}</Avatar>
                    )
                })
            }                                    
            


            </AvatarGroup>        
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
                        <div className={["w-64 bg-gray-100 border-2 border-gray-200 absolute -right-16 top-16", styles.notifbox].join(' ')} id="mobile-menu">
                            <div ref={ref} className=" pt-3 pl-5 pr-5 pb-3 text-center">
                                {((currentUser && (currentUser?.role.permission.name == "ROLE_ADMIN")) || mentPermission == true) && 
                                    moderators.map((e)=>{
                                        return(
                                        <ModAvatar name={e}/>
                                        )
                                    })
                                }
                                {(currentUser && (currentUser?.role.permission.name != "ROLE_ADMIN") && mentPermission == false) && 
                                    ment?.map((e)=>{
                                        return(

                                        <ModAvatar name={e}/>

                                        )
                                    })
                                }

                            </div>
                        </div>
                    )}
        </Transition>      
    </>
  )
}

export default Mentors
