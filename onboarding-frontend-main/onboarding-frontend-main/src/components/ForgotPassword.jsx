import React from 'react';
import { useNavigate} from 'react-router-dom';
export default function ForgotPassword()
{
  const navigate=useNavigate();
    return(
      <div className='text-center text-lg mt-4  '>
      <button type='button' className='w-full py-2 mt-4  bg-black hover:bg-black/90 relative text-white rounded-xl '
      onClick={()=>navigate("/forgotpassword")}>Forgot Password?</button>
      </div>
    )
}