import { format, parseISO, isValid } from 'date-fns';
import { ru } from 'date-fns/locale';

export const formatTaskDate = (dateInput?: string | Date | null) => {
  if (!dateInput) return "—";

  try {
    let date: Date;
    
    if (typeof dateInput === 'number') {
      date = new Date(dateInput * 1000); 
    } else if (typeof dateInput === 'string') {
      const dateStr = dateInput.replace('T', ' ')      
      date = parseISO(dateStr);
    } else {
      const isoString = dateInput.toISOString();
      date = parseISO(isoString.replace('T', ' '));
    }

    return format(date, "dd.MM.yyyy HH:mm", { locale: ru });
  } catch (error) {
    console.error("Ошибка форматирования даты:", dateInput, error);
    return "—";
  }
};

export const toDateTimeLocal = (date?: Date | string | null): string => {
  if (!date) return '';
  
  try {
    const d = date instanceof Date ? date : new Date(date);
    if (isNaN(d.getTime())) return '';
    
    return format(d, "yyyy-MM-dd'T'HH:mm", { locale: ru });
  } catch {
    return '';
  }
};

export const fromDateTimeLocal = (value: string): Date | null => {
  if (!value) return null;
  
  try {
    const [datePart, timePart] = value.split('T');
    const [year, month, day] = datePart.split('-').map(Number);
    const [hours, minutes] = timePart.split(':').map(Number);
    
    return new Date(year, month - 1, day, hours, minutes);
  } catch {
    return null;
  }
};

export const formatForBackend = (date?: Date | string | null): string | undefined => {
  if (!date) return undefined;
  
  try {
    const d = date instanceof Date ? date : new Date(date);
    return format(d, "yyyy-MM-dd'T'HH:mm:ss");
  } catch {
    return undefined;
  }
};