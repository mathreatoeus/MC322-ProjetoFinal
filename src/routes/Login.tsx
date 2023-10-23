import { useNavigate } from 'react-router-dom';
import Authentication from '../auth.tsx'
import {useRef} from 'react'

export default function Login (props:{auth:Authentication}) {
    const auth = props.auth;
    const inputRef = useRef(null);
    const inputRef2 = useRef(null);
    const navigate = useNavigate();

    return(
        <div className="loginContainer">
            <div className="minorloginContainer">
                <div className="loginTitle">Login</div>
                <div className="loginItem">
                    <span className="loginName">User</span>
                    <input ref={inputRef} className="loginInputName"></input>
                </div>
                <div className="loginItem">
                    <span className="loginName">Password</span>
                    <input ref={inputRef2} className="loginInputName"></input>
                </div>
                <a className="signup" href="/Register">sign up for free!</a>
                <button className="loginButton" onClick={()=>{ 
                            if (inputRef.current.value != null && inputRef2.current.value != null){
                                auth.login(inputRef.current.value, inputRef2.current.value)
                                return navigate("/")

                            } else{
                                console.log(inputRef.current.value, inputRef2.current.value)
                            }
                        }
                    }>
                        Enter
                </button>

            </div>
        </div>
    );
}