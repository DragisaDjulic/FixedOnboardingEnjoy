import React, { useContext } from 'react'
import { useEffect } from 'react';
import { useState } from 'react'
import { userContext } from '../App';

function TaskHead({onbinfo}) {
const {onboardings, setOnboardings, currentUser} = useContext(userContext);

useEffect(()=>{

},[])



  return (
    <>
      <div className='flex items-center justify-center
                      md:mr-5'>

          <div className='flex flex-col w-11/12
                          md:w-full 
                          sm:w-3/4'>
              <div className='mt-10 flex flex-col space-x-3
                              mb-12'>
                      <h1 className='font-bold text-4xl mb-3 text-center
                                      xl:mb-10'>{onbinfo?.name}</h1>
                      <p className='flex text-left'>{onbinfo?.description}</p>
              </div>
                  
          </div>
      </div>

    </>

  )
}

export default TaskHead
