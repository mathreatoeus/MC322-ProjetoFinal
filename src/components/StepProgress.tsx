import Step from "./Step";

export default function StepProgress(){
    return(
        <div className="stepper-wrapper">
            <Step
                position="1"
                label="Room"
                state="completed"
            />
            <Step
                position="2"
                label="Flight"
                state="active"
            />
            <Step
                position="3"
                label="Extras"
                state="item"
            />
            <Step
                position="4"
                label="Checkout"
                state="item"
            />
        </div>

    );
}