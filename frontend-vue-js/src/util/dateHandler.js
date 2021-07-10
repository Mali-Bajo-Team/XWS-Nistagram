// Thanks stacky: https://stackoverflow.com/questions/1531093/how-do-i-get-the-current-date-in-javascript
export function getTodayDateString() {
    let today = new Date();
    let dd = String(today.getDate()).padStart(2, "0");
    let mm = String(today.getMonth() + 1).padStart(2, "0"); //January is 0!
    let yyyy = today.getFullYear();

    today = yyyy + "-" + mm + "-" + dd;
    return today;
}