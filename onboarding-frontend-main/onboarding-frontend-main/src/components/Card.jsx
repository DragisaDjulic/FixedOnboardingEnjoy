import styles from './Card.module.css'
import OnboardName from './OnboardName';
import ProgressCircle from './ProgressCircle';
import { Link } from "react-router-dom";
import { userContext } from "../App"
import { useContext } from 'react'
import NewUsers from './NewUsers';
import EditOnboard from './EditOnboard';
import '../index.css'
import DeleteOnboarding from './DeleteOnboarding';


function Card({card, onDelete}){
    const {users, currentUser} = useContext(userContext);

    const addUsers = (newUsers) => {
        card.users.concat(newUsers.map(user => users.find(usr => usr.email == user)));
    }

    

    return(
        <>
         <div className={["flex flex-col p-5 text-white ml-7 mr-7 mb-10 hover:border-dashed hover:shadow-lg", styles.card].join(' ')}>
                    <Link to={`/onboarding/${card?.id}`}>
                    <div className='flex flex-col'>
                        <OnboardName name={card?.name}/>
                        {(currentUser && (currentUser?.role.permission.name != "ROLE_ADMIN" && card.isMentor != true)) &&  <ProgressCircle perc = {card}/>}
                        {(currentUser && (currentUser?.role.permission.name == "ROLE_ADMIN" || card.isMentor)) &&  
                        <div className={["flex self-center mt-8 mb-10 w-full", styles.description].join(' ')}>
                            <p className='line-clamp-5'>{card?.description}</p>
                        </div>
                                              
                       
                        }

                    
                    </div>

                    </Link>
                    {(currentUser && (currentUser?.role.permission.name == "ROLE_ADMIN" || card.isMentor)) && 
                    <div className="flex w-full flex-row h-10 bg-gray-100 mt-5 justify-center">
                    <EditOnboard onboard={card}/>
                    <NewUsers onboardingId={card?.id} onUserAdd={addUsers}/>
                    <DeleteOnboarding cardd={card} onDeletee={(id) => onDelete(id)}/>
                     


                    </div>
                    
                    }

               

               


            </div>        


        </>



    );



}

export default Card;