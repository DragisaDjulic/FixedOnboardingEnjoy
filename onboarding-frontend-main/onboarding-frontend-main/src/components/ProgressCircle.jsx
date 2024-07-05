import { CircularProgressbar } from 'react-circular-progressbar';
import 'react-circular-progressbar/dist/styles.css';
import styles from './Card.module.css'
import { useContext } from "react";
import { userContext } from "../App";

function ProgressCircle({perc}) {
  const {currentUser, setUserCurrent} = useContext(userContext);
    const arrCompleted = perc.tasks?.filter(t => t?.completed != false);
    const percentage = Math.round((arrCompleted.length * 100)/perc.tasks?.length);
    return(
        <div className={["flex self-center mt-8 mb-14",styles.progcirc].join(' ')}>
        <CircularProgressbar value={percentage} text={`${percentage}%`} 
        styles={{
            // Customize the root svg element
            root: {

            },
            // Customize the path, i.e. the "completed progress"
            path: {
              // Path color
              stroke: `rgba(0,0,0, ${percentage / 100})`,
              strokeLinecap: 'butt',
              

            },
            // Customize the circle behind the path, i.e. the "total progress"
            trail: {
              // Trail color
              stroke: '#d6d6d6',
              

            },
            // Customize the text
            text: {
              // Text color
              fill: 'black',
              // Text size
              fontSize: '16px',
            },

          }}
        />
        </div>


    )
}

export default ProgressCircle;