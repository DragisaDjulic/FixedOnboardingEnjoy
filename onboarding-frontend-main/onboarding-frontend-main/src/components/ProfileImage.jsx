import React from "react";
import ReactDOM from "react-dom";
import ImageUploading from "react-images-uploading";


function ProfileImage() {
  const [images, setImages] = React.useState([]);
  const maxNumber = 69;
  const onChange = (imageList, addUpdateIndex) => {
    // data for submit
    console.log(imageList, addUpdateIndex);
    setImages(imageList);
  };

  console.log(images);

  return (
      <ImageUploading
        value={images}
        onChange={onChange}
        maxNumber={maxNumber}
        dataURLKey="data_url"
        acceptType={["jpg"]}
        resolutionType="ratio"
      >
        {({
          imageList,
          onImageUpload,
          onImageRemoveAll,
          onImageUpdate,
          onImageRemove,
          isDragging,
          dragProps
        }) => (
          // write your building UI
          <div className="upload__image-wrapper">
            <button
              style={isDragging ? { color: "red" } : null}
              onClick={onImageUpload}
              {...dragProps}
            >
            Upload
            </button>

                <img src={images[0].data_url} alt="" width="100" height="100" />


          </div>
        )}
      </ImageUploading>
  );
}

export default ProfileImage
