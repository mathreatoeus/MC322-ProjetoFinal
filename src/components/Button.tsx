export default function Button(props:{label:string}){
    return(
        <button className="button_step">
            {props.label}
        </button>

    );
}