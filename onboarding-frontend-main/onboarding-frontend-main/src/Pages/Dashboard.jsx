import React from 'react'
import Navbar from '../components/Navbar'
import Card from '../components/Card'
import OnBoardBtns from '../components/OnBoardBtns'
import { getOnboardings } from '../service/onboardingApi';
import {useState,useEffect,useContext} from 'react'
import {userContext} from "../App"
import { TextField, InputAdornment } from '@mui/material';
import styles from '../components/Dash.module.css'
import {AiOutlineSearch} from 'react-icons/ai'
import LoadingSpinner from "../components/LoadingSpinner";
import NotificationAlertType from '../components/NotificationAlertType';
import {deleteOnboarding} from '../service/onboardingApi';

function Dashboard() {
  const {onboardings, setOnboardings,  currentUser, setCurrentUser, userToken, setUserToken} = useContext(userContext);
  const [isLoading, setIsLoading] = useState(false);
  const[notificationPopUp, setNotificationPopUp] = useState({
    visible: "hidden",
    type: '',
    text: '',
    changesCounter: 0
});
  useEffect(()=>{
    setIsLoading(true);
    getOnboardings().then(res => { 
         setTimeout(() => {
          setOnboardings(res);
           setIsLoading(false);
         }, 500);
    })
    .catch(() => {
      setIsLoading(false);
   });
  },[userToken])

  const [selectedOnboardings, setSelectedOnboardings] = useState(onboardings);

  useEffect(()=>{
    setSelectedOnboardings(onboardings);
  },[onboardings])

  let inputHandler = (e) => {
    //convert input text to lower case
    var lowerCase = e.target.value.toLowerCase();
    if(!e.target.value){
      setSelectedOnboardings(onboardings);
    } else
    setSelectedOnboardings(onboardings.filter(so => so.name.toLowerCase().includes(`${lowerCase}`)));
  }; 


  const deleteCard = (id) => {
    deleteOnboarding(id).then(res => {
        setOnboardings(onboardings.filter(onboarding => onboarding.id !== id));

    }).catch(err => console.log(err));

    setNotificationPopUp(prev => ({...prev,
        visible: "visible",
        type: "delete",
        text: "Onboarding deleted successfully",
        timed: true

    }));

}  

  return (
    <>
      <Navbar/>
      <div className={["flex justify-center mt-3", styles.main].join(' ')}>
        <div className={["self-center relative z-[9]", styles.search].join(' ')}>
          <TextField 
            id="outlined-basic"
            onChange={inputHandler}
            variant="standard"
            fullWidth
            label="Search" 
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <AiOutlineSearch />
                </InputAdornment>
              ),
            }}      
          />
        </div>

      </div>
      <div className='flex justify-center'>
      {(currentUser && (currentUser?.role.permission.name != "ROLE_EMPLOYEE")) && <OnBoardBtns/>}

        <div className='flex flex-row flex-wrap mt-10 w-4/5 justify-center'>
        {isLoading ? <LoadingSpinner/> : selectedOnboardings?.map((e)=>{
          return (
          <Card key={e?.id} card={e} onDelete={deleteCard}/>
          );})}

        </div>

      </div>

      <NotificationAlertType bottomPosition={"bottom-24"} notificationPopUp={notificationPopUp} setNotificationPopUp={setNotificationPopUp}/>    

    </>

  )
}

export default Dashboard