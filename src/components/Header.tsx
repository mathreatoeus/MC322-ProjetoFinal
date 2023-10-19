import DropdownUser from "./DropdownUser";
import HeaderItem from "./HeaderItem"
import UserItem from "./UserItem";

export default function Header () {
    return(
            <header className="mainHeader">
                <ul className = "navigation">
                    <HeaderItem
                        href="/"
                        src="/img/explore_icon.svg"
                        label="Explore"
                    />
                    <HeaderItem
                        href="/CustomPackage"
                        src="/img/package_custom_icon.svg"
                        label="Custom Package"
                    />
                    <HeaderItem
                        href="/PackageManagement"
                        src="/img/config_icon.svg"
                        label="Package Config."
                    />
                </ul>
                <UserItem name={"João"} pic={"/img/user_icon.svg"}/>
                <DropdownUser/>

            

            </header>
    );
}