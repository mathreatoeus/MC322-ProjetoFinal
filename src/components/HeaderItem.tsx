export default function HeaderItem (props:{href:string, src:string, label:string}) {
    return(
        <li>
            <a className = "itemContainer"href={props.href}>
                <div className="itemIcon">
                    <img src={props.src} height ="40px"  width="40px"></img>

                </div>
                <div className="itemLabel">
                    {props.label}
                </div>
            </a>
        </li>

    );
}