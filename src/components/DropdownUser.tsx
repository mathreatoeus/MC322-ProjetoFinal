export default function DropdownUser(){
    return(
        <div className="dropdownUser">
            <ul className="dropdownContainer">
                <li>
                    <a href="/mypackages">
                        Meus pacotes
                    </a>
                </li>
                <li>
                    <a href="/Login">
                        Login
                    </a>
                </li>
                <li>
                    <a href="/">
                        Logout
                    </a>
                </li>
                <li>
                    <a href="/configuration">
                        Configurações
                    </a>
                </li>
            </ul>
        </div>

    );
}