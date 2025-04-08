import React from 'react';
import LampIcon from '@/icons/LampIcon';
import PlusIcon from '@/icons/PlusIcon';
import ReminderRingIcon from '@/icons/ReminderRingIcon';
import SettingsIcon from '@/icons/SettingsIcon';
import TaskListIcon from '@/icons/TaskListIcon';
import ProfileIcon from '@/icons/ProfileIcon';

interface IconSelectorProps {
  id: string;
  className?: string;
}

const IconSelector: React.FC<IconSelectorProps> = ({ id, className }) => {
  switch (id) {
    case 'taskList':
        return <TaskListIcon className={className} />
    case 'lamp':
      return <LampIcon className={className} />;
    case 'plus':
      return <PlusIcon className={className} />;
    case 'ring':
      return <ReminderRingIcon className={className} />;
    case 'settings':
        return <SettingsIcon className={className} />
    case 'profile':
        return <ProfileIcon className={className} />
    default:
      return <div>0</div>;
  }
};

export default IconSelector;