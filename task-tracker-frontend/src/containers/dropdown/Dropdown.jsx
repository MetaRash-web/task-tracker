import { useEffect, useState, useRef } from "react";

import DropdownButton from "../dropdown/DropdownButton";
import DropdownContent from "../dropdown/DropdownContent";
import "./Dropdown.css";

const Dropdown = ({ buttonText, content }) => {
    const [open, setOpen] = useState(false);
    const [dropdownTop, setDropdownTop] = useState(0);

    const dropdownRef = useRef(null);
    const buttonRef = useRef(null);
    const contentRef = useRef(null);

    const toggleDropdown = (e) => {
        e.stopPropagation();

        if (!open) {
            if (!buttonRef.current || !contentRef.current) return; // Проверяем, что элементы существуют

            const spaceRemaining =
                window.innerHeight - buttonRef.current.getBoundingClientRect().bottom;
            const contentHeight = contentRef.current.clientHeight;

            const topPosition =
                spaceRemaining > contentHeight
                    ? null
                    : -(contentHeight - spaceRemaining);
            setDropdownTop(topPosition);
        }

        setOpen((open) => !open);
    };

    useEffect(() => {
        const handler = (event) => {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
                setOpen(false);
            }
        };

        document.addEventListener("click", handler);

        return () => {
            document.removeEventListener("click", handler);
        };
    }, [dropdownRef]);

    return (
        <div ref={dropdownRef} className="dropdown">
            <DropdownButton ref={buttonRef} toggle={toggleDropdown} open={open}>
                {buttonText}
            </DropdownButton>
            {
                <DropdownContent top={dropdownTop} ref={contentRef} open={open}>
                    {content}
                </DropdownContent>
            }
        </div>
    );
};

export default Dropdown;