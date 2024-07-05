import React from 'react'
import ErrorComponent from '../components/ErrorComponent'
import BackErrorDenied from "../images/Back_403.png"


function UPS() {
  return (
    <ErrorComponent errorImg={BackErrorDenied} errorCode={"4 0 3"} errorMsg={"Yikes! Looks like you don't have access!"}/>
  )
}

export default UPS