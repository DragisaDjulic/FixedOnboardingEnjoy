import React from 'react';
import {useNavigate} from 'react-router-dom';

export default function ErrorComponent({errorImg,errorCode,errorMsg})
{      
    const navigate=useNavigate();

    return(

        <div className="flex justify-center items-center w-full h-screen bg-black/90">

            <img className="absolute w-2/4 object-cover rounded-xl" src={errorImg} alt="/"></img>
            <div  className="relative flex justify-center h-50">

                        <div className="max-w-[350px] w-full mx-auto bg-white/80 p-8 rounded-xl z-0">
                            <h2 className='text-center  text-red-600 font-mono text-6xl mt-8 mb-8 '>{errorCode} </h2>
                            <h3 className='text-center text-red-600 font-mono text-2xl'>{errorMsg}</h3>
                            
                            <button className='w-full py-2 mt-8  bg-black hover:bg-black/80 relative text-white rounded-xl'
                             type='button' onClick={()=>{navigate(-1)}}>Go Back</button>
                            
                        </div>
            </div>
            
        </div>
    )
}