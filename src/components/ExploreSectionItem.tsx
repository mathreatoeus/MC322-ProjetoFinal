export default function ExploreSectionItem (props:{src:string, name:string, price:number}) {
    return(
        <div className="exploreCard">
            <a href="" >
                <img src={props.src} height ="250px"  width="250px"></img>
                <div className="exploreitemlabel">
                    <div className="exploreitemname">Pacote para {props.name}</div>
                    <div className="exploreitemprice">R$ {props.price}</div>

                </div>
                
            </a>
            
        </div>

    );
}