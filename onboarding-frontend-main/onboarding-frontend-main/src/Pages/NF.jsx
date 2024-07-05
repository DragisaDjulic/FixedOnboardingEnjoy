import React from 'react'
import ErrorComponent from '../components/ErrorComponent'
import BackErrorImg from "../images/Back_404.png"


function NF() {

  return (
    <div>
      <ErrorComponent errorImg={BackErrorImg} errorCode={"4 0 4"} errorMsg={"Seems you lost your way..."} />
    </div>
  )
}

export default NF