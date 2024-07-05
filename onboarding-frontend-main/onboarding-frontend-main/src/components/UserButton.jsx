import React from 'react'

function Button({txt, type, submit}) {
  return (
    <button onClick={submit} type={type} className='mt-5 bg-gray-400 p-3 rounded-xl text-white w-[100%]
                        duration-300'>{txt}</button>
  )
}

export default Button