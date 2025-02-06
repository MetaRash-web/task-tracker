import { tasks } from "../mock/tasks.js";
import TaskCard from "./TaskCard.jsx";
import "./TaskList.css";

export default function TaskList() {
    return (
        <div className="task-list">
            {tasks.map((task) => (
                <TaskCard key={task.id} task={task} />
            ))}
        </div>
    );
}