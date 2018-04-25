
import * as express from 'express';
import * as http from 'http';
import * as WebSocket from 'ws';

const app = express();
    app.use((req, res, next) => {
        res.header("Access-Control-Allow-Origin", "*");
        res.header("Access-Control-Allow-Headers", "X-Requested-With");
        res.header("Access-Control-Allow-Headers", "Content-Type");
        res.header("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS");
        next();
    });

const server = http.createServer(app);

const wss = new WebSocket.Server({ server });

wss.on('connection', (ws: WebSocket) => {

    ws.on('message', (message: string) => {
        // switch(message)
        ws.send(`Responding to: ${message} with content: []`);
    });

    console.log("Recived channel instance.");
});

server.listen(process.env.PORT || 8000, () => {
    console.log(`Server started on port ${server.address().port}`);
});