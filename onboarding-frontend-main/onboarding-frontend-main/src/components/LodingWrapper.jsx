import React from 'react';
import LoadingSpinner from './LoadingSpinner';
import BackroundgImage from '../images/bglog.png'
const LodingWrapper = ({loading}) => {
    return ( 
        <div className="relative w-full h-screen  bg-zinc-500/10" style={{backgroundImage:`url(${BackroundgImage})`}} >
            <div className=" flex justify-center items-center h-full">
                <div className="max-w-[400px] min-h-[500px] w-full mx-auto  bg-white p-8 rounded-xl z-0  ease-in-out duration-300">
                   { loading  && <div className='mt-8 ease-in-out  duration-300'><LoadingSpinner/></div>}{/**only show the spiner if bouth statments are true */}    
                </div>
            </div>
        </div>                    
     );
}
export default LodingWrapper;