export default function Register () {
    return(
        <div className="loginContainer">
            <div className="minorloginContainer">
                <div className="loginTitle">Registration form</div>
                <div className="loginItem">
                    <span className="loginName">User</span>
                    <input className="loginInputName"></input>
                </div>
                <div className="loginItem">
                    <span className="loginName">Password</span>
                    <input className="loginInputName"></input>
                </div>
                <button className="loginButton">Register</button>

            </div>
        </div>
    );
}