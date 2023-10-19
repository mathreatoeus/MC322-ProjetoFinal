import Button from "./Button";
import StepProgress from "./StepProgress";

export default function CustomPackageSection(){
    return(
        <div className="cstpckContainer">
            <div className="filterBar">
                <div className="filterItem">
                    <span className="filterItemSpan">Origin</span>
                    <input className="originInput"></input>
                </div>
                <div className="filterItem">
                    <span className="filterItemSpan">Destination</span>
                    <input className="destinationInput"></input>
                </div>
                <div className="filterItem">
                    <span className="filterItemSpan">From</span>
                    <input className="fromInput"></input>
                </div>
                <div className="filterItem">
                    <span className="filterItemSpan">To</span>
                    <input className="toInput"></input>
                </div>
                <div className="filterItem">
                    <span className="filterItemSpan">Rooms</span>
                    <input className="roomsInput"></input>
                </div>
                <div className="filterItem">
                    <span className="filterItemSpan">Persons</span>
                    <input className="personsInput"></input>
                </div>

            </div>
            <div className="steps">
                <Button
                    label="Prev."
                />
                <StepProgress/>
                <Button
                    label="Next"
                />

            </div>
            <div className="cstpckTable">

            </div>
        </div>

    );
}