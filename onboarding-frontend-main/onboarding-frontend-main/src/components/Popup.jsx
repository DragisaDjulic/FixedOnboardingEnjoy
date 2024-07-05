import React from 'react'
import styles from "../components/Popup.module.css"
import {AiFillCloseCircle} from "react-icons/ai"

function Popup(props) {
  return (props.trigger) ? (
    <div className={["",styles.popup].join(' ')}>
        <div className={["h-full",styles.popupinner].join(' ')}>            
            <AiFillCloseCircle size="40px" cursor="pointer"
                    color='rgba(0,0,0)' className={['',styles.closebtn].join(' ')} onClick={()=>props.setTrigger(false)}/>
            {props.children}
        </div>
    </div>
  ) : "";
}

export default Popup
