import React from 'react';
import { useNavigate } from 'react-router-dom';
import imgProfile from '../images/profile.jpg'
export default function NotificationPanel()

{   
    
    const navigate=useNavigate();

    return(
        <div className="flex justify-center items-center w-full">
            <div  className="justify-center max-w-[1000px]  w-full "  >
                <div className=" w-full  mx-auto  bg-black  p-7 mt-8 rounded-xl z-0 text-white text-center">
                     <h2 className='text-center text-white font-semibold text-xl mt-8 mb-8 '>Notification Panel</h2> {/*heading */}
                        <div className='overflow-y-auto m-2 p-4 max-h-[575px] border-white border-2 rounded-lg '>{/*overflow/scroll containter */}
                    
                            <div className='bg-white rounded-xl text-center text-black font-sans w-full max-h-[100px] h-full mb-3 mt-3 '>{/* content containter*/}
                                    <div className='flex items-center p-2 ml-4'>{/* content*/ }
                                        <div className='flex shrink-0'>{/*profile image containter */}
                                            <img className=" h-12 w-12 mt-2 rounded-full" src={imgProfile} alt='/'  />
                                            
                                        </div>
                                    Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eos suscipit dicta labore voluptatem eligendi ullam explicabo harum aperiam? 
                                Labore rem doloremque quod ipsum repudiandae amet asperiores aliquam quam laboriosam dicta.
                                    </div>
                                
                                </div>
                                <div className='bg-white rounded-xl text-center text-black font-sans w-full max-h-[100px] h-full mb-3 mt-3 '>
                                    <div className='flex items-center p-2 ml-4'>
                                        <div className='flex shrink-0'>
                                            <img className=" h-12 w-12 mt-2 rounded-full" src={imgProfile} alt='/'  />
                                            
                                        </div>
                                    Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eos suscipit dicta labore voluptatem eligendi ullam explicabo harum aperiam? 
                                Labore rem doloremque quod ipsum repudiandae amet asperiores aliquam quam laboriosam dicta.
                                    </div>
                                
                                </div>
                                <div className='bg-white rounded-xl text-center text-black font-sans w-full max-h-[100px] h-full mb-3 mt-3 '>
                                    <div className='flex items-center p-2 ml-4'>
                                        <div className='flex shrink-0'>
                                            <img className=" h-12 w-12 mt-2 rounded-full" src={imgProfile} alt='/'  />
                                            
                                        </div>
                                    Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eos suscipit dicta labore voluptatem eligendi ullam explicabo harum aperiam? 
                                Labore rem doloremque quod ipsum repudiandae amet asperiores aliquam quam laboriosam dicta.
                                    </div>
                                
                                </div>
                                <div className='bg-white rounded-xl text-center text-black font-sans w-full max-h-[100px] h-full mb-3 mt-3 '>
                                    <div className='flex items-center p-2 ml-4'>
                                        <div className='flex shrink-0'>
                                            <img className=" h-12 w-12 mt-2 rounded-full" src={imgProfile} alt='/'  />
                                            
                                        </div>
                                    Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eos suscipit dicta labore voluptatem eligendi ullam explicabo harum aperiam? 
                                Labore rem doloremque quod ipsum repudiandae amet asperiores aliquam quam laboriosam dicta.
                                    </div>
                                
                                </div>
                                <div className='bg-white rounded-xl text-center text-black font-sans w-full max-h-[100px] h-full mb-3 mt-3 '>
                                    <div className='flex items-center p-2 ml-4'>
                                        <div className='flex shrink-0'>
                                            <img className=" h-12 w-12 mt-2 rounded-full" src={imgProfile} alt='/'  />
                                            
                                        </div>
                                    Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eos suscipit dicta labore voluptatem eligendi ullam explicabo harum aperiam? 
                                Labore rem doloremque quod ipsum repudiandae amet asperiores aliquam quam laboriosam dicta.
                                    </div>
                                
                                </div>
                                <div className='bg-white rounded-xl text-center text-black font-sans w-full max-h-[100px] h-full mb-3 mt-3 '>
                                    <div className='flex items-center p-2 ml-4'>
                                        <div className='flex shrink-0'>
                                            <img className=" h-12 w-12 mt-2 rounded-full" src={imgProfile} alt='/'  />
                                            
                                        </div>
                                    Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eos suscipit dicta labore voluptatem eligendi ullam explicabo harum aperiam? 
                                Labore rem doloremque quod ipsum repudiandae amet asperiores aliquam quam laboriosam dicta.
                                    </div>
                                
                                </div>
                                <div className='bg-white rounded-xl text-center text-black font-sans w-full max-h-[100px] h-full mb-3 mt-3 '>
                                    <div className='flex items-center p-2 ml-4'>
                                        <div className='flex shrink-0'>
                                            <img className=" h-12 w-12 mt-2 rounded-full" src={imgProfile} alt='/'  />
                                            
                                        </div>
                                    Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eos suscipit dicta labore voluptatem eligendi ullam explicabo harum aperiam? 
                                Labore rem doloremque quod ipsum repudiandae amet asperiores aliquam quam laboriosam dicta.
                                    </div>
                                
                                </div>
                                <div className='bg-white rounded-xl text-center text-black font-sans w-full max-h-[100px] h-full mb-3 mt-3 '>
                                    <div className='flex items-center p-2 ml-4'>
                                        <div className='flex shrink-0'>
                                            <img className=" h-12 w-12 mt-2 rounded-full" src={imgProfile} alt='/'  />
                                            
                                        </div>
                                    Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eos suscipit dicta labore voluptatem eligendi ullam explicabo harum aperiam? 
                                Labore rem doloremque quod ipsum repudiandae amet asperiores aliquam quam laboriosam dicta.
                                    </div>
                                
                                </div>
                                
                                

                        </div>
                        <button className=' w-[15%] py-2 mt-3 bg-slate-700 hover:bg-slate-800 relative text-white rounded-xl ' type='button' onClick={()=>{navigate(-1)}}>Go Back</button>
                </div>

            </div>
        </div>
    )
}