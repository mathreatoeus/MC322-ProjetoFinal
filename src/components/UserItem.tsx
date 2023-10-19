export default function UserItem (props:{name:string, pic:string}) {
    return(
            <a className = "userContainer">
                <div className="userName">
                    Olá, {props.name}
                </div>

                <div className="userPicture">
                    <img src={props.pic} height ="50px"  width="50px"></img>

                </div>

            </a>

    );
}