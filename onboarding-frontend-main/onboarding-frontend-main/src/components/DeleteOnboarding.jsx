import styles from './Card.module.css'
import { Routes, Route, Link } from "react-router-dom";
import {userContext} from "../App"
import {useState,useEffect,useContext} from 'react'
import {deleteOnboarding} from '../service/onboardingApi';
import {AiFillDelete} from 'react-icons/ai'
import '../index.css'
import {Avatar, AvatarGroup} from '@mui/material'
import NotificationAlertType from './NotificationAlertType';
import Popup from './Popup';


function DeleteOnboarding({cardd, onDeletee}) {
    const {onboardings, setOnboardings} = useContext(userContext);
    const [modal, setModal] = useState(false);
    const[buttonPopup,setButtonPopup] = useState(false);

    const[notificationPopUp, setNotificationPopUp] = useState({
        visible: "hidden",
        type: '',
        text: '',
        changesCounter: 0
    });
    const toggleModal = () => {
      setModal(!modal);
    };

    // const deleteCard = () => {
    //     deleteOnboarding(cardd.id).then(res => {
    //         // console.log(res);
    //         setOnboardings(onboardings.filter(onboarding => onboarding.id !== cardd.id));

    //     }).catch(err => console.log(err));

    //     setNotificationPopUp(prev => ({...prev,
    //         visible: "visible",
    //         type: "delete",
    //         text: "Onboarding deleted successfully",
    //         timed: true
    
    //     }));

    // }  
  return (
    <>
        {modal && (
            <div className="modal text-black">
                <div onClick={toggleModal} className="overlay"></div>
                <div className="modal-content">
                    <h2>ARE YOU SURE YOU WANT TO DELETE THIS ONBOARDING?</h2>
                    <div className='mt-10 flex flex-row space-x-8'>
                    <button onClick={() => onDeletee(cardd.id)} className="text-red-600">
                        DELETE    
                    </button>
                    <button className="close-modal" onClick={toggleModal}>
                        CLOSE
                    </button>
                    </div>

                </div>
            </div>
        )}
        <button onClick={toggleModal}  className='block ml-3 hover:text-red-500'>
        <AiFillDelete
            size="25px"   
        />
        </button>
    </>
  )
}

export default DeleteOnboarding
