export default function Step(props:{position:string, label:string, state:string}){
    
    var class_name = "stepper-item "+ props.state
    return( 
        <div className = {class_name}>
            <div className="step-counter">{props.position}</div>
            <div className="step-name">{props.label}</div>
        </div>
    );
}