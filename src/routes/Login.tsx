import Button from "../components/Button";

export default function Login () {
    return(
        <div className="loginContainer">
            <div className="minorloginContainer">
                <div className="loginTitle">Login</div>
                <div className="loginItem">
                    <span className="loginName">User</span>
                    <input className="loginInputName"></input>
                </div>
                <div className="loginItem">
                    <span className="loginName">Password</span>
                    <input className="loginInputName"></input>
                </div>
                <a className="signup" href="/">sign up for free!</a>
                <button className="loginButton">Enter</button>
                


            </div>
        </div>
    );
}