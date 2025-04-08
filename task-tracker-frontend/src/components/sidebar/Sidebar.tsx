import IconSelector from "../IconSelector";

interface SidebarProps {
  onOpenModal: (modalName: string) => void;
}

const Sidebar = ({ onOpenModal }: SidebarProps) => {
  const menuItems = [
    { icon: "taskList", path: "/" },
    { icon: "plus", fileName: "AddTaskModal" },
    { icon: "settings", fileName: "SettingsModal" },
    { icon: "ring", path: "/notifications" },
    { icon: "profile", fileName: "AccountModal" },
  ];

  return (
    <div className="w-18 p-6 flex flex-col gap-6 h-full">
      {menuItems.map((item) => (
        item.path ? (
          <a key={item.icon} href={item.path}>
            <IconSelector id={item.icon} className="mt-5 text-white" />
          </a>
        ) : (
          <button
            key={item.icon}
            onClick={() => onOpenModal(item.fileName!)}
            className="cursor-pointer"
          >
            <IconSelector id={item.icon} className="mt-5 text-white" />
          </button>
        )
      ))}
    </div>
  );
};

export default Sidebar;