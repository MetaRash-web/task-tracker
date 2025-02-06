import "./TaskCard.css";
import * as prototype from "prop-types";

export default function TaskCard({ task }) {
    return (
        <div className="task-card">
            <h3>{task.title}</h3>
            <p>{task.description}</p>
            <span className={`priority ${task.priority.toLowerCase()}`}>
                {task.priority}
            </span>
            <span className={`status ${task.status.toLowerCase()}`}>
                {task.status}
            </span>
        </div>
    );
}

TaskCard.propTypes = {
    task: prototype.object.isRequired
}