import FilterModal from "../filter/FilterModal.jsx";
import {useState} from "react";


export default function Topbar() {
    const [isFilterVisible, setIsFilterVisible] = useState(false);

    return (
        <div className="top-bar">
            <button
                className={`filter-button ${isFilterVisible ? "active" : null}`}
                onClick={() => setIsFilterVisible(!isFilterVisible)}
            >
                <i className={`fa-solid ${isFilterVisible ? "fa-xmark" : "fa-bars"}`}></i>
            </button>
            {isFilterVisible && (
                <FilterModal
                    isFilterVisible={isFilterVisible}
                />
            )}
        </div>
    )
}