import React from 'react'
import Navbar from '../components/Navbar'
import TaskCard from '../components/TaskCard'
import { useParams } from 'react-router'
import {useState,useEffect, useContext} from 'react'
import {userContext} from "../App"

function Onboardings() {

  const params = useParams();


  const {onboardings, setOnboardings} = useContext(userContext);

  // const onb = onboardings.find(onb => onb.id === params.id);



  return (
    <>
      <Navbar/>

      <div className='flex flex-col justify-center text-center relative
                      md:px-5
                      lg:px-5
                      xl:px-10'>
        <TaskCard/>
      </div>

    </>

  )
}

export default Onboardings