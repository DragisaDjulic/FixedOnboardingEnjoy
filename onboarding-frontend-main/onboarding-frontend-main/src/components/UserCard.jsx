import styles from './Card.module.css'
import { Routes, Route, Link } from "react-router-dom";



function UserCard({user}){
    return(
        
        <div className={["p-5 items-center flex flex-col text-white  hover:border-dashed hover:shadow-lg group mr-5",styles.usercard].join(' ')}>
            <div className='mb-8 mt-4'>
                {user?.avatar?.length ? <img className="object-cover h-28 w-28 rounded-full" src={user?.avatar} alt='user'/> : <img className="h-28 w-28 rounded-full" src="https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png?20150327203541" alt='user'/>}

            </div>
            <div className='mb-2 text-xl text-center'>
                <p>{user?.firstName} {user?.lastName}</p>
                <p>{user?.role.name}</p>
            </div>
            <div className=''>
                <p>Curently on {user?.onboardings.length} onboardings</p>
            </div>
        </div>


    )
}

export default UserCard;