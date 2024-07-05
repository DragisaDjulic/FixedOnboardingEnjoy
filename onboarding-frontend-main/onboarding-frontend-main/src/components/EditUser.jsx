import {AiFillEdit} from 'react-icons/ai'
import {AiFillDelete} from 'react-icons/ai'

function EditUser(){
    return(
        <div className="flex w-full flex-row group-hover:h-16 bg-teal h-0 mt-12 -mb-5 rounded-t-xl opacity-75 justify-center">
            <button className='hidden group-hover:block mr-3'>
                <AiFillEdit
                    size="25px"
                />
            </button>
            <button  className='hidden group-hover:block ml-3'>
                <AiFillDelete
                    size="25px"
                />
            </button>
            
        </div>
    );
}
export default EditUser;