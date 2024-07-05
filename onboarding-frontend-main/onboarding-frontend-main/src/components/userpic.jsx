import React, { useState } from 'react'
import { AiFillEdit } from 'react-icons/ai';
import ProfileImage from "../components/ProfileImage"
import ImageUploading from "react-images-uploading";
import {RiImageEditFill} from 'react-icons/ri'
import { useEffect } from 'react';
import '../index.css'

function Userpic({onUpdate, profpic}) {
  const [images, setImages] = useState(profpic);
  const [errors, setErrors] = useState(null);
  console.log(profpic);
  const maxNumber = 69;
  const onChange = (imageList, addUpdateIndex) => {
    // data for submit
      setImages(imageList);
      onUpdate(imageList[0].data_url);

  };
  const hasError = (errors) => {
    setErrors(errors);
  }

  console.log(profpic);
  console.log(errors);


  return (
    <>
      <ImageUploading
        value={images}
        onChange={onChange}
        onError={hasError}
        maxNumber={maxNumber}
        dataURLKey="data_url"
        acceptType={["jpg"]}
        resolutionType="ratio"
        maxFileSize="200000"

      >
        {({
          imageList,
          onImageUpload,
          onImageRemoveAll,
          onImageUpdate,
          onImageRemove,
          isDragging,
          dragProps,
          errors
        }) => {
          return (
          <>
            <div className="relative md:-right-8 xl:-right-8 ">
              <button className='text-white'
                style={isDragging ? { color: "red" } : null}
                onClick={() => {
                  setImages(null);
                  onImageUpload();
                }}
                {...dragProps}
              >
              <RiImageEditFill size="25px"/>
              </button>

                  


            </div>               
          </>

        )}}
      </ImageUploading>
      {profpic?.length ? <img src={profpic}  className='object-cover rounded-full w-52 h-52 md:mr-10'/> : <img className='object-cover rounded-full w-52 h-52 md:mr-10' src="https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png?20150327203541"/>}
      {errors && 
        (
          <div className="modal text-black">
            <div onClick={() => setErrors(null)} className="overlay"></div>
            <div className="modal-content">
              {errors.acceptType && <h2 className='mb-2 mt-2'>You have to choose image in .jpg</h2>}
              {errors.maxFileSize && <h2>The image you chose exceeds maximum size of 500kb</h2>}
                <button className='mt-5 px-5 p-2 border-solid border-black border-2' onClick={() => setErrors(null)}>OK</button>
            </div>
        </div>
        )
      }
    </>
  )
}


export default Userpic