import { MaterialIcons, AntDesign, Ionicons } from '@expo/vector-icons';
export const CAMERA_PERMISSION_MESSAGE = "Aby zgłosić problem, musisz nadać dostęp do swojej kamery";
export const PERMISSION_GRANT = "Nadaj pozwolenie";
export const PHOTO_RETAKE_MESSAGE = "Znowu";
export const PHOTO_ACCEPT_MESSAGE = "Dalej";
export const REPORT_PROBLEM_BUTTON_LABEL="Zgłoś problem";
export const HISTORY_BUTTON_LABEL = "Historia";
export const HISTORY_TOAST_TEXT1_MESSAGE = "UWAGA";
export const HISTORY_TOAST_TEXT2_MESSAGE = "Zgłoś problem, aby mieć dostęp do historii";
export const MODAL_CLOSE_BUTTON_LABEL="Zamknij";
export const MODAL_DONE_BUTTON_LABEL= "Wybierz";
export const PROBLEM_TOAST_TEXT1_MESSAGE = "UWAGA";
export const PROBLEM_TOAST_TEXT2_MESSAGE = "Najpierw wybierz kategorię";
export const SUBMIT_TOAST_TEXT1_MESSAGE = "UWAGA";
export const SUBMIT_TOAST_TEXT2_MESSAGE = "Wypełnij wszystkie pola";
export const CATEGORY_LABEL = "Kategoria";
export const PROBLEM_LABEL = "Problem";
export const LOCATION_SEARCH_PLACEHOLDER = "Lokalizacja";
export const DESCRIPTION_LABEL = "Opis";
export const REPORT_PROBLEM_SUBMIT_LABEL = "Dalej";
export const MAP_SUBMIT_LABEL = "Potwierdź lokalizację";
export const CATEGORY_ERROR_MESSAGE = "Proszę wybrać kategorię";
export const PROBLEM_ERROR_MESSAGE = "Proszę wybrać problem";
export const PROBLEM_SUMMARY_SUCCESS_TITLE = "Udało się";
export const PROBLEM_SUMMARY_SUCCESS_MESSAGE = "Zgłoszenie zostało wysłane!";
export const PROBLEM_SUMMARY_ERROR_TITLE = "Coś poszło nie tak";
export const PROBLEM_SUMMARY_ERROR_MESSAGE = "Spróbuj wysłać zgłoszenie później!";
export const GO_HOME_BUTTON_LABEL = "Powrót do strony głównej";

export const CATEGORY_PREDICTION_HELPER_TEXT = "Sprawdź czy kategoria została prawidłowo zidentyfikowana (jeżeli nie, ustaw poprawną kategorię)!";
export const CATEGORY_TRANSLATED_VALUES = {
    "Garbage and segregation": "Śmiecie i segregacja"
}

export const STATUSES_MAP = {
    "Pending": <MaterialIcons name="pending" size={14} color="black" />,
    "Accepted": <AntDesign name="checkcircle" size={14} color="black" />,
    "Rejected": <AntDesign name="closecircle" size={14} color="black" />,
    "Done": <Ionicons name="checkmark-done-circle" size={14} color="black" />,
    "Reported": <MaterialIcons name="report" size={14} color="black" />
}
