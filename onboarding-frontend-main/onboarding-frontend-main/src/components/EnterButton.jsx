import styles from './Card.module.css'
import { Routes, Route, Link } from "react-router-dom";



function EnterButton(){
    return(
        <div className='flex self-center mt-7'>
            <Link to="/onboarding" className={['text-center hover:bg-svetloplava hover:text-white',styles.enterbtn].join(' ')}>
                Enter Onboarding
            </Link>
        </div>
    )
}

export default EnterButton;