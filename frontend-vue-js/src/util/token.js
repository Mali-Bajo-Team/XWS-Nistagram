export function getToken() {
    return localStorage.getItem("JWT-CPIS");
}

export function saveToken(token) {    
    localStorage.setItem("JWT-CPIS", token);
}

export function getParsedToken() {
    let token = localStorage.getItem("JWT-CPIS");
    var base64Payload = token.split(".")[1];
    var payload = Buffer.from(base64Payload, "base64");
    return JSON.parse(payload.toString());
}

export function removeToken() {
    localStorage.removeItem("JWT-CPIS");
}
export function getEmailFromToken() {
    let parsedToken=getParsedToken();
    return parsedToken.sub;
}