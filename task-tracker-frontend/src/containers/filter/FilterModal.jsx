import * as prototype from "prop-types";
import "./FilterModal.css";
import "../dropdown/Dropdown.css";
import Dropdown from "../dropdown/Dropdown";
import DropdownItem from "../dropdown/DropdownItem";

export default function FilterModal({ isFilterVisible }) {
    const itemsForSort = ["Default", "Deadline", "Priority"];


    return (
        isFilterVisible && (
            <div className="filter-panel" id="filter-modal">
                <div className="filter-header">
                    <span className="filter-sort">View</span>
                </div>
                <div className="filter-divider"></div>
                <div className="filter-content">
                    <div className="filter-row">
                        <label className="filter-label">Sort by:</label>
                        <Dropdown
                            buttonText="Default"
                            content={
                                <>
                                    {itemsForSort.map((item) => (
                                        <DropdownItem>{`${item}`}</DropdownItem>
                                    ))}
                                </>
                            }
                        />
                    </div>
                </div>
            </div>
        ));
}

FilterModal.propTypes = {
    isFilterVisible: prototype.bool.isRequired,
};