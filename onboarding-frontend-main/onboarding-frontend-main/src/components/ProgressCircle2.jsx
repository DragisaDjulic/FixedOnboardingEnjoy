import { CircularProgressbar } from 'react-circular-progressbar';
import 'react-circular-progressbar/dist/styles.css';
import styles from './Card.module.css'


function ProgressCircle({proginfo}) {

    // console.log(proginfo);
    const arrCompleted = proginfo?.filter(t => t?.completed != false);
    // console.log(arrCompleted);
    const percentage = Math.round((arrCompleted?.length * 100)/proginfo?.length);
    // console.log(percentage);
    return(
        <div className={["flex self-center mt-5 mb-10",styles.progcirc].join(' ')}>
        <CircularProgressbar value={percentage} text={`${percentage}%`} 
        styles={{
            // Customize the root svg element
            root: {
                
            },
            // Customize the path, i.e. the "completed progress"
            path: {
              // Path color
              stroke: `rgba(18, 163, 146, ${percentage / 100})`,
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