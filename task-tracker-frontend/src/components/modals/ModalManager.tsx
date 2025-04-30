"use client";

import { ModalType, ModalPropsMap } from "@/types/modal.types";
import AddTaskModal from "./tasks/AddTaskModal";
import EditTaskModal from "./tasks/EditTaskModal";
import SettingsModal from "./settings-modal/SettingsModal";
import AccountModal from "./account-modal/AccountModal";
import NotificationsModal from "./notifications-modal/NotificationsModal";
import { AnimatePresence, motion } from "framer-motion";

interface ModalManagerProps<T extends ModalType> {
    modalType: T | null;
    onClose: () => void;
    modalProps?: ModalPropsMap[T];
  }

  
export const ModalManager = <T extends ModalType>({
  modalType,
  onClose,
  modalProps,
}: ModalManagerProps<T>) => {
  const modalComponents = {
    AddTaskModal: (
      <AddTaskModal 
        onClose={onClose}
        {...(modalProps as ModalPropsMap["AddTaskModal"])}
      />
    ),
    EditTaskModal: (
      <EditTaskModal
        onClose={onClose}
        {...(modalProps as ModalPropsMap["EditTaskModal"])}
      />
    ),
    SettingsModal: (
      <SettingsModal 
        onClose={onClose}
        {...(modalProps as ModalPropsMap["SettingsModal"])}
      />
    ),
    AccountModal: (
      <AccountModal 
        onClose={onClose}
        {...(modalProps as ModalPropsMap["AccountModal"])}
      />
    ),
    NotificationsModal: (
      <NotificationsModal
      onClose={onClose}
        {...(modalProps as ModalPropsMap["NotificationsModal"])}
      />
    )
  };

  return (
    <AnimatePresence>
      {modalType && (
        <motion.div
          key={modalType}
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          exit={{ opacity: 0, y: -20 }}
          transition={{ duration: 0.2, ease: "easeOut" }}
          className="fixed inset-0 z-50 flex items-center justify-center p-4"
        >
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="absolute inset-0 bg-black/50"
            onClick={onClose}
          />
          
          <div className="relative z-10">
            {modalComponents[modalType]}
          </div>
        </motion.div>
      )}
    </AnimatePresence>
  );
};