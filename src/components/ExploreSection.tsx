import ExploreSectionItem from "./ExploreSectionItem";

export default function ExploreSection () {
    return(
        <div className = "exploreSection">
            <div className="containerleftButton">
                <div className ="leftButton">
                    <img src="/img/left_arrow_icon.svg" height="50px" width="50px"></img>

                </div>
                

            </div>
            <div className="explorecontainer">
                <ExploreSectionItem src={"/img/paisagem1.jpeg"} name={"Budapeste"} price={1000}/>
                <ExploreSectionItem src={"/img/paisagem1.jpeg"} name={"Natal"} price={1100}/>
                <ExploreSectionItem src={"/img/paisagem1.jpeg"} name={"Toronto"} price={890}/>
                <ExploreSectionItem src={"/img/paisagem1.jpeg"} name={"Zurich"} price={890}/>


            </div>
            <div className="containerrightButton">
                <div className="rightButton">
                    <img src="/img/right_arrow_icon.svg" height="50px" width="50px"></img>

                </div>

            </div>
        </div>
    );
}