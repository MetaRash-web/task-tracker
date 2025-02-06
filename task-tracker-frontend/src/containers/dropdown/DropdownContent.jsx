import { forwardRef } from "react";
import "./DropdownContent.css";

const DropdownContent = forwardRef((props, ref) => {
    const { children, open, top } = props;
    return (
        <div
            className={`dropdown-content ${open ? "content-open" : null}`}
            style={{top: top !== null ? `${top}px` : "100%"}}
            ref={ref}
            onClick={(e) => e.stopPropagation()}
        >
            {children}
        </div>
    );
});

export default DropdownContent;