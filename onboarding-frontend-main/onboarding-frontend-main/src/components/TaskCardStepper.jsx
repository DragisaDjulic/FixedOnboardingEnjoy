import React from 'react'
import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepButton from '@mui/material/StepButton';

function TaskCardStepper({tasks, curIndex, setCurIndex}) {


  return (
        <div className='mb-10 overflow-x-scroll overflow-y-hidden
                        md:overflow-x-scroll md:overflow-y-hidden md:w-[70%]
                        xl:overflow-visible'>
            <Box sx ={{width: '100%'}}>
                <Stepper nonLinear alternativeLabel activeStep={curIndex}
                sx={{
                    '& .MuiStepLabel-root .Mui-completed': {
                        color: '#15803D', // circle color (COMPLETED)
                        
                    },
                    '& .MuiStepLabel-label.Mui-completed.MuiStepLabel-alternativeLabel':
                        {
                        color: 'grey.500', // Just text label (COMPLETED)  

                        },
                    '& .MuiStepLabel-root .Mui-active': {
                        color: 'black', // circle color (ACTIVE)
                        
                    },
                    '& .MuiStepLabel-label.Mui-active.MuiStepLabel-alternativeLabel':
                        {
                        color: 'common.black', // Just text label (ACTIVE)
                        
                        },
                    '& .MuiStepLabel-root .Mui-active .MuiStepIcon-text': {
                        fill: 'white', // circle's number (ACTIVE)
                    },
                    "& .MuiStepConnector-root.Mui-active .MuiStepConnector-line": {
                        borderColor: "#15803D",
                        },
                        "& .MuiStepConnector-line": {
                        borderTopWidth: "3px",
                        },
                        "& .MuiStepConnector-root.Mui-completed .MuiStepConnector-line": {
                        borderColor: "#15803D",
                        },
                    }}
                >
                    {tasks?.sort((a, b) => a.serialNo - b.serialNo).map((task, index) => {
                        return (
                            <Step
                                    key={task.id} completed={task.completed}>
                                <StepButton onClick={() => setCurIndex(index)}>{task.name}</StepButton>
                            </Step>
                        )
                    })}
                </Stepper>

            </Box>
        </div>
  )
}

export default TaskCardStepper
